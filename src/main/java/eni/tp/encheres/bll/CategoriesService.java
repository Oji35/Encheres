package eni.tp.encheres.bll;

import eni.tp.encheres.bo.Categorie;
import eni.tp.encheres.bo.Utilisateur;

import java.util.List;

public interface CategoriesService {

    void addCategorie(Categorie categorie);

    void removeCategorie(int id);

    List<Categorie> getAllCategorie();

    Categorie getCategoriebyID(int id);

    void update(Categorie categorie);




}
