package eni.tp.encheres.dal;

import eni.tp.encheres.bo.Enchere;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnchereDAOImpl implements EnchereDAO {

    private final JdbcTemplate jdbcTemplate;

    // Requêtes SQL
    static final String SELECT_BY_ARTICLE = "SELECT * FROM ENCHERES WHERE no_article = ?";
    static final String INSERT_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, ?, ?)";

    public EnchereDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Enchere> readEncheresParArticle(int noArticle) {
        return jdbcTemplate.query(SELECT_BY_ARTICLE, new BeanPropertyRowMapper<>(Enchere.class), noArticle);
    }

    @Override
    public void createEnchere(Enchere enchere) {
        jdbcTemplate.update(INSERT_ENCHERE,
                enchere.getNoUtilisateur(),
                enchere.getNoArticle(),
                enchere.getDateEnchere(),
                enchere.getMontantEnchere()
        );
    }
}
