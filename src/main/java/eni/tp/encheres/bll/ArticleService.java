package eni.tp.encheres.bll;

import eni.tp.encheres.bo.ArticleVendu;

import java.util.List;

public interface ArticleService {
    void addArticleVendu(ArticleVendu article);

    void removeArticleVenduParId(int id);

    ArticleVendu getArticleVendubyID(int id);

    void update(ArticleVendu article);

    List<ArticleVendu> getAllArticles();

}
