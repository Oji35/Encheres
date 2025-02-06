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
    static final String UPDATE = "UPDATE ENCHERES SET no_utilisateur=?, no_article=?, date_enchere=?, montant_enchere=? WHERE no_utilisateur=? AND no_article=?";

    public EnchereDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Enchere readEncheresParArticle(int id) {
        System.out.println(id);
        List<Enchere> encheres = jdbcTemplate.query(SELECT_BY_ARTICLE, new BeanPropertyRowMapper<>(Enchere.class), id);

        // Return the first result if it exists, or null if no results
        if (encheres != null && !encheres.isEmpty()) {
            return encheres.get(0);  // Return the first bid if found
        } else {
            return null;  // Return null if no bids were found
        }
    }

    @Override
    public void createEnchere(Enchere enchere) {
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(enchere.getDateEnchere().getTime());
        jdbcTemplate.update(INSERT_ENCHERE,
                enchere.getNoUtilisateur(),
                enchere.getNoArticle(),
                sqlTimestamp,
                enchere.getMontantEnchere()

        );
    }

    @Override
    public void updateEnchere (Enchere enchere) {
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(enchere.getDateEnchere().getTime());
        jdbcTemplate.update((UPDATE),
                enchere.getNoUtilisateur(),
                enchere.getNoArticle(),
                sqlTimestamp,
                enchere.getMontantEnchere(),
                enchere.getNoUtilisateur(),
                enchere.getNoArticle()

        );
    }

}
