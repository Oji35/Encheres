package eni.tp.encheres.dal;

import eni.tp.encheres.bo.Utilisateur;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UtilisateurDAO {

    int createUtilisateur(Utilisateur utilisateur);

    Utilisateur readUtilisateur(int id);

    List<Utilisateur> readAllUtilisateurs();

    void updateUtilisateur(Utilisateur utilisateur);

    //REQUETE SQL POUR DESACTIVER L'UTILISATEUR
    @Transactional
    void disableUtilisateur(Utilisateur utilisateur);

    // REQUETE SQL POUR DELETE
    void deleteUtilisateur(Utilisateur utilisateur);


}
