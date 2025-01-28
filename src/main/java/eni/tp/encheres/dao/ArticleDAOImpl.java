package eni.tp.encheres.dao;

import eni.tp.encheres.bo.ArticleVendu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper to map rows from the ResultSet to the ArticleVendu object
    private RowMapper<ArticleVendu> rowMapper = new RowMapper<ArticleVendu>() {
        @Override
        public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArticleVendu article = new ArticleVendu(
                    rs.getString("no_article"),
                    rs.getString("nom_article"),
                    rs.getString("description"),
                    rs.getObject("date_fin_encheres", LocalDate.class),
                    rs.getObject("date_debut_encheres", LocalDate.class),
                    rs.getString("mise_a_prix"),
                    rs.getString("prix_vente")
            );
            return article;
        }
    };

    // CREATE: Add a new article
    public int createArticle(ArticleVendu article) {
        String sql = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, article.getNomArticle(), article.getDescription(),
                article.getDateDebutEncheres(), article.getDateFinEncheres(),
                article.getMiseAPrix(), article.getPrixVente());
    }

    // READ: Get an article by ID
    public ArticleVendu getArticleById(String noArticle) {
        String sql = "SELECT * FROM ARTICLES_VENDUS WHERE no_article = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, noArticle);
    }

    // READ: Get all articles
    public List<ArticleVendu> getAllArticles() {
        String sql = "SELECT * FROM ARTICLES_VENDUS";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // UPDATE: Update an existing article
    public int updateArticle(ArticleVendu article) {
        String sql = "UPDATE ARTICLES_VENDUS SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, mise_a_prix = ?, prix_vente = ? " +
                "WHERE no_article = ?";
        return jdbcTemplate.update(sql, article.getNomArticle(), article.getDescription(),
                article.getDateDebutEncheres(), article.getDateFinEncheres(),
                article.getMiseAPrix(), article.getPrixVente(), article.getNoArticle());
    }

    // DELETE: Delete an article by ID
    public int deleteArticle(String noArticle) {
        String sql = "DELETE FROM ARTICLES_VENDUS WHERE no_article = ?";
        return jdbcTemplate.update(sql, noArticle);
    }
}