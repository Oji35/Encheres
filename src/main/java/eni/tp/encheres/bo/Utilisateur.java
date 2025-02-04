package eni.tp.encheres.bo;

import java.util.Objects;

public class Utilisateur {

    private int numeroUtilisateur;
    private String pseudo;
    private String nom;
    private String prenom;
    private String email;
    private int telephone;
    private String rue;
    private int codePostal;
    private String ville;
    private String motDePasse;
    private int credit;
    private boolean admin= false;

    public Utilisateur(int numeroUtilisateur, String pseudo, String nom, String prenom,
                       String email, int telephone, String rue, int codePostal,
                       String ville, String motDePasse, int credit, boolean admin) {

        this.numeroUtilisateur = numeroUtilisateur;
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.motDePasse = motDePasse;
        this.credit = credit;
        this.admin = admin;
    }

    public int getNumeroUtilisateur() {
        return numeroUtilisateur;
    }

    public Utilisateur() {
    }

    public void setNumeroUtilisateur(int numeroUtilisateur) {
        this.numeroUtilisateur = numeroUtilisateur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "numeroUtilisateur=" + numeroUtilisateur +
                ", pseudo='" + pseudo + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephone=" + telephone +
                ", rue='" + rue + '\'' +
                ", codePostal=" + codePostal +
                ", ville='" + ville + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", credit=" + credit +
                ", admin=" + admin +
                '}';
    }


}
