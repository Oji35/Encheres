package eni.tp.encheres.dal;

// Imports notables : JDBCtemplate, Namedparameter, BenPropertyRowMapper
import eni.tp.encheres.bo.Utilisateur;
import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

    // Variables STRING pour les requêtes SQL
    static final String SELECT_ALL = "SELECT * from UTILISATEURS";
    static final String SELECT_BY_ID = "SELECT * from UTILISATEURS where no_utilisateur=?";
    static final String INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    static final String DELETE = "DELETE FROM UTILISATEURS where no_utilisateur=?";
    static final String UPDATE = "UPDATE UTILISATEURS SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, rue=?, code_postal=?, ville=?, mot_de_passe=?, credit=?, administrateur=? WHERE no_utilisateur=?";


    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public UtilisateurDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    // READ ALL
    @Override
    public List<Utilisateur> readAllUtilisateurs() {
        return jdbcTemplate.query(SELECT_ALL, BeanPropertyRowMapper.newInstance(Utilisateur.class));
    }

    // READ BY ID
    @Override
    public Utilisateur readUtilisateur(int id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID, BeanPropertyRowMapper.newInstance(Utilisateur.class), id);
    }

    // UPDATE
    @Override
    public void updateUtilisateur(Utilisateur utilisateur) {
        jdbcTemplate.update(UPDATE, utilisateur.getPseudo(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getEmail(), utilisateur.getNumeroTelephone(), utilisateur.getCodePostal(), utilisateur.getVille(), utilisateur.getMotDePasse(), utilisateur.getCredit(), utilisateur.isAdmin());
    }

    // REQUETE SQL POUR DELETE
    @Transactional
    @Override
    public void deleteUtilisateur(long id) {
        jdbcTemplate.update(DELETE, id);
    }

    // FONCTION DELETE AVEC APPEL A L'ID
    @Transactional
    @Override
    public void CallIDAndDelete(Utilisateur utilisateur) {
        deleteUtilisateur(utilisateur.getNumeroUtilisateur());
    }

    // CREATE
    @Override
    public int createUtilisateur(Utilisateur utilisateur) {
        var namedparameters = new MapSqlParameterSource();
        namedparameters.addValue("pseudo", utilisateur.getPseudo());
        namedparameters.addValue("nom", utilisateur.getNom());
        namedparameters.addValue("prenom", utilisateur.getPrenom());
        namedparameters.addValue("email", utilisateur.getEmail());
        namedparameters.addValue("telephone", utilisateur.getNumeroTelephone());
        namedparameters.addValue("rue", utilisateur.getRue());
        namedparameters.addValue("code_postal", utilisateur.getCodePostal());
        namedparameters.addValue("ville", utilisateur.getVille());
        namedparameters.addValue("mot_de_passe", utilisateur.getMotDePasse());
        namedparameters.addValue("credit", utilisateur.getCredit());
        namedparameters.addValue("administrateur", utilisateur.isAdmin());

        // Pour générer un no_utilisateur automatiquement
        // En lien avec l'auto-incrémentation de la colonne no_utilisateur (IDENTITY) dans SQL
        var keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                INSERT,
                namedparameters,
                keyHolder,
                new String[]{"no_utilisateur"}
        );

        return keyHolder.getKey().intValue();  // Returns the generated ID of the new user
    }

}
