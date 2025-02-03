package eni.tp.encheres.Controller;

import eni.tp.encheres.bll.UtilisateurService;
import eni.tp.encheres.bo.ArticleVendu;
import eni.tp.encheres.bo.Utilisateur;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

@Controller
@RequestMapping
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/login")
    public String Login(Model model) {

        return "login";
    }

//    @PostMapping("/login")
//    public String LoggedOn(Model model) {
//        System.out.println(model);
//        System.out.println(model.getAttribute("utilisateur"));
//
//        return "view-encheres";
//    }

    @GetMapping("/inscription")
    public String Inscription(Model model) {

        return "inscription";
    }

    @PostMapping("/inscription")
    public String registerUser(@RequestParam("pseudo") String pseudo,
                               @RequestParam("password") String password,
                               @RequestParam("nom") String nom,
                               @RequestParam("prenom") String prenom,
                               @RequestParam("email") String email,
                               @RequestParam("telephone") int telephone,
                               @RequestParam("rue") String rue,
                               @RequestParam("code_postal") int codePostal,
                               @RequestParam("ville") String ville
                               ) {
        System.out.println(pseudo);
        System.out.println(password);
        System.out.println(nom);
        System.out.println(prenom);
        System.out.println(email);
        System.out.println(telephone);
        System.out.println(rue);
        System.out.println(codePostal);
        System.out.println(ville);
        // Step 1: Handle password encoding
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        // Step 2: Create a new Utilisateur (User) object
        Utilisateur newUser = new Utilisateur();
        newUser.setPseudo(pseudo);
        newUser.setMotDePasse(encodedPassword);  // Store the encoded password
        newUser.setNom(nom);
        newUser.setPrenom(prenom);
        newUser.setEmail(email);
        newUser.setTelephone(telephone);
        newUser.setRue(rue);
        newUser.setCodePostal(codePostal);
        newUser.setVille(ville);
        newUser.setCredit(0);
        newUser.setAdmin(false);
        System.out.println("UtilisateurController : " + newUser);

        // Step 3: Save the new user to the database using the service or DAO layer
        utilisateurService.addUtilisateur(newUser);

        // Step 4: Redirect or return the appropriate view
        return "redirect:/login";
    }

    @GetMapping("/profil")
    public String afficherprofil(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();

        // QUERY SQL to get the no_utilisateur
        String sql = "SELECT no_utilisateur FROM UTILISATEURS WHERE pseudo = ?";
        Integer noUtilisateur = jdbcTemplate.queryForObject(sql, Integer.class, username);

        if (noUtilisateur != null) {
            Utilisateur utilisateur = utilisateurService.getUtilisateurbyID(noUtilisateur);
            model.addAttribute("utilisateur", utilisateur);

            return "profil";
        }
        // If no user found, redirect to login page or show an error
        return "redirect:/login";
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



}
