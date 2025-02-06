package eni.tp.encheres.Controller;

import eni.tp.encheres.bll.*;
import eni.tp.encheres.bo.ArticleVendu;
import eni.tp.encheres.bo.Enchere;
import eni.tp.encheres.bo.EnchereException;
import eni.tp.encheres.bo.Utilisateur;
import eni.tp.encheres.dal.CategorieDAOImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping
public class EnchereController {

    private final ArticleServiceImpl articleServiceImpl;

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private EnchereService enchereService;

    public EnchereController(ArticleServiceImpl articleServiceImpl, ArticleService articleService, EnchereService enchereService) {
        this.articleService = articleService;
        this.articleServiceImpl = articleServiceImpl;
        this.enchereService = enchereService;
    }

    @GetMapping("/")
    public String Home(Model model) {
        return "redirect:/view-encheres";
    }

    @GetMapping("/accueil-encheres")
    public String accueil(Model model) {
        model.addAttribute("keyword", "");
        model.addAttribute("category", "Toutes");
        return "accueil-encheres";
    }

    @GetMapping("/view-encheres")
    public String afficherListeEnchere(Model model) {
        List<ArticleVendu> articles = articleService.getAllArticles(); // Vérifie que cette méthode fonctionne
        model.addAttribute("articles", articles); // Doit être au pluriel comme dans Thymeleaf
        return "view-encheres"; // Assure-toi que view-encheres.html existe
    }

    @PostMapping("/view-encheres")
    public String deleteArticle(@RequestParam(name = "id") int id) {
        articleService.removeArticleVenduParId(id);
        return "view-encheres";
    }

    @GetMapping("/detail")
    public String afficherDetailArticle(@RequestParam(name = "id") int id, Model model) {
        ArticleVendu article = articleService.getArticleVendubyID(id);
        System.out.println(article);

        Enchere enchere = enchereService.getEncherebyID(id);
        System.out.println("enchere de getEnchereController :" + enchere);

        if (article == null) {
            return "redirect:/erreur";
        }
        model.addAttribute("article", article);
        model.addAttribute("enchere", enchere);
        return "details-vente";
    }


    @PostMapping("/details-vente")
    public String posteEnchere(@RequestParam(name = "id") int id) {
        articleService.removeArticleVenduParId(id);
        return "view-encheres";
    }


    @Autowired
    private CategoriesService categoriesService; // Injection du service

    @GetMapping("/nouvelle-vente")
    public String afficherFormulaire(@AuthenticationPrincipal UserDetails userDetails,Model model) {
        model.addAttribute("article", new ArticleVendu());
        model.addAttribute("categories", categoriesService.getAllCategorie());
        String username = userDetails.getUsername();
        System.out.println(utilisateurService.getUtilisateurByUsername(username));
        model.addAttribute("utilisateur", utilisateurService.getUtilisateurByUsername(username));

        return "nouvelle-vente";
    }
    //2 post Mapping a tester
    @PostMapping("/nouvelle-vente")
    public String newChat(
            @RequestParam(name = "nomArticle") String nomArticle,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "category") int category,
            @RequestParam(name = "miseAPrix") int miseAPrix,
            @RequestParam(name = "DebutEnchere") LocalDate DebutEnchere,
            @RequestParam(name = "FinEnchere") LocalDate FinEnchere,
            @AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute ArticleVendu article, Model model) {

        ArticleVendu newArticle = new ArticleVendu();
        newArticle.setNomArticle(nomArticle);
        newArticle.setDescription(description);
        newArticle.setCategorie(categoriesService.getCategoriebyID(category));


        String username = userDetails.getUsername();
        newArticle.setUtilisateur(utilisateurService.getUtilisateurByUsername(username));

        newArticle.setPrix_initial(miseAPrix);
        newArticle.setPrixVente(miseAPrix);
        newArticle.setDateDebutEncheres(DebutEnchere);
        newArticle.setDateFinEncheres(FinEnchere);

        System.out.println(newArticle);

        articleService.addArticleVendu(newArticle);
        return "redirect:/view-encheres";
    }

//    @PostMapping("/nouvelle-vente")
//    public String enregistrerVente(@ModelAttribute ArticleVendu article, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("categories", List.of("Maison", "Informatique", "Ameublement", "Vêtement", "Sport&Loisirs"));
//            return "nouvelle-vente";
//        }
//        // Sauvegarde dans la base de données (ajoute ton service ici)
//        articleService.addArticleVendu(article);
//        return "redirect:/ventes";
//    }




    //Recuperer les données de l'enchere
    @PostMapping("/encherir")
    public String submitById(@RequestParam(name = "id") int id,
                             @RequestParam(name = "montantEnchere") int montantEnchere,
                             @AuthenticationPrincipal UserDetails userDetails, // Get the authenticated user
                             RedirectAttributes redirectAttributes,
                             Model model) throws Exception {


        // Get the current logged-in user ID (assuming your user object has the user ID)
        System.out.println("Numéro article envoyé par le Postmapping : " + id);
        String username = userDetails.getUsername();

        Utilisateur utilisateur = utilisateurService.getUtilisateurByUsername(username);

        // Set the current date as the bid date
        Date dateEnchere = new Date(); // You can use LocalDateTime.now() if you need a more specific timestamp

        System.out.println("Date envoyée " + dateEnchere);

        // Create a new Encheres instance
        Enchere enchere = new Enchere();
        enchere.setNoUtilisateur(utilisateur.getNo_Utilisateur());
        enchere.setNoArticle(id);
        enchere.setMontantEnchere(montantEnchere);
        enchere.setDateEnchere(dateEnchere);

        System.out.println(enchere.toString());

        enchereService.addEnchere(enchere);
        model.addAttribute("message","Enchère enregistrée avec succès!");
        return "redirect:/details-article/" + id;
    }

}


//@GetMapping("/detail")
//public String afficherDetailArticle(@RequestParam(name = "id") int id, Model model) {
//    ArticleVendu article = articleService.getArticleVendubyID(id);
//    if (article == null) {
//        return "redirect:/erreur";
//    }
//    model.addAttribute("article", article);
//    return "details-vente";
//}