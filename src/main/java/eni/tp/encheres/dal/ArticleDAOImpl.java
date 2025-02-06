package eni.tp.encheres.dal;

import eni.tp.encheres.bo.ArticleVendu;
import org.springframework.dao.EmptyResultDataAccessException;
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
    static final String SELECT_ALL = "SELECT \n" +
            "    a.no_article,\n" +
            "    a.nom_article,\n" +
            "    a.description,\n" +
            "    a.date_debut_encheres,\n" +
            "    a.date_fin_encheres,\n" +
            "    a.prix_initial,\n" +
            "    a.prix_vente,\n" +
            "    u.pseudo AS utilisateur_pseudo,\n" +
            "    u.nom AS utilisateur_nom,\n" +
            "    u.prenom AS utilisateur_prenom,\n" +
            " c.no_categorie AS categorie_no, " +
            "    c.libelle AS categorie_libelle\n" +
            "FROM \n" +
            "    ARTICLES_VENDUS a\n" +
            "JOIN \n" +
            "    UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur\n" +
            "JOIN \n" +
            "    CATEGORIES c ON a.no_categorie = c.no_categorie";

    static final String SELECT_BY_ID =  "SELECT " +
            "a.no_article, " +
            "a.nom_article, " +
            "a.description, " +
            "a.date_debut_encheres, " +
            "a.date_fin_encheres, " +
            "a.prix_initial, " +
            "a.prix_vente, " +
            "u.pseudo AS utilisateur_pseudo, " +
            "u.nom AS utilisateur_nom, " +
            "u.prenom AS utilisateur_prenom, " +
            " c.no_categorie AS categorie_no, " +
            "c.libelle AS categorie_libelle " +
            "FROM ARTICLES_VENDUS a " +
            "JOIN " +
            "UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur " +
            "JOIN " +
            "CATEGORIES c ON a.no_categorie = c.no_categorie " +
            "WHERE a.no_article = ?";
//            "SELECT * from ARTICLES_VENDUS where no_article=?";
    static final String INSERT = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie)";
    static final String DELETE = "DELETE FROM ARTICLES_VENDUS where no_article=?";
    static final String UPDATE = "UPDATE ARTICLES_VENDUS SET nom_article=?, description=?, date_debut_encheres=?, date_fin_encheres=?, prix_initial=?, prix_vente=?, no_utilisateur=?, no_categorie=? WHERE no_article=?";


    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    JdbcTemplate jdbcTemplate;
    private ArticleRowMapper articleRowMapper;


    public ArticleDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate, ArticleRowMapper articleRowMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
        this.articleRowMapper = articleRowMapper;
    }

    // READ ALL
    @Override
    public List<ArticleVendu> readAllArticles() {
        return jdbcTemplate.query(SELECT_ALL, articleRowMapper);
    }

    // READ BY ID
    @Override
    public ArticleVendu readArticle(long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, BeanPropertyRowMapper.newInstance(ArticleVendu.class), id);
        } catch (EmptyResultDataAccessException e) {
            // Handle the case where no article was found (could return null or throw a custom exception)
            return null;
        }
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
        namedparameters.addValue("no_utilisateur", article.getUtilisateur().getNo_Utilisateur());
        namedparameters.addValue("no_categorie", article.getNoCategorie());


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