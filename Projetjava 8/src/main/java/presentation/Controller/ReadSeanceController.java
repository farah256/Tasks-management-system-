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

public class ReadSeanceController {
    private ReadSeanceView view;
    private static TacheGestion tacheGestion;
    private static DocumentGestion documentGestion;

    private static TacheTableModel tacheTableModel;
    private static DocumentTableModel documentTableModel;
    private String seanceId;

    public ReadSeanceController() {}

    public ReadSeanceController(ReadSeanceView view, TacheGestion tacheGestion, DocumentGestion documentGestion, String seanceId) {
        this.view = view;
        this.seanceId = seanceId;
        this.tacheGestion = tacheGestion;
        this.documentGestion = documentGestion;
        this.tacheTableModel = new TacheTableModel();
        this.view.setTableModel1(tacheTableModel);
        this.documentTableModel = new DocumentTableModel();
        this.view.setTableModel3(documentTableModel);
        view.addDocumentButtonListener(new AddDocumentButtonListener());
        view.addDeleteButtonListener(new DeleteButtonListener());
        view.addRetourButtonListener(new RetourButtonListener());
        view.getDocumentTable().addMouseListener(new fileButtonListener() );
        view.addAddTacheButtonListener((new TacheButtonListener()));


        displayTaches(seanceId);
        displayDocuments(seanceId);
    }


    class AddDocumentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AjoutDocumentController controller = new AjoutDocumentController(new AjoutDocumentView(), documentGestion, "null","null",seanceId); // Pass the project ID
            controller.initialize("null","null",seanceId);
        }
    }
    class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedTacheId = view.getSelectedTacheId();
            String selectedDocumentId = view.getSelectedDocumentId();

            if (selectedTacheId != null) {
                tacheGestion.cloturer(selectedTacheId);
                displayTaches(seanceId);
            }  else if (selectedDocumentId != null) {
                documentGestion.delete(selectedDocumentId);
                displayDocuments(seanceId);
            } else {
                JOptionPane.showMessageDialog(view, "Please select an item to perform this action.", "No Item Selected", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    class TacheButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AjoutTacheControlleur ajoutTacheControlleur = new AjoutTacheControlleur(new AjoutTacheView(), tacheGestion,"null", "null", seanceId);
            ajoutTacheControlleur.initialize("null", "null", seanceId); // Vous pouvez avoir une méthode initialize() dans AjoutProjetControlleur pour afficher la vue

        }
    }


    class RetourButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();

        }
    }

    class fileButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2 && view.getDocumentTable().getSelectedRow() != -1) {
                int selectedRow = view.getDocumentTable().getSelectedRow();
                DocumentTableModel model = (DocumentTableModel) view.getDocumentTable().getModel();
                Document selectedDocument = model.getDocumentAt(selectedRow);
                openDocument(selectedDocument);
            }
        }
    }



    public static void displayDocuments(String seanceId) {
        List<Document> documents = documentGestion.getDocumentsBySeanceId(seanceId);
        documentTableModel.setDocuments(documents);
    }
    private void openDocument(Document document) {
        try {
            if (document.getUrl() != null) {
                File file = new File(document.getUrl());
                if (file.exists()) {
                    Desktop.getDesktop().open(file);
                } else {
                    JOptionPane.showMessageDialog(view, "File not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(view, "No file URL specified.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error opening file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void initialize(String seanceId) {
        try {
            Connection connection = new Connection();
            TacheDAO tacheDAO = new TacheDAO(connection.getDatabase());
            DocumentDAO documentDAO = new DocumentDAO(connection.getDatabase());
            TacheGestion tacheGestion = new TacheGestion(tacheDAO);
            DocumentGestion documentGestion = new DocumentGestion(documentDAO);
            ReadSeanceView readSeanceView = new ReadSeanceView();
            ReadSeanceController readSeanceController = new ReadSeanceController(readSeanceView, tacheGestion, documentGestion, seanceId);
            readSeanceView.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void displayTaches(String seanceId) {
        List<Tache> taches = tacheGestion.getTachesBySeanceId(seanceId);

        tacheTableModel.setTaches(taches);
    }

    public static void main(String[] args) {
        String seaceId = null;
        initialize(seaceId);
    }
}
