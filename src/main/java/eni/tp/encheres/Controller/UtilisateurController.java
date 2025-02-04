package eni.tp.encheres.Controller;

import eni.tp.encheres.bll.UtilisateurService;
import eni.tp.encheres.bll.UtilisateurServiceImpl;
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
    @Autowired
    private UtilisateurServiceImpl utilisateurServiceImpl;

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

        //evite de s'inscrire sous le pseudo anonymousUser
        if (pseudo.equals("anonymousUser")) {
            return "redirect:/error";
        }
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
//        String sql = "SELECT no_utilisateur FROM UTILISATEURS WHERE pseudo = ?";
//        Integer noUtilisateur = jdbcTemplate.queryForObject(sql, Integer.class, username);

        // Use the getUtilisateurByUsername method to get the Utilisateur object
        Utilisateur utilisateur = utilisateurService.getUtilisateurByUsername(username);

        if (utilisateur != null) {
            model.addAttribute("utilisateur", utilisateur);

            return "profil";
        }
        // If no user found, redirect to login page or show an error
        return "redirect:/login";
    }




    @GetMapping("/modifier-profil")
    public String modifierProfilForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();

        Utilisateur utilisateur = utilisateurService.getUtilisateurByUsername(username);

        if (utilisateur != null) {
            model.addAttribute("utilisateur", utilisateur);

            return "modifier-profil";

        }

        // If no user found, redirect to login page or show an error
        return "redirect:/login";

    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/modifier-profil")
    public String postModifierProfil(@RequestParam("nom") String nom,
                                     @RequestParam("prenom") String prenom,
                                     @RequestParam("email") String email,
                                     @RequestParam("telephone") int telephone,
                                     @RequestParam("rue") String rue,
                                     @RequestParam("code_postal") int codePostal,
                                     @RequestParam("ville") String ville,
                                     @RequestParam("password") String password,
                                     @RequestParam("confirmPassword") String confirmPassword,
                                     @AuthenticationPrincipal UserDetails userDetails,
                                     Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Les mots de passe ne correspondent pas.");
            return "modifier-profil"; // Show error and stay on the profile update page
        }

        String username = userDetails.getUsername();

        Utilisateur utilisateurModif = utilisateurService.getUtilisateurByUsername(username);
        if (utilisateurModif == null) {
            model.addAttribute("error", "Utilisateur non trouvé.");
            return "modifier-profil"; // Show error and stay on the profile update page
        }

        utilisateurModif.setNom(nom);
        utilisateurModif.setPrenom(prenom);
        utilisateurModif.setEmail(email);
        utilisateurModif.setTelephone(telephone);
        utilisateurModif.setRue(rue);
        utilisateurModif.setCodePostal(codePostal);
        utilisateurModif.setVille(ville);
        if (!password.isEmpty()) {
            utilisateurModif.setMotDePasse(passwordEncoder.encode(password));
        }

        utilisateurService.update(utilisateurModif);
        System.out.println("UtilisateurController : " + utilisateurModif.toString());


        return "redirect:/profil";
    }

    //Gestion de la déconnection
    @RequestMapping ("/logout-sucess")
    public String logoutSucess(Model model) {
        return"redirect:/inscription";
    }

}
