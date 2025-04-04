package presentation.Controller;

import metier.Gestion.DocumentGestion;
import metier.Gestion.SeanceGestion;
import metier.Gestion.TacheGestion;
import metier.pojo.Categorie;
import metier.pojo.Projet;
import metier.pojo.Type;
import persistence.*;
import metier.Gestion.ProjetGestion;
import presentation.View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ListeProjetControlleur {
    private static ProjetGestion projetGestion;
    private ListProjetView view;
    private JFrame currentFrame;
    private static ProjetTableModel projetTableModel;


    public ListeProjetControlleur(ListProjetView view, ProjetGestion projetGestion) {
        this.view = view;
        this.projetGestion = projetGestion;
        this.projetTableModel = new ProjetTableModel();
        this.view.setTableModel(projetTableModel);
        this.view.addSearchButtonListener(new SearchButtonListener());
        this.view.addFiltrerButtonListener(new FiltrerButtonListener());
        this.view.addAddButtonListener(new AddButtonListener());
        this.view.addDeleteButtonListener(new DeleteButtonListener());
        this.view.addModifyButtonListener(new ModifyButtonListener());
        this.view.addDuplicateButtonListener(new DuplicateButtonListener());
        this.view.addDReadButtonListener(new ReadButtonListener());


        // Set the table model
        displayProjects();
    }

    class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchText = view.getSearchText();
            List<Projet> projets;
            if (!searchText.isEmpty()) {
                projets = projetGestion.filtrerProjetsParMotCle(searchText);
            } else {
                projets = projetGestion.getAll();
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

            FiltrerProjetControlleur filtrerProjetControlleur = new FiltrerProjetControlleur(new FiltrerProjetView(), projetGestion);
            filtrerProjetControlleur.initialize();

        }
    }


    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentFrame != null) {
                currentFrame.dispose();
            }

            AjoutProjetControlleur ajoutProjetControlleur = new AjoutProjetControlleur(new AjoutProjetView(), projetGestion);
            ajoutProjetControlleur.initialize();
        }
    }

    class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String projectId = view.getSelectedProjectId();
            if (projectId != null) {
                projetGestion.cloturer(projectId);
                displayProjects();
            } else {
                JOptionPane.showMessageDialog(view, "Please select a project.", "No Project Selected", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    class ModifyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String projectId = view.getSelectedProjectId();
            if (projectId != null) {
                Projet projet = projetGestion.read(projectId);
                if (projet != null) {
                    ModifierProjetview modifierView = new ModifierProjetview();
                    ModiferProjetController controller = new ModiferProjetController(modifierView, projetGestion);
                    controller.setProjectInfo(projet);
                    modifierView.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(view, "Project not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(view, "Please select a project.", "No Project Selected", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    class DuplicateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String projectId = view.getSelectedProjectId();
            if (projectId != null) {
                projetGestion.duplicate(projectId);
                displayProjects(); // Mettre à jour l'affichage après la duplication
            } else {
                JOptionPane.showMessageDialog(view, "Please select a project.", "No Project Selected", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    class ReadButtonListener implements ActionListener {
        @Override
            public void actionPerformed(ActionEvent e) {

                    String projectId = view.getSelectedProjectId();
                    if (projectId != null) {
                        ReadProjetController.initialize(projectId);
                    } else {
                        JOptionPane.showMessageDialog(view, "Please select a project.", "No Project Selected", JOptionPane.WARNING_MESSAGE);
                    }
        }
    }


    public  static void initialize() {
        try {
            Connection connection = new Connection();
            ProjetDAO projetDAO = new ProjetDAO(connection.getDatabase());
            ProjetGestion projetGestion = new ProjetGestion(projetDAO);
            ListProjetView listProjetView = new ListProjetView(new MenuControlleur(new MenuView()));

            ListeProjetControlleur controller = new ListeProjetControlleur(listProjetView, projetGestion);
            new MenuControlleur(new MenuView()).showNewFrame(listProjetView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ListProjetView getView() {
        return view;
    }

    public static void displayProjects() {
        List<Projet> projets = projetGestion.getAll();
        projetTableModel.setProjets(projets);
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
        List<Projet> projets = projetGestion.filtrerProjetsParTypeEtCategorie(type,categorie);
        projetTableModel.setProjets(projets);
    }

    public static void main(String[] args) {
        initialize();
    }

}
