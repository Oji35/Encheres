package eni.tp.encheres.Controller;


import eni.tp.encheres.bo.Enchere;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class EnchereController {
    private Enchere enchere;
    private EnchereService enchereService;


    public EnchereController(EnchereService enchereService) {this.enchereService = enchereService;}

    public String Home(Model model) {
        return "Index";
    }

    @GetMapping("/liste")
    public String afficherListeEnchere(Model model) {
        var enchere = enchereService.getEnchere();
        model.addAttribute("enchere", enchere);
        return "ListeEnchere";
    }


}
