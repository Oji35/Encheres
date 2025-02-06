package eni.tp.encheres.dal;

import eni.tp.encheres.bo.Enchere;

import java.util.List;

public interface EnchereDAO {
    // Lire les enchères par article
    Enchere readEncheresParArticle(int noArticle);

    // Créer une nouvelle enchère
    void createEnchere(Enchere enchere);

    void updateEnchere(Enchere enchere);
}
