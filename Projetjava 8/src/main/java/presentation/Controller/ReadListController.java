package presentation.Controller;

import metier.Gestion.DocumentGestion;
import metier.Gestion.SeanceGestion;
import metier.Gestion.TacheGestion;
import metier.pojo.Document;
import metier.pojo.Seance;
import metier.pojo.Tache;
import persistence.*;
import presentation.View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

public class ReadListController {
    private ReadListView view;
    private static TacheGestion tacheGestion;

    private static TacheTableModel tacheTableModel;
    private String listeId;

    public ReadListController() {}

    public ReadListController(ReadListView view, TacheGestion tacheGestion, String listeId) {
        this.view = view;
        this.listeId = listeId;
        this.tacheGestion = tacheGestion;
        this.tacheTableModel = new TacheTableModel();
        this.view.setTableModel1(tacheTableModel);
        view.addAddTacheButtonListener(new AddTacheButtonListener());
        view.addRetourButtonListener(new RetourButtonListener());

        displayTachesListes(listeId);

    }


    class AddTacheButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AjoutTacheControlleur controller = new AjoutTacheControlleur(new AjoutTacheView(), tacheGestion, "null",listeId,"null"); // Pass the project ID
            controller.initialize("null",listeId,"null");
        }
    }



    class RetourButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();

        }
    }
    public static void displayTachesListes(String listeId) {
        List<Tache> taches = tacheGestion.getTachesByListId(listeId);
        tacheTableModel.setTaches(taches);  // Met à jour le modèle de table avec les nouvelles tâches
    }


    public static void initialize(String seanceId) {
        try {
            Connection connection = new Connection();
            TacheDAO tacheDAO = new TacheDAO(connection.getDatabase());
            TacheGestion tacheGestion = new TacheGestion(tacheDAO);
            ReadListView readListView = new ReadListView();
            ReadListController readListController = new ReadListController(readListView, tacheGestion, seanceId);
            readListView.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String seaceId = null;
        initialize(seaceId);
    }
}