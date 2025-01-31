package eni.tp.encheres.Controller;

import eni.tp.encheres.bll.ArticleService;
import eni.tp.encheres.bo.ArticleVendu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        System.out.println("Liste d'articles du viex-encheres : " + articles);
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
        System.out.println("Article by id du Controller : " + article);
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



}
