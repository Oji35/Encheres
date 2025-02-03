package eni.tp.encheres.bll;

import eni.tp.encheres.bo.Enchere;
import eni.tp.encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurService {


    void addUtilisateur(Utilisateur utilisateur);

    void removeUtilisateur(int id);

    List<Utilisateur> getAllUtilisateur();

    Utilisateur getUtilisateurbyID(int id);

    void update(Utilisateur utilisateur);

    Utilisateur getUtilisateurByUsername(String username);
}
