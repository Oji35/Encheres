package eni.tp.encheres.Controller;

import eni.tp.encheres.bll.ArticleService;
import eni.tp.encheres.bo.ArticleVendu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class EnchereController {

    private ArticleService articleService;

    public EnchereController(ArticleService articleService) {
        this.articleService = articleService;
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
        System.out.println("Liste d'articles du view-encheres : " + articles);
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
        if (article == null) {
            return "redirect:/erreur";
        }
        System.out.println(article);
        model.addAttribute("article", article);
        return "details-vente";
    }

    @PostMapping("/details-vente")
    public String posteEnchere(@RequestParam(name = "id") int id) {
        articleService.removeArticleVenduParId(id);
        return "view-encheres";
    }
    @GetMapping("/nouvelle-vente")
    public String afficherFormulaire(Model model) {
        model.addAttribute("article", new ArticleVendu()); // Nouvel article vide
        model.addAttribute("categories", List.of("Maison", "Informatique", "Ameublement", "Vêtement", "Sport&Loisirs"));
        return "nouvelle-vente";
    }


    @PostMapping("/nouvelle-vente")
    public String enregistrerVente(@ModelAttribute ArticleVendu article, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", List.of("Maison", "Informatique", "Ameublement", "Vêtement", "Sport&Loisirs"));
            return "nouvelle-vente";
        }
        // Sauvegarde dans la base de données (ajoute ton service ici)
        articleService.addArticleVendu(article);

        return "redirect:/ventes";
    }

  




}
