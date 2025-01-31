package eni.tp.encheres.dal;

import eni.tp.encheres.bo.Categorie;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CategorieDAO {

    int createCategorie(Categorie Categorie);

    Categorie readCategories(int id);

    List<Categorie> readAllCategories();

    void CallIDAndDelete(Categorie Categorie);

    void updateCategorie(Categorie categorie);

    void deleteCategorie(int id);


}
