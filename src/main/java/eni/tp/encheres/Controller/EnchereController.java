package eni.tp.encheres.Controller;

import eni.tp.encheres.bll.EnchereService;
import eni.tp.encheres.bll.UtilisateurService;
import eni.tp.encheres.bo.Enchere;
import eni.tp.encheres.bo.Utilisateur;
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

    private final EnchereService enchereService;

    public EnchereController(EnchereService enchereService) {
        this.enchereService = enchereService;
    }

    @GetMapping("/")
    public String Home(Model model) {
        return "accueil-encheres";
    }


    @GetMapping("/accueil-encheres")
    public String accueil(Model model) {
        List<Enchere> articles = enchereService.getAllArticles();
        model.addAttribute("articles", articles);
        model.addAttribute("keyword", "");
        model.addAttribute("category", "Toutes");
        return "accueil-encheres";
    }


    @GetMapping("/view-encheres")
    public String afficherListeEnchere(Model model) {
        var enchere = enchereService.getEnchere();
        model.addAttribute("enchere", enchere);
        return "ListeEnchere";
    }

    @PostMapping("/view-encheres")
    public String supprimerEnchere(@RequestParam int id) {
        enchereService.removeEnchere(id); //va sup lobjet de la liste a mettre seulment pour le vendeur
        return "redirect:/liste";
    }



}
