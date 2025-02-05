package eni.tp.encheres.bll;

import eni.tp.encheres.bll.EnchereService;
import eni.tp.encheres.bo.Enchere;
import eni.tp.encheres.bo.EnchereException;
import eni.tp.encheres.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addEnchere(Enchere enchere) throws EnchereException {
        String selectQuery = "SELECT montant_enchere FROM ENCHERES WHERE no_article = ?";
        Integer previous = jdbcTemplate.queryForObject(selectQuery, new Object[]{enchere.getId()}, Integer.class);

        if (previous == null) {
            throw new EnchereException("Erreur interne");
        }
        if (enchere.getMontantEnchere() < previous) {
            throw new EnchereException("Le montant proposé doit être supérieur à " + previous.toString());
        }

        String userQuery = "SELECT credit FROM UTILISATEURS WHERE no_utilisateur = ?";
        Integer credits = jdbcTemplate.queryForObject(userQuery, new Object[]{enchere.getNumeroUtilisateur()}, Integer.class);
        if (credits == null) {
            throw new EnchereException("Erreur interne");
        }
        if (enchere.getMontantEnchere() > credits) {
            throw new EnchereException("Crédit insuffisant");
        }

        String query = "INSERT INTO ENCHERES (no_utilisateur,no_article, date_enchere, montant_enchere) VALUES (?,?,?,?)";
        jdbcTemplate.update(query,enchere.getNumeroUtilisateur(), enchere.getId(), enchere.getDateEnchere(), enchere.getMontantEnchere() );
    }

    @Override
    public void removeEnchere(int id) {
//        jdbcTemplate.update();
    }

    @Override
    public List<Enchere> getEnchere() {
        return List.of();
    }

    @Override
    public Enchere getEncherebyID(int id) {
        return null;
    }

    @Override
    public void update(Enchere enchere) {

    }

    @Override
    public List<Enchere> getAllArticles() {
        return List.of();
    }
}