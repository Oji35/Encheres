package eni.tp.encheres.bo;
import java.time.*;

public class ArticleVendu {
    private long noArticle;
    private String nomArticle;
    private String description;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private String prix_initial;
    private String prixVente;

    public ArticleVendu(long noArticle, String nomArticle, String description, LocalDate dateFinEncheres, LocalDate dateDebutEncheres, String prix_initial, String prixVente) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateFinEncheres = dateFinEncheres;
        this.dateDebutEncheres = dateDebutEncheres;
        this.prix_initial = prix_initial;
        this.prixVente = prixVente;
    }


    public long getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(long noArticle) {
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

    public String getPrix_initial() {
        return prix_initial;
    }

    public void setPrix_initial(String prix_initial) {
        this.prix_initial = prix_initial;
    }

    public String getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(String prixVente) {
        this.prixVente = prixVente;
    }
}
