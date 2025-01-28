package eni.tp.encheres.Controller;

import eni.tp.encheres.bll.EnchereService;
import eni.tp.encheres.bo.Enchere;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class EnchereController {

    private final EnchereService enchereService;

    public EnchereController(EnchereService enchereService) {
        this.enchereService = enchereService;
    }

    @GetMapping("/")
    public String Home(Model model) {
        return "index";
    }

    @GetMapping("/liste")
    public String afficherListeEnchere(Model model) {
        var enchere = enchereService.getEnchere();
        model.addAttribute("enchere", enchere);
        return "ListeEnchere";
    }

    @PostMapping("/liste")
    public String supprimerEnchere(@RequestParam int id) {
        enchereService.removeEnchere(id); //va sup lobjet de la liste a mettre seulment pour le vendeur
        return "redirect:/liste";
    }




}
