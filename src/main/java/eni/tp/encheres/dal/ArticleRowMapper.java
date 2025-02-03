package eni.tp.encheres.dal;

import eni.tp.encheres.bo.ArticleVendu;
import eni.tp.encheres.bo.Categorie;
import eni.tp.encheres.bo.Utilisateur;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ArticleRowMapper implements RowMapper<ArticleVendu> {
    @Override
    public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {

        Utilisateur user = new Utilisateur();
        user.setPseudo(rs.getString("utilisateur_pseudo"));
        user.setNom(rs.getString("utilisateur_nom"));
        user.setPrenom(rs.getString("utilisateur_prenom"));

        Categorie categorie = new Categorie();
        categorie.setLibelle(rs.getString("categorie_libelle"));

        ArticleVendu articleVendu = new ArticleVendu();
        articleVendu.setNoArticle(rs.getInt("no_article"));
        articleVendu.setNomArticle(rs.getString("nom_article"));
        articleVendu.setDescription(rs.getString("description"));
        articleVendu.setDateDebutEncheres(rs.getTimestamp("date_debut_encheres").toLocalDateTime().toLocalDate());
        articleVendu.setDateFinEncheres(rs.getTimestamp("date_fin_encheres").toLocalDateTime().toLocalDate());
        articleVendu.setPrix_initial(rs.getLong("prix_initial"));
        articleVendu.setPrixVente(rs.getLong("prix_vente"));
        articleVendu.setUtilisateur(user);
        articleVendu.setCategorie(categorie);

        return articleVendu;
    }
}

