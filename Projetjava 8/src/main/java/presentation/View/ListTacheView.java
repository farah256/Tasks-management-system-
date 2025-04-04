package presentation.View;

import presentation.Controller.MenuControlleur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;

public class ListTacheView extends JFrame {
    MenuView menuView=new MenuView();
    private  JTable table;
    private JButton searchButton ;
    private JButton filtrerButton ;
    private JButton addButton ;
    private JButton deleteButton ;
    private JButton modifyButton;
    private JButton duplicateButton ;
    private JButton read;
    private JButton refresh;
    private  JTextField searchBar;

    public ListTacheView(MenuView menuView) {
        setTitle("Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        String s = "Listes des tâches";
        JPanel sidePanel = menuView.createSidePanel();
        JPanel contentPanel = createContentPanel();
        JPanel headerPanel = menuView.createHeaderPanel(s);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(d.width/2-this.getSize().width/2, d.height/2-this.getSize().height/2);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(sidePanel, BorderLayout.WEST);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(headerPanel, BorderLayout.NORTH);

    }

    public JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Panel for search bar and buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Search bar
        searchBar = new JTextField(10);
        topPanel.add(searchBar);

        // Buttons
        searchButton = new JButton("Rechercher");
        filtrerButton = new JButton("Filtrer");
        addButton = new JButton("Ajouter");
        deleteButton = new JButton("Supprimer");
        modifyButton = new JButton("Modifier");
        duplicateButton = new JButton("Cloner");
        read = new JButton("Afficher");

        topPanel.add(searchButton);
        topPanel.add(filtrerButton);
        topPanel.add(addButton);
        topPanel.add(deleteButton);
        topPanel.add(modifyButton);
        topPanel.add(duplicateButton);
        topPanel.add(read);

        contentPanel.add(topPanel, BorderLayout.NORTH);

        // Initialize the table
        table = new JTable();

        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        return contentPanel;
    }

    public String getSelectedProjectId() {
        int selectedRow = table.getSelectedRow();
        int tacheIdColumnIndex = 0;
        if (selectedRow != -1) {
            return (String) table.getValueAt(selectedRow, tacheIdColumnIndex);
        } else {
            return null;
        }
    }

    public void addSearchButtonListener(ActionListener listener) {

        searchButton.addActionListener(listener);

    }

    public void addFiltrerButtonListener(ActionListener listener) {
        filtrerButton.addActionListener(listener);

    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);

    }

    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);

    }

    public void addModifyButtonListener(ActionListener listener) {
        modifyButton.addActionListener(listener);

    }

    public void addDuplicateButtonListener(ActionListener listener) {
        duplicateButton.addActionListener(listener);

    }
    public void addDReadButtonListener(ActionListener listener) {
        read.addActionListener(listener);

    }

    public String getSearchText() {
        return searchBar.getText();
    }

    public void setTableModel( TacheTableModel model) {
        table.setModel(model);
    }
    public String getSelectedIdTaches() {
        int selectedRow = table.getSelectedRow();
        int tacheIdColumnIndex = 0;
        if (selectedRow != -1) {
            return (String) table.getValueAt(selectedRow, tacheIdColumnIndex);
        } else {
            return null;
        }
    }

    public JTable getTable() {
        return table;
    }
    public static void main(String[] args) {
        ListTacheView frame = new ListTacheView(new MenuControlleur(new MenuView()).getView());
        frame.setVisible(true);

    }
}







