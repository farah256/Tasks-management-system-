package presentation.Controller;

import metier.Gestion.DocumentGestion;
import metier.Gestion.ProjetGestion;
import metier.Gestion.SeanceGestion;
import metier.Gestion.TacheGestion;

import metier.pojo.*;
import persistence.*;
import presentation.View.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import static presentation.Controller.ListeProjetControlleur.displayProjects;

public class ListeTacheController {
    private static TacheGestion tacheGestion;
    private ListTacheView view;
    private JFrame currentFrame;
    private static TacheTableModel tacheTableModel;

    public ListeTacheController(ListTacheView view, TacheGestion tacheGestion) {
        this.view = view;
        this.tacheGestion = tacheGestion;
        this.tacheTableModel = new TacheTableModel();
        this.view.setTableModel(tacheTableModel);
        this.view.addSearchButtonListener(new ListeTacheController.SearchButtonListener());
        this.view.addFiltrerButtonListener(new ListeTacheController.FiltrerButtonListener());
        this.view.addAddButtonListener(new ListeTacheController.AddButtonListener());
        this.view.addDeleteButtonListener(new ListeTacheController.DeleteButtonListener());
        this.view.addModifyButtonListener(new ListeTacheController.ModifyButtonListener());
        this.view.addDuplicateButtonListener(new ListeTacheController.DuplicateButtonListener());
        this.view.addDReadButtonListener(new ListeTacheController.ReadButtonListener());


        // Set the table model
        displayTaches();
    }
    class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchText = view.getSearchText();
            List<Tache> taches;
            if (!searchText.isEmpty()) {
                taches = tacheGestion.filtrerProjetsParMotCle(searchText);
            } else {
                taches = tacheGestion.getAll();
            }

            if (!taches.isEmpty()) {
                tacheTableModel.setTaches(taches);
            } else {
                JOptionPane.showMessageDialog(view, "No taches found.", "No Taches Found", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    class FiltrerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            FiltrerTacheController filtrertachesControlleur = new FiltrerTacheController(new FiltrerTacheView(), tacheGestion);
            filtrertachesControlleur.initialize();

        }
    }


    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentFrame != null) {
                currentFrame.dispose();
            }
            AjoutTacheControlleur ajoutTacheControlleur = new AjoutTacheControlleur(new AjoutTacheView(), tacheGestion,"null","null","null");
            ajoutTacheControlleur.initialize("null","null","null"); // Vous pouvez avoir une méthode initialize() dans AjoutProjetControlleur pour afficher la vue
        }



    }

    class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String TacheId = view.getSelectedIdTaches();
            if (TacheId != null) {
                tacheGestion.cloturer(TacheId);
                displayTaches();
            } else {
                JOptionPane.showMessageDialog(view, "Please select a tache.", "No tache Selected", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    class ModifyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String tacheId = view.getSelectedIdTaches();
            if (tacheId != null) {
                Tache tache = tacheGestion.read(tacheId);
                if (tache != null) {
                    ModifierTacheView modifierView = new ModifierTacheView();
                    ModifierTacheController controller = new ModifierTacheController(modifierView, tacheGestion);
                    controller.setProjectInfo(tache);
                    modifierView.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(view, "Tache not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(view, "Please select a tache.", "No tache Selected", JOptionPane.WARNING_MESSAGE);
            }
        }
    }


    class DuplicateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String TacheId = view.getSelectedIdTaches();
            if (TacheId != null) {
                tacheGestion.duplicate(TacheId);
                displayTaches();
            } else {
                JOptionPane.showMessageDialog(view, "Please select a tache", "No tache Selected", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    class ReadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String tacheid = view.getSelectedIdTaches();
            if (tacheid != null) {
                Tache tache = tacheGestion.read(tacheid);
                if (tache != null) {
                    ReadTacheController.initialize(tacheid);
                } else {
                    JOptionPane.showMessageDialog(view, "Project not found.", "Project Not Found", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(view, "Please select a project.", "No Project Selected", JOptionPane.WARNING_MESSAGE);
            }


        }
    }

    public  static void initialize() {
        try {
            Connection connection = new Connection();
            TacheDAO tacheDAO = new TacheDAO(connection.getDatabase());
            TacheGestion tacheGestion1 = new TacheGestion(tacheDAO);
            ListTacheView listTacheView = new ListTacheView(new MenuControlleur(new MenuView()).getView());

            ListeTacheController controller = new ListeTacheController(listTacheView, tacheGestion1);
            new MenuControlleur(new MenuView()).showNewFrame(listTacheView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ListTacheView getView() {
        return view;
    }

    public static void displayTaches() {
        List<Tache> taches= tacheGestion.getAll();
        tacheTableModel.setTaches(taches);
    }


    public static void displayFilteredTaches(FiltrerTacheView view) {
        // Récupérer la catégorie sélectionnée par l'utilisateur
        Categorie categorie = null;
        if (view.getEnseignementCheckbox().isSelected()) {
            categorie = Categorie.Enseignement;
        } else if (view.getEncadrementCheckbox().isSelected()) {
            categorie = Categorie.Encadrement;
        }

        // Récupérer le type sélectionné par l'utilisateur
        Etat etat=null;
        if (view.getAttente().isSelected()){
            etat=Etat.attente;
        } else if (view.getFinie().isSelected()) {
            etat=Etat.finie;
        }
        // Récupérer la date sélectionnée par l'utilisateur

        List<Tache> taches = tacheGestion.filtrerTaches(etat,categorie);
        tacheTableModel.setTaches(taches);
    }






    public static void main(String[] args) {
        initialize();
    }




}
