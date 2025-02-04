package eni.tp.encheres.Controller;

import eni.tp.encheres.bll.ArticleService;
import eni.tp.encheres.bll.ArticleServiceImpl;
import eni.tp.encheres.bll.CategoriesService;
import eni.tp.encheres.bo.ArticleVendu;
import eni.tp.encheres.dal.CategorieDAOImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
@RequestMapping
public class EnchereController {

    private final ArticleServiceImpl articleServiceImpl;

    private ArticleService articleService;

    public EnchereController(ArticleServiceImpl articleServiceImpl, ArticleService articleService) {
        this.articleService = articleService;
        this.articleServiceImpl = articleServiceImpl;
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
        if (article == null) {
            return "redirect:/erreur";
        }
        model.addAttribute("article", article);
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
    public String afficherFormulaire(Model model) {
        model.addAttribute("article", new ArticleVendu());
        model.addAttribute("categories", categoriesService.getAllCategorie());
        return "nouvelle-vente";
    }
    //2 post Mapping a tester
    @PostMapping("/nouvelle-vente")
    public String newChat(@ModelAttribute ArticleVendu article, Model model) {
        articleService.addArticleVendu(article);
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

}
