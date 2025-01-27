package eni.tp.encheres.bo;
import java.time.*;

public class ArticleVendu {
    private String noArticle;
    private String nomArticle;
    private String description;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private String miseAPrix;
    private String prixVente;

    public ArticleVendu(String noArticle, String nomArticle, String description, LocalDate dateFinEncheres, LocalDate dateDebutEncheres, String miseAPrix, String prixVente) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateFinEncheres = dateFinEncheres;
        this.dateDebutEncheres = dateDebutEncheres;
        this.miseAPrix = miseAPrix;
        this.prixVente = prixVente;
    }

    public String getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(String noArticle) {
        this.noArticle = noArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public LocalDate getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(LocalDate dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public String getMiseAPrix() {
        return miseAPrix;
    }

    public void setMiseAPrix(String miseAPrix) {
        this.miseAPrix = miseAPrix;
    }

    public String getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(String prixVente) {
        this.prixVente = prixVente;
    }
}
