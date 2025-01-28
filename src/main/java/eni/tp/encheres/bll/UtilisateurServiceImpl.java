package eni.tp.encheres.bll;

import eni.tp.encheres.bo.Enchere;
import eni.tp.encheres.bo.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurServiceImpl implements UtilisateurService {

    private final List<Utilisateur> utilisateurList = new ArrayList<>();

    @Override
    public void addUtilisateur(Utilisateur utilisateur) {
        utilisateurList.add(utilisateur);
    }

    @Override
    public void removeutilisateur(int numeroUtilisateur) {
        utilisateurList.removeIf(e -> e.getnumeroUtilisateur() == numeroUtilisateur);
    }

    @Override
    public List<Utilisateur> getnumeroUtilisateur() {
        return utilisateurList;
    }

    @Override
    public Enchere getEncherebyID(int id) {
        return utilisateurList.stream().filter(e -> e.getnumeroUtilisateur() == numeroUtilisateur).findFirst().orElse(null);
    }

}
