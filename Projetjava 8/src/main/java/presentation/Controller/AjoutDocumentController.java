package presentation.Controller;

import metier.Gestion.DocumentGestion;
import metier.pojo.Document;
import org.bson.types.Binary;
import persistence.Connection;
import persistence.DocumentDAO;
import presentation.View.AjoutDocumentView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

public class AjoutDocumentController {
    private AjoutDocumentView view;
    private DocumentGestion documentGestion;
    private String projectId;
    private String seanceId;
    private String tacheId;

    public AjoutDocumentController(AjoutDocumentView view, DocumentGestion documentGestion, String projectId, String tacheId, String seanceId) {
        this.view = view;
        this.documentGestion = documentGestion;
        this.projectId = projectId;
        this.tacheId = tacheId;
        this.seanceId = seanceId;
        view.getEnregistrerButton().addActionListener(new EnregistrerButtonListener());
        view.getRetourButton().addActionListener(new RetourButtonListener());
        view.getImportButton().addActionListener(new ImportButtonListener());
    }

    class EnregistrerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nom = view.getNom().getText();
            Date dateAjout = view.getDateAjout().getDate();
            String url = view.getFileUrl();

            Document document = new Document(nom, dateAjout, url);
            document.setIdProjet(projectId);
            document.setIdSeance(seanceId);
            document.setIdTache(tacheId);

            if (documentGestion.create(document)) {
                view.dispose();
                JOptionPane.showMessageDialog(view, "The document was successfully added to the database.", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.getNom().setText("");
                view.getDateAjout().setDate(null);
                view.setFileUrl("");
                ReadTacheController.displayDocuments(tacheId);
                ReadProjetController.displayDocumentsprojet(projectId);
                ReadSeanceController.displayDocuments(seanceId);
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add the document.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ImportButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a Document");

            int userSelection = fileChooser.showOpenDialog(view);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToImport = fileChooser.getSelectedFile();
                String fileUrl = fileToImport.getAbsolutePath();
                view.setFileUrl(fileUrl);
            }
        }
    }

    class RetourButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
        }

    }

    public static void initialize(String projectId, String tacheId, String seanceId) {
        try {
            Connection connection = new Connection();
            DocumentDAO documentDAO = new DocumentDAO(connection.getDatabase());
            DocumentGestion documentGestion = new DocumentGestion(documentDAO);
            AjoutDocumentView view = new AjoutDocumentView();
            AjoutDocumentController controller = new AjoutDocumentController(view, documentGestion, projectId, tacheId, seanceId);
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String projectId = "664631be372d8629a18d24d4";
        initialize(projectId, "null", "null");
    }
}
