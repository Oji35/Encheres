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
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(EnchereService utilisateurService) {
        this.utilisateur = utilisateurService;
    }

    @GetMapping("/profil")
    public String afficherprofil(Model model) {
        var nomProfil = UtilisateurService.getNom();
        model.addAttribute("enchere", enchere);
        return "ListeEnchere";
    }
}