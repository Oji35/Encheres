package eni.tp.encheres.Controller;

import eni.tp.encheres.bll.UtilisateurService;
import eni.tp.encheres.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class UtilisateurController {

    private final UtilisateurService utilisateurService;


    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/profil")
    public String afficherprofil(Model model) {
        Utilisateur utilisateur = utilisateurService.getUtilisateur().isEmpty()
                ? new Utilisateur(1, "User", "Nom", "Prénom", "email@example.com", 123456789,
                "Rue Exemple", 75000, "Ville", "password", 100, false)
                : utilisateurService.getUtilisateur().get(0);

        model.addAttribute("nomProfil", utilisateur.getNom());
        model.addAttribute("emailProfil", utilisateur.getEmail());

        return "ListeEnchere";
    }
}
