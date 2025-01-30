package eni.tp.encheres.Controller;

import eni.tp.encheres.bll.ArticleService;
import eni.tp.encheres.bo.ArticleVendu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "accueil-encheres";
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



}
