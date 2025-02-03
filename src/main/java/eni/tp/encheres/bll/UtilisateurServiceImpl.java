package eni.tp.encheres.bll;

import eni.tp.encheres.bo.Utilisateur;
import eni.tp.encheres.dal.UtilisateurDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurDAO utilisateurDAO;
    private Utilisateur utilisateur;
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Utilisateur getUtilisateurByUsername(String username) {
        String query = "SELECT * FROM UTILISATEURS WHERE pseudo = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{username},
                BeanPropertyRowMapper.newInstance(Utilisateur.class));
    }

    @Override
    public void addUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.createUtilisateur(utilisateur);

        System.out.println("UtilisateurService : " + utilisateur);
    }

    @Override
    public void removeUtilisateur(int id) {
        utilisateurDAO.deleteUtilisateur(id);
    }

    @Override
    public List<Utilisateur> getAllUtilisateur() {
        return utilisateurDAO.readAllUtilisateurs();
    }

    @Override
    public Utilisateur getUtilisateurbyID(int id) {
        return utilisateurDAO.readUtilisateur(id);
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

