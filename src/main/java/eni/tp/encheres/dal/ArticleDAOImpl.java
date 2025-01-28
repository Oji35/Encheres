package eni.tp.encheres.dal;

import eni.tp.encheres.bo.ArticleVendu;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

    // Variables STRING pour les requêtes SQL
    static final String SELECT_ALL = "SELECT * from ARTICLES_VENDUS";
    static final String SELECT_BY_ID = "SELECT * from ARTICLES_VENDUS where no_article=?";
    static final String INSERT = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES (?,?,?,?,?,?,?)";
    static final String DELETE = "DELETE FROM ARTICLES_VENDUS where no_article=?";
    static final String UPDATE = "UPDATE ARTICLES_VENDUS SET nom_article=?, description=?, date_debut_encheres=?, date_fin_encheres=?, prix_initial=?, prix_vente=?, no_utilisateur=?, no_categorie=? WHERE no_article=?";


    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    JdbcTemplate jdbcTemplate;


    public ArticleDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    // READ ALL
    @Override
    public List<ArticleVendu> readAllArticles() {
        return jdbcTemplate.query(SELECT_ALL, BeanPropertyRowMapper.newInstance(ArticleVendu.class));
    }

    // READ BY ID
    @Override
    public ArticleVendu readArticle(long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID, BeanPropertyRowMapper.newInstance(ArticleVendu.class), id);
    }

    // UPDATE
    @Override
    public void updateArticle(ArticleVendu article) {
        jdbcTemplate.update(UPDATE, article.getNoArticle(), article.getNomArticle(), article.getDescription(), article.getDateDebutEncheres(), article.getDateFinEncheres(), article.getPrix_initial(), article.getPrixVente());
    }

    // REQUETE SQL POUR DELETE
    @Override
    public void deleteArticle(long id) {
        jdbcTemplate.update(DELETE, id);
    }

    // FONCTION DELETE AVEC APPEL A L'ID
    @Override
    public void CallIDAndDelete(ArticleVendu article) {
        deleteArticle(article.getNoArticle());
    }

    // CREATE
    @Override
    public int createArticleVendu(ArticleVendu article) {
        var namedparameters = new MapSqlParameterSource();
        namedparameters.addValue("nom_article", article.getNomArticle());
        namedparameters.addValue("description", article.getDescription());
        namedparameters.addValue("date_debut_encheres", article.getDateDebutEncheres());
        namedparameters.addValue("date_fin_encheres", article.getDateFinEncheres());
        namedparameters.addValue("prix_initial", article.getPrix_initial());
        namedparameters.addValue("prix_vente", article.getPrixVente());


        // For auto-generating the no_article value (IDENTITY column)
        var keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                INSERT, // Assuming INSERT is updated for ARTICLES_VENDUS table
                namedparameters,
                keyHolder,
                new String[]{"no_article"}
        );

        return keyHolder.getKey().intValue();  // Returns the generated ID of the new article
    }


}