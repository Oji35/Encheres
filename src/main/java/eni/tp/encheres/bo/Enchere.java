package eni.tp.encheres.bo;

import java.time.LocalDateTime;

public class Enchere {
    private int numeroUtilisateur;
    private int noArticle;
    private LocalDateTime dateEnchere;
    private int montantEnchere;
    private String nomObjet;

    public Enchere( int numeroUtilisateur,int noArticle, int montantEnchere) {
        this.numeroUtilisateur = numeroUtilisateur;
        this.noArticle = noArticle;
        this.dateEnchere = LocalDateTime.now();
        this.montantEnchere = montantEnchere;
    }

    public String getNomObjet() {return nomObjet;}

    public void setNomObjet(String nomObjet) {this.nomObjet = nomObjet;}

    public int getId() {
        return noArticle;
    }

    public void setId(int id) {
        this.noArticle = id;
    }

    public int getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(int montantEnchere) {
        this.montantEnchere = montantEnchere;
    }

    public LocalDateTime getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(LocalDateTime dateEnchere) {
        this.dateEnchere = dateEnchere;
    }


    public int getNumeroUtilisateur() {
        return numeroUtilisateur;
    }
}

