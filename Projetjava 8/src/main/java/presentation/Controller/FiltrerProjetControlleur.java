package presentation.Controller;

import metier.Gestion.ProjetGestion;
import metier.pojo.Categorie;
import metier.pojo.Projet;
import metier.pojo.Type;
import persistence.Connection;
import persistence.ProjetDAO;
import presentation.View.FiltrerProjetView;
import presentation.View.ListProjetView;
import presentation.View.MenuView;
import presentation.View.ProjetTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FiltrerProjetControlleur {
    private FiltrerProjetView view;
    private ProjetGestion projetGestion;

    public FiltrerProjetControlleur(FiltrerProjetView view, ProjetGestion projetGestion) {
        this.view = view;
        this.projetGestion = projetGestion;
        view.getEnregistrerButton().addActionListener(new EnregistrerButtonListener());
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

            List<Projet> projetsFiltres = projetGestion.filtrerProjetsParTypeEtCategorie(type, categorie);
            if (!projetsFiltres.isEmpty()) {
                view.dispose();
                ListeProjetControlleur.displayFilteredProjects(view);
            } else {
                JOptionPane.showMessageDialog(view, "Aucun projet ne correspond aux critères sélectionnés.", "Aucun Résultat", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    public static void initialize() {
        try {
            Connection connection = new Connection();
            ProjetDAO projetDAO = new ProjetDAO(connection.getDatabase());
            ProjetGestion projetGestion1 = new ProjetGestion(projetDAO);
            FiltrerProjetView view = new FiltrerProjetView();
            FiltrerProjetControlleur controller = new FiltrerProjetControlleur(view,projetGestion1);
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        initialize();
    }


}
