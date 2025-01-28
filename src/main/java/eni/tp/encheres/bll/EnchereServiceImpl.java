package eni.tp.encheres.bll;

import eni.tp.encheres.bo.Enchere;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService {

    private final List<Enchere> enchereList = new ArrayList<>();

    @Override
    public void addEnchere(Enchere enchere) {
        enchereList.add(enchere);
    }

    @Override
    public void removeEnchere(int id) {
        enchereList.removeIf(e -> e.getId() == id);
    }

    @Override
    public List<Enchere> getEnchere() {
        return enchereList;
    }

    @Override
    public Enchere getEncherebyID(int id) {
        return enchereList.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void update(Enchere enchere) {
        for (int i = 0; i < enchereList.size(); i++) {
            if (enchereList.get(i).getId() == enchere.getId()) {
                enchereList.set(i, enchere);
                return;
            }
        }
    }

    @Override
    public List<Enchere> getAllArticles() {
        return List.of();
    }
}
