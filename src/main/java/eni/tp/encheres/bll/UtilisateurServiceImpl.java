package eni.tp.encheres.bll;

import eni.tp.encheres.bo.Utilisateur;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private List<Utilisateur> utilisateurs = new ArrayList<>();

    @Override
    public void addUtilisateur(Utilisateur utilisateur) {
        utilisateurs.add(utilisateur);
    }

    @Override
    public void removeUtilisateur(int id) {
        utilisateurs.removeIf(u -> u.getNumeroUtilisateur() == id);
    }

    @Override
    public List<Utilisateur> getUtilisateur() {
        return utilisateurs;
    }

    @Override
    public Utilisateur getUtilisateurbyID(int id) {
        return utilisateurs.stream()
                .filter(u -> u.getNumeroUtilisateur() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Utilisateur utilisateur) {
        for (int i = 0; i < utilisateurs.size(); i++) {
            if (utilisateurs.get(i).getNumeroUtilisateur() == utilisateur.getNumeroUtilisateur()) {
                utilisateurs.set(i, utilisateur);
                return;
            }
        }
    }
}
