package presentation.Controller;

import metier.Gestion.HistoriqueGestion;
import metier.Gestion.ProjetGestion;
import metier.pojo.Categorie;
import metier.pojo.Projet;
import metier.pojo.Type;
import persistence.Connection;
import persistence.HistoriqueProjetDAO;
import persistence.ProjetDAO;
import presentation.View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HistoriqueProjetControlleur {
    private static HistoriqueGestion historiqueGestion;
    private static HistoriqueProjetView view;
    private static ProjetTableModel projetTableModel;

    public HistoriqueProjetControlleur(HistoriqueProjetView view, HistoriqueGestion historiqueGestion) {
        this.view = view;
        this.historiqueGestion = historiqueGestion;
        this.projetTableModel = new ProjetTableModel();
        this.view.setTableModel(projetTableModel);
        this.view.addSearchButtonListener(new HistoriqueProjetControlleur.SearchButtonListener());
        this.view.addFiltrerButtonListener(new HistoriqueProjetControlleur.FiltrerButtonListener());
        displayHistoriqueProjects();
    }

    public static void initialize() {
        try {
            Connection connection = new Connection();
            HistoriqueProjetDAO historiqueProjetDAO = new HistoriqueProjetDAO(connection.getDatabase());
            HistoriqueGestion historiqueGestion1 = new HistoriqueGestion(historiqueProjetDAO);
            HistoriqueProjetView historiqueProjetView = new HistoriqueProjetView(new MenuControlleur(new MenuView()).getView());

            HistoriqueProjetControlleur controller = new HistoriqueProjetControlleur( historiqueProjetView, historiqueGestion1 );
            new MenuControlleur(new MenuView()).showNewFrame( historiqueProjetView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HistoriqueProjetView getView() {
        return view;
    }


    public static void displayHistoriqueProjects() {
        List<Projet> projets = historiqueGestion.getAllProject();
        projetTableModel.setProjets(projets); // Met à jour le modèle de tableau avec les nouveaux projets
    }
    class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchText = view.getSearchText();
            List<Projet> projets;
            if (!searchText.isEmpty()) {
                projets = historiqueGestion.filtrerProjetsParMotCle(searchText);
            } else {
                projets = historiqueGestion.getAllProject();
            }
            if (!projets.isEmpty()) {
                projetTableModel.setProjets(projets);
            } else {
                JOptionPane.showMessageDialog(view, "No projects found.", "No Projects Found", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    class FiltrerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            FiltrerhistoriqueProjet filtrerProjetControlleur = new FiltrerhistoriqueProjet(new FiltrerProjetView(), historiqueGestion);
            filtrerProjetControlleur.initialize();

        }
    }
    public static void displayFilteredProjects(FiltrerProjetView view) {
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
        List<Projet> projets = historiqueGestion.filtrerProjetsParTypeEtCategorie(type,categorie);
        projetTableModel.setProjets(projets);
    }



    public static void main(String[] args) {
        initialize();
    }
}




