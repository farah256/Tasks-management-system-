package presentation.View;

import presentation.Controller.MenuControlleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ListeView extends JFrame{
    private MenuView menuView= new MenuView();
    private  JTable table;
   private JButton addButton ;
    private JButton deleteButton ;
    private JButton read;

    public ListeView(MenuControlleur menuControlleur) {
        setTitle("Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        String s = "Listes des projets";

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(d.width/2-this.getSize().width/2, d.height/2-this.getSize().height/2);
        JPanel sidePanel = menuControlleur.getView().createSidePanel(); // Accessing MenuView via MenuController
        JPanel contentPanel = createContentPanel();
        JPanel headerPanel = menuControlleur.getView().createHeaderPanel(s);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(sidePanel, BorderLayout.WEST);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(headerPanel, BorderLayout.NORTH);

    }

    public JPanel createContentPanel(){
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        table = new JTable();
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        addButton = new JButton("Ajouter");
        deleteButton = new JButton("Supprimer");
        read = new JButton("Afficher");

        topPanel.add(addButton);
        topPanel.add(deleteButton);

        topPanel.add(read);

        contentPanel.add(topPanel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        return contentPanel;
    }
    public String getSelectedProjectId() {
        int selectedRow = table.getSelectedRow();
        int projectIdColumnIndex = 0;
        if (selectedRow != -1) {
            return (String) table.getValueAt(selectedRow, projectIdColumnIndex);
        } else {
            return null;
        }
    }


    public static void main(String[] args) {
        ListeView frame = new ListeView(new MenuControlleur(new MenuView()));
        frame.setVisible(true);

    }



    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);

    }

    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);

    }


    public void addDReadButtonListener(ActionListener listener) {
        read.addActionListener(listener);

    }


    public void setTableModel( ListTableModel model) {
        table.setModel(model);
    }

    public JTable getTable() {
        return table;
    }



}
