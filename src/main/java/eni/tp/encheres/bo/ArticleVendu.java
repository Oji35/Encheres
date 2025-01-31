package eni.tp.encheres.bo;
import java.time.*;

public class ArticleVendu {
    private int noArticle;
    private String nomArticle;
    private String description;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private long prix_initial;
    private long prixVente;
    Utilisateur utilisateur;
    Categorie categorie;

    public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateFinEncheres, LocalDate dateDebutEncheres, long prix_initial, long prixVente) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateFinEncheres = dateFinEncheres;
        this.dateDebutEncheres = dateDebutEncheres;
        this.prix_initial = prix_initial;
        this.prixVente = prixVente;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public ArticleVendu() {
    }

    public int getNoUtilisateur() {
        return utilisateur.getNumeroUtilisateur();
    }

    public int getNoCategorie() {
        return categorie.getNoCategorie();
    }

    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
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

    public long getPrix_initial() {
        return prix_initial;
    }

    public void setPrix_initial(long prix_initial) {
        this.prix_initial = prix_initial;
    }

    public long getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(long prixVente) {
        this.prixVente = prixVente;
    }

    @Override
    public String toString() {
        return "ArticleVendu{" +
                "noArticle=" + noArticle +
                ", nomArticle='" + nomArticle + '\'' +
                ", description='" + description + '\'' +
                ", dateDebutEncheres=" + dateDebutEncheres +
                ", dateFinEncheres=" + dateFinEncheres +
                ", prix_initial=" + prix_initial +
                ", prixVente=" + prixVente +
                ", utilisateur=" + utilisateur +
                ", categorie=" + categorie +
                '}';
    }
}
