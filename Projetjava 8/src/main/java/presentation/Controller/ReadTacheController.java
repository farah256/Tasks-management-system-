package presentation.Controller;

import metier.Gestion.DocumentGestion;
import metier.Gestion.SeanceGestion;
import metier.Gestion.TacheGestion;
import metier.pojo.Document;
import metier.pojo.Seance;
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

public class ReadTacheController {
    private ReadTacheView view;
    private static DocumentGestion documentGestion;

    private static DocumentTableModel documentTableModel;
    private String tacheId;

    public ReadTacheController() {}

    public ReadTacheController(ReadTacheView view,  DocumentGestion documentGestion, String tacheId) {
        this.view = view;
        this.tacheId = tacheId;
        this.documentGestion = documentGestion;
        this.documentTableModel = new DocumentTableModel();
        this.view.setTableModel3(documentTableModel);
        view.addAdddocumentButtonListener(new AddDocumentButtonListener());
        view.addDeleteButtonListener(new DeleteButtonListener());
        view.addRetourButtonListener(new RetourButtonListener());
        view.getDocumentTable().addMouseListener(new fileButtonListener() );


        displayDocuments(tacheId);
    }


    class AddDocumentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AjoutDocumentController controller = new AjoutDocumentController(new AjoutDocumentView(), documentGestion, "null",tacheId,"null"); // Pass the project ID
            controller.initialize("null",tacheId,"null");
        }
    }

    class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedDocumentId = view.getSelectedDocumentId();

            if (selectedDocumentId != null) {
                documentGestion.delete(selectedDocumentId);
                displayDocuments(tacheId);
            }else {
                JOptionPane.showMessageDialog(view, "Please select an item to perform this action.", "No Item Selected", JOptionPane.ERROR_MESSAGE);
            }
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



    public static void displayDocuments(String tacheId) {
        List<Document> documents = documentGestion.getDocumentsByTaskId(tacheId);
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

    public static void initialize(String tacheId) {
        try {
            Connection connection = new Connection();
            TacheDAO tacheDAO = new TacheDAO(connection.getDatabase());
            DocumentDAO documentDAO = new DocumentDAO(connection.getDatabase());
            TacheGestion tacheGestion = new TacheGestion(tacheDAO);
            DocumentGestion documentGestion = new DocumentGestion(documentDAO);
            ReadTacheView readTacheView = new ReadTacheView();
            ReadTacheController readTacheController = new ReadTacheController(readTacheView, documentGestion, tacheId);
            readTacheView.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String seaceId = null;
        initialize(seaceId);
    }
}
