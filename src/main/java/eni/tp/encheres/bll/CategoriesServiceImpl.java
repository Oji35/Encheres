package eni.tp.encheres.bll;


import eni.tp.encheres.bo.Categorie;
import eni.tp.encheres.dal.CategorieDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    private CategorieDAO categorieDAO;
    public CategoriesServiceImpl(CategorieDAO categorieDAO) {
        this.categorieDAO = categorieDAO;
    }


    @Override
    public void addCategorie(Categorie categorie) {
        categorieDAO.createCategorie(categorie);
    }

    @Override
    public void removeCategorie(int id) {
        categorieDAO.deleteCategorie(id);   }

    @Override
    public List<Categorie> getAllCategorie() {
        return categorieDAO.readAllCategories();
    }

    @Override
    public Categorie getCategoriebyID(int id) {
        return categorieDAO.readCategories(id);
    }

    @Override
    public void update(Categorie categorie) {
        categorieDAO.updateCategorie(categorie);
    }
}
