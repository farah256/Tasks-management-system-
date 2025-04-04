package presentation.Controller;

import metier.Gestion.HistoriqueGestion;
import metier.Gestion.ProjetGestion;
import metier.pojo.Categorie;
import metier.pojo.Projet;
import metier.pojo.Type;
import persistence.Connection;
import persistence.HistoriqueProjetDAO;
import persistence.ProjetDAO;
import presentation.View.FiltrerProjetView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FiltrerhistoriqueProjet {

    private FiltrerProjetView view;
    private HistoriqueGestion historiqueGestion;
    public FiltrerhistoriqueProjet(FiltrerProjetView view, HistoriqueGestion historiqueGestion) {
        this.view = view;
        this.historiqueGestion = historiqueGestion;
        view.getEnregistrerButton().addActionListener(new FiltrerhistoriqueProjet.EnregistrerButtonListener());
    }

    class EnregistrerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Récupérer la catégorie sélectionnée par l'utilisateur
            Categorie categorie = null;
            if (view.getEnseignementCheckbox().isSelected()) {
                categorie = Categorie.Enseignement;
            } else if (view.getEncadrementCheckbox().isSelected()) {
                categorie = Categorie.Encadrement;
            }

            // Récupérer le type sélectionné par l'utilisateur
            Type type = null;
            if (view.getCours().isSelected()) {
                type = Type.Cours;
            } else if (view.getThese().isSelected()) {
                type = Type.These;
            } else if (view.getTd().isSelected()) {
                type = Type.TD;
            } else if (view.getPfa().isSelected()) {
                type = Type.PFA;
            } else if (view.getExam().isSelected()) {
                type = Type.Examen;
            } else if (view.getPfe().isSelected()) {
                type = Type.PFE;
            } else if (view.getTp().isSelected()) {
                type = Type.TP;
            }

            List<Projet> projetsFiltres = historiqueGestion.filtrerProjetsParTypeEtCategorie(type, categorie);
            if (!projetsFiltres.isEmpty()) {
                view.dispose();
                HistoriqueProjetControlleur.displayFilteredProjects(view);
            } else {
                JOptionPane.showMessageDialog(view, "Aucun projet ne correspond aux critères sélectionnés.", "Aucun Résultat", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    public static void initialize() {
        try {
            Connection connection = new Connection();
            HistoriqueProjetDAO historiqueDAO = new HistoriqueProjetDAO(connection.getDatabase());
            HistoriqueGestion projetGestion1 = new HistoriqueGestion(historiqueDAO);
            FiltrerProjetView view = new FiltrerProjetView();
            FiltrerhistoriqueProjet controller = new FiltrerhistoriqueProjet(view,projetGestion1);
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        initialize();
    }


}



