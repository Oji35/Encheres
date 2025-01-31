package eni.tp.encheres.dal;

import eni.tp.encheres.bo.Categorie;
import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategorieDAOImpl implements CategorieDAO{

        // Variables STRING pour les requêtes SQL
        static final String SELECT_ALL = "SELECT * from CATEGORIES";
        static final String SELECT_BY_ID = "SELECT * from CATEGORIES where no_categorie=?";
        static final String INSERT = "INSERT INTO CATEGORIES (libelle) VALUES (?)";
        static final String DELETE = "DELETE FROM CATEGORIES where no_categorie=?";
        static final String UPDATE = "UPDATE CATEGORIES SET libelle=? WHERE no_categorie=?";


        JdbcTemplate jdbcTemplate;
        NamedParameterJdbcTemplate namedParameterJdbcTemplate;


        public CategorieDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
            this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        }


        // READ ALL
        @Override
        public List<Categorie> readAllCategories() {
            return jdbcTemplate.query(SELECT_ALL, BeanPropertyRowMapper.newInstance(Categorie.class));
        }

        // READ BY ID
        @Override
        public Categorie readCategories(int id) {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, BeanPropertyRowMapper.newInstance(Categorie.class), id);
        }

        // UPDATE
        @Override
        public void updateCategorie(Categorie categorie ) {
            jdbcTemplate.update(UPDATE, categorie.getNoCategorie(), categorie.getLibelle());
        }

        // REQUETE SQL POUR DELETE
        @Transactional
        @Override
        public void deleteCategorie(int id) {
            jdbcTemplate.update(DELETE, id);
        }

        // FONCTION DELETE AVEC APPEL A L'ID
        @Transactional
        @Override
        public void CallIDAndDelete(Categorie categorie) {
            deleteCategorie(categorie.getNoCategorie());
        }

        // CREATE
        @Override
        public int createCategorie(Categorie Categorie) {
            var namedparameters = new MapSqlParameterSource();
            namedparameters.addValue("libelle", Categorie.getLibelle());


            // Pour générer un no_utilisateur automatiquement
            // En lien avec l'auto-incrémentation de la colonne no_utilisateur (IDENTITY) dans SQL
            var keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(
                    INSERT,
                    namedparameters,
                    keyHolder,
                    new String[]{"no_categorie"}
            );

            return keyHolder.getKey().intValue();  // Returns the generated ID of the new user
        }

    }

