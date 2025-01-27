package eni.tp.encheres.dao;

import eni.tp.encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurDAO {

    int createUtilisateur(Utilisateur utilisateur);

    Utilisateur readUtilisateur(int id);

    List<Utilisateur> readAllUtilisateurs();

    void updateUtilisateur(Utilisateur utilisateur);

    void deleteUtilisateur(int id);

    void deleteUtilisateur(Utilisateur utilisateur);



}
