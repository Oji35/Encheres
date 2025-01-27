package eni.tp.encheres.bo;

public class Utilisateur {

    private long numeroUtilisateur;
    private String pseudo;
    private String nom;
    private String prenom;
    private String email;
    private long numeroTelephone;
    private String rue;
    private long codePostal;
    private String ville;
    private String motDePasse;
    private int credit;
    private boolean admin= false;

    public Utilisateur(long numeroUtilisateur, String pseudo, String nom, String prenom,
                       String email, long numeroTelephone, String rue, long codePostal,
                       String ville, String motDePasse, int credit, boolean admin) {

        this.numeroUtilisateur = numeroUtilisateur;
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numeroTelephone = numeroTelephone;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.motDePasse = motDePasse;
        this.credit = credit;
        this.admin = admin;
    }

    public long getNumeroUtilisateur() {
        return numeroUtilisateur;
    }

    public void setNumeroUtilisateur(long numeroUtilisateur) {
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

    public long getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(long numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public long getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(long codePostal) {
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
}
