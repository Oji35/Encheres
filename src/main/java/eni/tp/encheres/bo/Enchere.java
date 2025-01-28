package eni.tp.encheres.bo;

import java.util.Date;

public class Enchere {

    private Date dateEnchere;
    private int montantEnchere;
    private int id;
    private String nomObjet;

    public String getNomObjet() {return nomObjet;}

    public void setNomObjet(String nomObjet) {this.nomObjet = nomObjet;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(int montantEnchere) {
        this.montantEnchere = montantEnchere;
    }

    public Date getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(Date dateEnchere) {
        this.dateEnchere = dateEnchere;
    }



}
