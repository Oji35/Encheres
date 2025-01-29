package eni.tp.encheres.bll;

import eni.tp.encheres.bo.ArticleVendu;
import eni.tp.encheres.dal.ArticleDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleDAO articleDAO;

    public ArticleServiceImpl(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Override
    public void addArticleVendu(ArticleVendu article) {
        articleDAO.createArticleVendu(article);
    }

    @Override
    public void removeArticleVenduParId(int id) {
        articleDAO.deleteArticle(id);
    }

    @Override
    public ArticleVendu getArticleVendubyID(int id) {
        return articleDAO.readArticle(id);
    }

    @Override
    public void update(ArticleVendu article) {
        articleDAO.updateArticle(article);
    }

    @Override
    public List<ArticleVendu> getAllArticles() {
        return articleDAO.readAllArticles();
    }
}
