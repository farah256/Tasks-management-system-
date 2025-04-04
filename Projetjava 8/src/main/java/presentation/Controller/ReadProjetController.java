package presentation.Controller;

import metier.Gestion.DocumentGestion;
import metier.Gestion.SeanceGestion;
import metier.Gestion.TacheGestion;
import metier.pojo.Document;
import metier.pojo.Projet;
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
import java.util.ArrayList;
import java.util.List;

public class ReadProjetController {
    private ReadProjetView view;
    private static TacheGestion tacheGestion;
    private static SeanceGestion seanceGestion;
    private static DocumentGestion documentGestion;

    private static TacheTableModel tacheTableModel;
    private static SeanceTableModel seanceTableModel;
    private static DocumentTableModel documentTableModel;
    private String projetId;


    public ReadProjetController() {}

    public ReadProjetController(ReadProjetView view, TacheGestion tacheGestion, SeanceGestion seanceGestion, DocumentGestion documentGestion, String projetId) {
        this.view = view;
        this.projetId = projetId;
        this.tacheGestion = tacheGestion;
        this.seanceGestion = seanceGestion;
        this.documentGestion = documentGestion;
        this.tacheTableModel = new TacheTableModel();
        this.view.setTableModel1(tacheTableModel);
        this.seanceTableModel = new SeanceTableModel();
        this.view.setTableModel2(seanceTableModel);
        this.documentTableModel = new DocumentTableModel();
        this.view.setTableModel3(documentTableModel);

        // Add action listeners to the buttons
        view.addDocumentButtonListener(new AddDocumentButtonListener());
        view.addSeanceButtonListener(new AddSeanceButtonListener());
        view.addDeleteButtonListener(new DeleteButtonListener());
        view.addModifyButtonListener(new ModifyButtonListener());
        view.addDuplicateButtonListener(new DuplicateButtonListener());
        view.addRetourButtonListener(new RetourButtonListener());
        view.addReadButtonListener(new ReadButtonListener());
        view.getDocumentTable().addMouseListener(new fileButtonListener() );
        view.addAddTacheButtonListener(new addAddTacheButtonListener());
        view.addAddStaticButton(new StasticButtonListener());


        displayTachesprojet(projetId);
        displaySeance(projetId);
        displayDocumentsprojet(projetId);
    }

    class StasticButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            InfoProjetController.initialize(projetId);
        }
    }
    // Action listener for adding a document
    class AddDocumentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AjoutDocumentController controller = new AjoutDocumentController(new AjoutDocumentView(), documentGestion, projetId,"null","null"); // Pass the project ID
            controller.initialize(projetId,"null","null");        }
    }

    // Action listener for adding a seance
    class AddSeanceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AjoutSeanceControlleur ajoutSeanceControlleur = new AjoutSeanceControlleur(new AjoutSeanceView(), seanceGestion, projetId);
            ajoutSeanceControlleur.initialize(projetId);
        }
    }
    class addAddTacheButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AjoutTacheControlleur ajoutTacheControlleur = new AjoutTacheControlleur(new AjoutTacheView(), tacheGestion,projetId, "null", "null");
            ajoutTacheControlleur.initialize(projetId, "null", "null");
        }
    }

    // Action listener for deleting
    class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedTacheId = view.getSelectedTacheId();
            String selectedSeanceId = view.getSelectedSeanceId();
            String selectedDocumentId = view.getSelectedDocumentId();

            if (selectedTacheId != null) {
                tacheGestion.cloturer(selectedTacheId);
                displayTachesprojet(projetId);
            } else if (selectedSeanceId != null) {
                seanceGestion.delete(selectedSeanceId);
                displaySeance(projetId);
            } else if (selectedDocumentId != null) {
                documentGestion.delete(selectedDocumentId);
                displayDocumentsprojet(projetId);
            } else {
                JOptionPane.showMessageDialog(view, "Please select an item to perform this action.", "No Item Selected", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class ModifyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedTacheId = view.getSelectedTacheId();
            String selectedSeanceId = view.getSelectedSeanceId();
            String selectedDocumentId = view.getSelectedDocumentId();

            if (selectedTacheId != null) {
                JOptionPane.showMessageDialog(view, "Please select another item to perform this action.", "No Item Selected", JOptionPane.ERROR_MESSAGE);
            }else if (selectedSeanceId != null) {
                Seance selectedSeance = seanceGestion.read(selectedSeanceId);
                if (selectedSeance != null) {
                    ModifierSeanceView modifyView = new ModifierSeanceView();
                    ModifierSeanceController controller = new ModifierSeanceController(modifyView, seanceGestion,projetId);
                    controller.setSeanceInfo(selectedSeance);
                    modifyView.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(view, "Selected session not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (selectedDocumentId != null) {
                JOptionPane.showMessageDialog(view, "Please select another item to perform this action.", "No Item Selected", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, "Please select an item to perform this action.", "No Item Selected", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class DuplicateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedTacheId = view.getSelectedTacheId();
            String selectedSeanceId = view.getSelectedSeanceId();
            String selectedDocumentId = view.getSelectedDocumentId();

            if (selectedTacheId != null) {
                tacheGestion.duplicate(selectedTacheId);
                displayTachesprojet(projetId);
            } else if (selectedSeanceId != null) {
                JOptionPane.showMessageDialog(view, "Please select another item to perform this action.", "No Item Selected", JOptionPane.ERROR_MESSAGE);
            } else if (selectedDocumentId != null) {
                JOptionPane.showMessageDialog(view, "Please select another item to perform this action.", "No Item Selected", JOptionPane.ERROR_MESSAGE);
            } else {
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
    class ReadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
                String selectedTacheId = view.getSelectedTacheId();
                String selectedSeanceId = view.getSelectedSeanceId();
                String selectedDocumentId = view.getSelectedDocumentId();

                if (selectedTacheId != null) {
                 ReadTacheController.initialize(selectedTacheId);
                } else if (selectedSeanceId != null) {
                    ReadSeanceController.initialize(selectedSeanceId);
                } else if (selectedDocumentId != null) {
                    JOptionPane.showMessageDialog(view, "Please select another item to perform this action.", "No Item Selected", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view, "Please select an item to perform this action.", "No Item Selected", JOptionPane.ERROR_MESSAGE);
                }
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

    public static void displaySeance(String projetId) {
        List<Seance> seances = seanceGestion.getSeancesByProjectId(projetId);
        seanceTableModel.setSeances(seances);
    }


    public static void displayTachesprojet(String projetId) {
        List<Tache> taches = tacheGestion.getTachesByProjectId(projetId);
        tacheTableModel.setTaches(taches);
    }


    public static void displayDocumentsprojet(String projetId) {
        List<Document> documents = documentGestion.getDocumentsByProjectId(projetId);
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

    public static void initialize(String projectId) {
        try {
            Connection connection = new Connection();
            TacheDAO tacheDAO = new TacheDAO(connection.getDatabase());
            SeanceDAO seanceDAO = new SeanceDAO(connection.getDatabase());
            DocumentDAO documentDAO = new DocumentDAO(connection.getDatabase());
            TacheGestion tacheGestion = new TacheGestion(tacheDAO);
            SeanceGestion seanceGestion = new SeanceGestion(seanceDAO);
            DocumentGestion documentGestion = new DocumentGestion(documentDAO);
            ReadProjetView readProjetView = new ReadProjetView();
            ReadProjetController readProjetController = new ReadProjetController(readProjetView, tacheGestion, seanceGestion, documentGestion, projectId);
            readProjetView.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String projectId = null;
        initialize(projectId);
    }
}
