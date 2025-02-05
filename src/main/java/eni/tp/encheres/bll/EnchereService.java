package eni.tp.encheres.bll;

import eni.tp.encheres.bo.Enchere;

import java.util.List;

public interface EnchereService {
    // Récupérer toutes les enchères pour un article donné
    List<Enchere> listerEncheresParArticle(int noArticle);

    // Ajouter une nouvelle enchère
    void proposerEnchere(Enchere enchere);

    // Récupérer la meilleure enchère pour un article
    Enchere getMeilleureEnchereParArticle(int noArticle);

    // Cette méthode ne doit pas avoir de corps dans l'interface
    Enchere getMeilleureEnchere(int articleId);
}
