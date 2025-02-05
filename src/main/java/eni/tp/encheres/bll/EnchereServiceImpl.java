package eni.tp.encheres.bll;

import eni.tp.encheres.bo.Enchere;
import eni.tp.encheres.dal.EnchereDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService {

    private final EnchereDAO enchereDAO;

    @Autowired
    public EnchereServiceImpl(EnchereDAO enchereDAO) {
        this.enchereDAO = enchereDAO;
    }

    @Override
    public List<Enchere> listerEncheresParArticle(int noArticle) {
        return enchereDAO.readEncheresParArticle(noArticle);
    }

    @Override
    public void proposerEnchere(Enchere enchere) {
        enchereDAO.createEnchere(enchere);
    }

    @Override
    public Enchere getMeilleureEnchereParArticle(int noArticle) {
        List<Enchere> encheres = enchereDAO.readEncheresParArticle(noArticle);
        return encheres.stream()
                .max((e1, e2) -> Integer.compare(e1.getMontantEnchere(), e2.getMontantEnchere()))
                .orElse(null);
    }

    @Override
    public Enchere getMeilleureEnchere(int articleId) {
        List<Enchere> encheres = enchereDAO.readEncheresParArticle(articleId);
        return encheres.stream()
                .max(Comparator.comparingInt(Enchere::getMontantEnchere))
                .orElse(null);
    }
}
