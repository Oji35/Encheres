package eni.tp.encheres.Controller;

import eni.tp.encheres.bll.ArticleService;
import eni.tp.encheres.bll.UtilisateurService;
import eni.tp.encheres.bo.ArticleVendu;
import eni.tp.encheres.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@Controller
public class UploadController {

    private final Path uploadPath;
    private final ArticleService articleService;
    private final UtilisateurService utilisateurService;

    public UploadController(@Value("${file.upload-dir}") String uploadDir,
                            ArticleService articleService,
                            UtilisateurService utilisateurService) {
        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.articleService = articleService;
        this.utilisateurService = utilisateurService;

        try {
            Files.createDirectories(this.uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Impossible de créer le répertoire d'upload", e);
        }
    }
    @GetMapping("/nouvelle-vente-upload")
    public String showUploadForm(Model model) {
        model.addAttribute("article", new ArticleVendu());
        // Ajoutez les catégories si nécessaire
        return "nouvelle-vente"; // Assurez-vous que le nom correspond à votre fichier HTML
    }

    @PostMapping("/nouvelle-vente-upload")
    public String handleFileUpload(@RequestParam("avatar") MultipartFile file,
                                   @ModelAttribute ArticleVendu article,
                                   Model model,
                                   Principal principal) {
        if (!file.isEmpty()) {
            try {
                if (!file.getContentType().startsWith("image/")) {
                    model.addAttribute("message", "Le fichier doit être une image !");
                    return "redirect:/nouvelle-vente-upload";
                }

                String originalFileName = file.getOriginalFilename().replaceAll("\\s+", "_");
                String fileName = System.currentTimeMillis() + "_" + originalFileName;
                Path filePath = this.uploadPath.resolve(fileName);
                Files.write(filePath, file.getBytes());

                article.setImageName(fileName);
                Utilisateur utilisateur = utilisateurService.findByPseudo(principal.getName());

                if (utilisateur != null) {
                    article.setUtilisateur(utilisateur);
                    articleService.addArticleVendu(article);
                    model.addAttribute("message", "Fichier uploadé avec succès: " + fileName);
                } else {
                    model.addAttribute("message", "Utilisateur non trouvé !");
                }
            } catch (IOException e) {
                model.addAttribute("message", "Erreur lors de l'upload du fichier: " + file.getOriginalFilename());
            }
        } else {
            model.addAttribute("message", "Veuillez sélectionner un fichier à uploader.");
        }
        return "redirect:/nouvelle-vente-upload";
    }

    @GetMapping("/uploads/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) throws MalformedURLException {
        Path filePath = uploadPath.resolve(fileName);
        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}
