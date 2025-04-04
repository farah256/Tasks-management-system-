package presentation.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ReadTacheView extends JFrame {
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel centerPanel;
    private JButton addDocumentButton;
    private JButton retourButton;
    private JTable documentTable;
    private JButton deleteButton;
    private JTextField searchBar;
    private JButton searchButton;


    public ReadTacheView() {
        setTitle("Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);

        initComponents();
        arrangeComponents();
    }

    private void initComponents() {
        topPanel = new JPanel();
        bottomPanel = new JPanel();
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 3)); // Three tables will be displayed side by side

        // Add buttons for adding document, tache, seance, etc. to the top panel
        addDocumentButton = new JButton("Ajout Document");
        deleteButton = new JButton("Supprimer");
        retourButton = new JButton("Retour");


        // Add components to the top panel
        topPanel.add(addDocumentButton);
        topPanel.add(deleteButton);
        bottomPanel.add(retourButton);
    }

    private void arrangeComponents() {
        getContentPane().setLayout(new BorderLayout());

        // Add the top and bottom panels to the frame
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        // Initialize table models

        // Initialize JTables with table models
        documentTable = new JTable();

        // Add the center panel to display the table
        JPanel tablesPanel = new JPanel(new GridLayout(1, 1));
        JScrollPane tacheScrollPane = new JScrollPane(documentTable); // Create JScrollPane for the first table
        tablesPanel.add(tacheScrollPane);

        getContentPane().add(tablesPanel, BorderLayout.CENTER);
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
    public void addAdddocumentButtonListener(ActionListener listener) {
        addDocumentButton.addActionListener(listener);
    }


    public void addRetourButtonListener(ActionListener listener) {
        retourButton.addActionListener(listener);
    }
    public void setTableModel3( DocumentTableModel model) {
        documentTable.setModel(model);
    }
    public JTable getDocumentTable() {
        return documentTable;
    }
    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReadListView listView = new ReadListView();
            listView.setVisible(true);
        });
    }
}
