package presentation.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ReadListView extends JFrame {
    private ListeView listeView;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel centerPanel;
    private JTable tacheTable;
    private JButton addTacheButton;
    private JButton retourButton;

    public ReadListView() {
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
        centerPanel.setLayout(new GridLayout(1, 3));

        addTacheButton = new JButton("Ajout Tache");
        retourButton = new JButton("Retour");

        topPanel.add(addTacheButton);
        bottomPanel.add(retourButton);
    }

    private void arrangeComponents() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        TacheTableModel tacheTableModel = new TacheTableModel();
        tacheTable = new JTable(tacheTableModel);
        JPanel tablesPanel = new JPanel(new GridLayout(1, 1));
        JScrollPane tacheScrollPane = new JScrollPane(tacheTable);
        tablesPanel.add(tacheScrollPane);
        getContentPane().add(tablesPanel, BorderLayout.CENTER);
    }


    public void addAddTacheButtonListener(ActionListener listener) {
        addTacheButton.addActionListener(listener);
    }



    public void setTableModel1(TacheTableModel model) {
        tacheTable.setModel(model);
    }

    public void addRetourButtonListener(ActionListener listener) {
        retourButton.addActionListener(listener);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReadListView listView = new ReadListView();
            listView.setVisible(true);
        });
    }
}
