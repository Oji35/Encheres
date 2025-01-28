package eni.tp.encheres.bll;

import eni.tp.encheres.bo.Enchere;
import org.springframework.stereotype.Service;
import java.util.*;
import eni.tp.encheres.bo.Enchere;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public interface EnchereService {

    void addEnchere(Enchere enchere);

    void removeEnchere(int id);

    List<Enchere> getEnchere();

    Enchere getEncherebyID(int id);

    void update(Enchere enchere);
}
