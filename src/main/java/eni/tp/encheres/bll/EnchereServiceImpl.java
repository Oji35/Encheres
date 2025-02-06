package eni.tp.encheres.bll;

import eni.tp.encheres.bll.EnchereService;
import eni.tp.encheres.bo.Enchere;
import eni.tp.encheres.bo.EnchereException;
import eni.tp.encheres.bo.Utilisateur;
import eni.tp.encheres.dal.EnchereDAO;
import eni.tp.encheres.dal.EnchereDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EnchereDAO enchereDAO;

    @Override
    public void addEnchere(Enchere enchere) throws EnchereException {
        try {
            String selectQuery = "SELECT montant_enchere FROM ENCHERES WHERE no_article = ?";
            Integer previous = jdbcTemplate.queryForObject(selectQuery, new Object[]{enchere.getNoArticle()}, Integer.class);
            System.out.println("Enchère précédente : " + previous);

                if (enchere.getMontantEnchere() < previous) {
                    throw new EnchereException("Le montant proposé doit être supérieur à " + previous.toString());
                }

                String userQuery = "SELECT credit FROM UTILISATEURS WHERE no_utilisateur = ?";
                Integer credits = jdbcTemplate.queryForObject(userQuery, new Object[]{enchere.getNoUtilisateur()}, Integer.class);
                System.out.println("Crédits : " + credits);

                if (credits == null) {
                    throw new EnchereException("Erreur interne");
                }
                if (enchere.getMontantEnchere() > credits) {
                    throw new EnchereException("Crédit insuffisant");
                }

                // Step 5: Subtract the bid amount from the user's current credit balance
            int newCreditBalance = credits - enchere.getMontantEnchere();
            String updateUserCreditsQuery = "UPDATE UTILISATEURS SET credit = ? WHERE no_utilisateur = ?";
            int rowsUpdated = jdbcTemplate.update(updateUserCreditsQuery, newCreditBalance, enchere.getNoUtilisateur());

                System.out.println("ENCHERE : " + enchere);
                enchereDAO.updateEnchere(enchere);


        } catch (EmptyResultDataAccessException e) {
            // If no previous bid is found, proceed to create the new bid
            System.out.println("No previous bid for this article, creating a new one.");
            String userQuery = "SELECT credit FROM UTILISATEURS WHERE no_utilisateur = ?";
            Integer credits = jdbcTemplate.queryForObject(userQuery, new Object[]{enchere.getNoUtilisateur()}, Integer.class);
            int newCreditBalance = credits - enchere.getMontantEnchere();
            String updateUserCreditsQuery = "UPDATE UTILISATEURS SET credit = ? WHERE no_utilisateur = ?";
            int rowsUpdated = jdbcTemplate.update(updateUserCreditsQuery, newCreditBalance, enchere.getNoUtilisateur());
            enchereDAO.createEnchere(enchere);

        }


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
        System.out.println("ID de getEncherebyID :" + id);
        Enchere enchere = enchereDAO.readEncheresParArticle(id);
        System.out.println(enchere);

            return enchere;
    }

    @Override
    public void update(Enchere enchere) {

    }

    @Override
    public List<Enchere> getAllArticles() {
        return List.of();
    }
}