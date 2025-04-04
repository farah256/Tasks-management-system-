package presentation.Controller;

import metier.Gestion.ListeGestion;
import metier.pojo.ListeTache;
import persistence.Connection;
import persistence.ListeDAO;
import presentation.View.AjoutListView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AjoutListController {
    private AjoutListView view;
    private ListeGestion listeGestion;



    public AjoutListController(AjoutListView view,ListeGestion listeGestion) {
        this.listeGestion = listeGestion;
        this.view = view;
        view.getEnregistrerButton().addActionListener(new AjoutListController.EnregistrerButtonListener());
        view.getRetourButton().addActionListener(new AjoutListController.RetourButtonListener());


    }



    class EnregistrerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String description = view.getDescriptionField().getText();
            ListeTache listeTache = new ListeTache(description);

            if (listeGestion.create(listeTache)) {
                view.dispose();
                JOptionPane.showMessageDialog(view, "La liste  s'ajoute correctement dans la base de donné", "Succe", JOptionPane.INFORMATION_MESSAGE);
                view.getDescriptionField().setText("");
                ListController.displayLists();
            } else {
                JOptionPane.showMessageDialog(view, "la liste ne s'ajoute pas correctement dans la base de donné.", "Erreur", JOptionPane.ERROR_MESSAGE);
                ListController.displayLists();

            }
        }
    }

    class RetourButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose(); // Fermer la fenêtre d'ajout de projet
        }
    }



    public static void initialize() {
        try {
            Connection connection = new Connection();
            ListeDAO listeDAO = new ListeDAO(connection.getDatabase());
            ListeGestion listeGestion1 = new ListeGestion(listeDAO);
            AjoutListView view = new AjoutListView();
            AjoutListController controller = new AjoutListController(view,listeGestion1);

            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initialize();
    }
}

