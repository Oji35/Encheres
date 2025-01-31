package eni.tp.encheres.Configuration;

import eni.tp.encheres.bo.Utilisateur;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



public class LoginConfiguration {
    //utilisation de logger pour suivre l'exécution
    @Controller
    public static class LoginController {

    //    Utilisateur utilisateur;
    //    protected final Log logger = LogFactory.getLog(getClass());

     //   @GetMapping("/login")
    //    String login(Model model) {
     //       model.addAttribute("utilisateur", utilisateur);
     //       logger.info("Affichage du formulaire de login");
     //       return "login";
     //   }

        //recupérer les infos de connection
     //   @PostMapping("/login")
     //   String loginPost(@RequestParam String pseudo, @RequestParam String motdepasse, Model model) {
     //       logger.info("Tentative de connection user");
     //       if("pseudo".equals(utilisateur.getPseudo()) && "password".equals(motdepasse)) {
     //           return "redirect:/accueil-encheres";
     //       }else{
     //           logger.warn("Erreur de l'identifiant ou du mot de passe");
     //           return "login";
     //       }

        }

    }


