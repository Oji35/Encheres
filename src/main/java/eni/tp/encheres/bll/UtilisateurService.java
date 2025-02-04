package eni.tp.encheres.bll;

import eni.tp.encheres.bo.Enchere;
import eni.tp.encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurService {


    Utilisateur getUtilisateurByUsername(String username);

    void addUtilisateur(Utilisateur utilisateur);

    void removeUtilisateur(Utilisateur utilisateur);

    List<Utilisateur> getAllUtilisateur();

    Utilisateur getUtilisateurbyID(int id);

    void update(Utilisateur utilisateur);


}
