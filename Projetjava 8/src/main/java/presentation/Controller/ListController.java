package presentation.Controller;

import metier.Gestion.*;
import metier.pojo.Categorie;
import metier.pojo.ListeTache;
import metier.pojo.Projet;
import metier.pojo.Type;
import persistence.*;
import presentation.View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ListController {
    private static ListeGestion listeGestion;
    private ListeView view;
    private JFrame currentFrame;
    private static ListTableModel listTableModel;


    public ListController(ListeView view, ListeGestion listeGestion) {
        this.view = view;
        this.listeGestion = listeGestion;
        this.listTableModel = new ListTableModel();
        this.view.setTableModel(listTableModel);
        this.view.addAddButtonListener(new AddButtonListener());
        this.view.addDeleteButtonListener(new DeleteButtonListener());
        this.view.addDReadButtonListener(new ReadButtonListener());

        displayLists();
    }




    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentFrame != null) {
                currentFrame.dispose();
            }
            AjoutListController ajoutListController = new AjoutListController(new AjoutListView(), listeGestion);
            ajoutListController.initialize();
        }
    }

    class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String listID = view.getSelectedProjectId();
            if (listID != null) {
                listeGestion.delete(listID);
                displayLists(); // Mettre à jour l'affichage après la suppression
            } else {
                JOptionPane.showMessageDialog(view, "Please select a project.", "No Project Selected", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    class ReadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String listID = view.getSelectedProjectId();
            if (listID != null) {
                ReadListController.initialize(listID);
            } else {
                JOptionPane.showMessageDialog(view, "Please select a project.", "No Project Selected", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public  static void initialize() {
        try {
            Connection connection = new Connection();
            ListeDAO listeDAO = new ListeDAO(connection.getDatabase());
            ListeGestion listeGestion1 = new ListeGestion(listeDAO);
            ListeView listeView = new ListeView(new MenuControlleur(new MenuView()));

            ListController controller = new ListController(listeView, listeGestion1);
            new MenuControlleur(new MenuView()).showNewFrame(listeView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ListeView getView() {
        return view;
    }

    public static void displayLists() {
        List<ListeTache> listeTaches = listeGestion.getAll();
        listTableModel.setTaches(listeTaches);
    }


    public static void main(String[] args) {
        initialize();
    }

}
