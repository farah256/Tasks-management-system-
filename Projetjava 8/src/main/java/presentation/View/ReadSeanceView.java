package presentation.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ReadSeanceView extends JFrame {
    private ListProjetView listProjetView;
    private JPanel topPanel;
    private JPanel bottomPanal;

    private JPanel centerPanel;
    private JTable tacheTable;
    private JTable documentTable;
    private JButton addDocumentButton ;
    private JButton addTacheButton;
    private JButton deleteButton;
    private JButton retour;
    private JTextField searchBar;
    private JButton searchButton;



    public ReadSeanceView() {
        setTitle("Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);

        initComponents();
        arrangeComponents();
    }

    private void initComponents() {
        topPanel = new JPanel();
        bottomPanal = new JPanel();
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 3));
        addDocumentButton = new JButton("Ajout Document");
        addTacheButton = new JButton("Ajout Tache");
        deleteButton = new JButton("Supprimer");
        retour = new JButton("Retour");

        topPanel.add(addDocumentButton);
        topPanel.add(addTacheButton);
        topPanel.add(deleteButton);
        bottomPanal.add(retour);
    }

    private void arrangeComponents() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanal,BorderLayout.SOUTH);
        TacheTableModel tacheTableModel = new TacheTableModel();
        SeanceTableModel seanceTableModel = new SeanceTableModel();
        DocumentTableModel documentTableModel = new DocumentTableModel();
        tacheTable = new JTable(tacheTableModel);
        documentTable = new JTable(documentTableModel);
        JPanel tablesPanel = new JPanel(new GridLayout(1, 2));
        JScrollPane tacheScrollPane = new JScrollPane(tacheTable);
        JScrollPane documentScrollPane = new JScrollPane(documentTable);
        tablesPanel.add(tacheScrollPane);
        tablesPanel.add(documentScrollPane);

        getContentPane().add(tablesPanel, BorderLayout.CENTER);
    }
    public String getSelectedTacheId() {
        int selectedRow = tacheTable.getSelectedRow();
        int tacheIdColumnIndex = 0;
        if (selectedRow != -1) {
            return (String) tacheTable.getValueAt(selectedRow, tacheIdColumnIndex);
        } else {
            return null;
        }
    }

    public String getSelectedDocumentId() {
        int selectedRow = documentTable.getSelectedRow();
        int documentIdColumnIndex = 0;
        if (selectedRow != -1) {
            return (String) documentTable.getValueAt(selectedRow, documentIdColumnIndex);
        } else {
            return null;
        }
    }
    public void addSearchButtonListener(ActionListener listener) {

        searchButton.addActionListener(listener);

    }
    public String getSearchText() {
        return searchBar.getText();
    }

    public void addDocumentButtonListener(ActionListener listener) {
        addDocumentButton.addActionListener(listener);
    }

    public void addAddTacheButtonListener(ActionListener listener) {
        addTacheButton.addActionListener(listener);
    }


    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

     public void setTableModel1( TacheTableModel model) {
        tacheTable.setModel(model);
    }
    public void setTableModel3( DocumentTableModel model) {
        documentTable.setModel(model);
    }
    public void addRetourButtonListener(ActionListener listener) {
        retour.addActionListener(listener);
    }
    public JTable getDocumentTable() {
        return documentTable;
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReadProjetView readPrrojetView = new ReadProjetView();
            readPrrojetView.setVisible(true);
        });
    }
}


