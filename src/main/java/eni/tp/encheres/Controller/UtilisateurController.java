package eni.tp.encheres.Controller;

import eni.tp.encheres.bll.UtilisateurService;
import eni.tp.encheres.bll.UtilisateurServiceImpl;
import eni.tp.encheres.bo.Utilisateur;
import eni.tp.encheres.dal.UtilisateurDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final UtilisateurDAO utilisateurDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UtilisateurServiceImpl utilisateurServiceImpl;

    public UtilisateurController(UtilisateurService utilisateurService, UtilisateurDAO utilisateurDAO) {
        this.utilisateurService = utilisateurService;
        this.utilisateurDAO = utilisateurDAO;
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

        // Check if the pseudo already exists
        Utilisateur existingUser = utilisateurService.getUtilisateurByUsername(pseudo);
        if (existingUser != null) {
            return "redirect:/inscription";
        }

        //evite de s'inscrire sous le pseudo anonymousUser
        if (pseudo.equals("anonymousUser")) {
            return "redirect:/inscription";
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
        // Use the getUtilisateurByUsername method to get the Utilisateur object
        Utilisateur utilisateur = utilisateurService.getUtilisateurByUsername(username);
        System.out.println("Numero utilisateur connecté : " + utilisateur.getNo_Utilisateur());

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
    public String postModifierProfil(@RequestParam("pseudo") String pseudo,
                                    @RequestParam("nom") String nom,
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
            return "modifier-profil";
        }

        String username = userDetails.getUsername();

        Utilisateur utilisateurModif = utilisateurService.getUtilisateurByUsername(username);
//        if (utilisateurModif == null) {
//            model.addAttribute("error", "Utilisateur non trouvé.");
//            return "modifier-profil";
//        }
//
//        // Check if the pseudo is already taken by another user
//        List<Utilisateur> allUsers = utilisateurDAO.readAllUtilisateurs();
//        for (Utilisateur user : allUsers) {
//            if (user.getPseudo().equals(pseudo)) {
//                model.addAttribute("error", "Pseudo déjà utilisé");
//                return "modifier-profil";
//            }
//        }

        utilisateurModif.setPseudo(pseudo);
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

    @PostMapping("/profil")
    public String SupprimerProfil (@AuthenticationPrincipal UserDetails userDetails,
                                   Model model, HttpServletRequest request, HttpServletResponse response)    {
        String username = userDetails.getUsername();
        Utilisateur utilisateur = utilisateurService.getUtilisateurByUsername(username);
        if (utilisateur != null) {
            //Supprime l'utilisateur
            utilisateurService.removeUtilisateur(utilisateur);
            //Logout
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
            return "redirect:/view-encheres";
        }
        return "redirect:/login"
                ;
    }
    //Gestion de la déconnection
    @RequestMapping("/logout-sucess")
    public String logoutSucess(Model model) {
        return "redirect:/inscription";
    }




}