package eni.tp.encheres.dal;

import eni.tp.encheres.bo.ArticleVendu;
import eni.tp.encheres.bo.Utilisateur;

import java.util.List;

public interface ArticleDAO {
    // READ ALL
    List<ArticleVendu> readAllArticles();

    // READ BY ID
    ArticleVendu readArticle(long id);

    // UPDATE
    void updateArticle(ArticleVendu article);

    // REQUETE SQL POUR DELETE
    void deleteArticle(long id);

    // FONCTION DELETE AVEC APPEL A L'ID
    void CallIDAndDelete(ArticleVendu article);

    // CREATE
    int createArticleVendu(ArticleVendu articleVendu);
}
