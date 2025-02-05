package eni.tp.encheres.dal;

import eni.tp.encheres.bo.Enchere;

import java.util.List;

public interface EnchereDAO {
    // Lire les enchères par article
    List<Enchere> readEncheresParArticle(int noArticle);

    // Créer une nouvelle enchère
    void createEnchere(Enchere enchere);
}
