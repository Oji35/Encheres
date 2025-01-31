package eni.tp.encheres.bll;

import eni.tp.encheres.bo.Utilisateur;
import eni.tp.encheres.dal.UtilisateurDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

    @Service
    public class UtilisateurServiceImpl implements UtilisateurService {

        private UtilisateurDAO utilisateurDAO;
        private List<Utilisateur> utilisateurs = new ArrayList<>();

        public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO) {this.utilisateurDAO = utilisateurDAO;}

        @Override
        public void addUtilisateur(Utilisateur utilisateur) {
            utilisateurDAO.createUtilisateur(utilisateur);
            
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

