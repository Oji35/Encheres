package eni.tp.encheres.bll;

import eni.tp.encheres.bo.ArticleVendu;

public interface ArticleService {

    public void addArticleVendu(ArticleVendu article);

    public ArticleVendu getArticleVendubyID(int id);

    Object getAllArticles();

    void removeArticleVenduParId(int id);

    void update(ArticleVendu article);




}
