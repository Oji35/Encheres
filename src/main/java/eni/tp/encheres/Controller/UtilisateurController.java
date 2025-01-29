package eni.tp.encheres.Controller;

import eni.tp.encheres.bll.UtilisateurService;
import eni.tp.encheres.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        return "profil";
    }

    @GetMapping("/modifier-profil")
    public String modifierProfilForm(@RequestParam("id") int id, Model model) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurbyID(id);
                model.addAttribute("utilisateur", utilisateur);
        return "modifier-profil";
    }


    @PostMapping("/modifier-profil")
    public String postModifierProfil(@ModelAttribute Utilisateur utilisateur,
                                     @RequestParam String password,
                                     @RequestParam String newpassword,
                                     @RequestParam String confirmPassword,
                                     Model model) {
        Utilisateur utilisateurExistant = utilisateurService.getUtilisateurbyID(utilisateur.getNumeroUtilisateur());

       // if (!passwordEncoder.matches(password, utilisateurExistant.getMotDePasse())) {
        //    model.addAttribute("error", "L'ancien mot de passe est incorrect.");
         //   return "modifier-profil";
            //}
        if (newpassword.equals(password)) {
            model.addAttribute("error", "Le nouveau mot de passe doit être différent de l'ancien.");
            return "modifier-profil";
        }
        if (!newpassword.equals(confirmPassword)) {
            model.addAttribute("error", "La confirmation du mot de passe est incorrecte.");
            return "modifier-profil";
        }
        //utilisateurExistant.setMotDePasse(passwordEncoder.encode(newpassword));
        //utilisateurService.update(utilisateurExistant);

        return "redirect:/utilisateur/profil";
    }
//A VOIR SI IL FAUT CREER UNE PAGE D INSCRIPTION OU PAS DONC JE REDIRECT SUR LE PROFIL

    @GetMapping("/inscription")
    public String inscriptionUtilisateur(Model model) {
        model.addAttribute("utilisateur", utilisateurService.getUtilisateur());
        return "redirect:/profil";
    }
    @PostMapping("/inscription")
    public String creerUtilisateur(@ModelAttribute Utilisateur utilisateur,Model model){
        utilisateurService.addUtilisateur(utilisateur);
        return "redirect:/profil";
    }


}
