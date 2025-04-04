package presentation.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;

public class HistoriqueTacheView extends JFrame {
    MenuView menuView = new MenuView();
    private  JTextField searchBar;
    private JTable table;

    private JButton searchButton ;
    private JButton filtrerButton ;

    public HistoriqueTacheView(MenuView menuView) {
        setTitle("Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        String s = "Listes d'historique des tâches";

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(d.width/2-this.getSize().width/2, d.height/2-this.getSize().height/2);
        JPanel sidePanel = menuView.createSidePanel();
        JPanel contentPanel = createContentPanel();
        JPanel headerPanel = menuView.createHeaderPanel(s);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(sidePanel, BorderLayout.WEST);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(headerPanel, BorderLayout.NORTH);

    }

    public JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchBar = new JTextField(10);
        topPanel.add(searchBar);
        searchButton = new JButton("Rechercher");
        filtrerButton = new JButton("Filtrer");

        topPanel.add(searchButton);
        topPanel.add(filtrerButton);


        contentPanel.add(topPanel, BorderLayout.NORTH);



        table = new JTable();



        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        return contentPanel;
    }

    public void addSearchButtonListener(ActionListener listener) {

        searchButton.addActionListener(listener);

    }
    public String getSearchText() {
        return searchBar.getText();
    }
    public void addFiltrerButtonListener(ActionListener listener) {
        filtrerButton.addActionListener(listener);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HistoriqueProjetView frame = new HistoriqueProjetView(new MenuView());
            frame.setVisible(true);
        });
    }
    public void setTableModel( TacheTableModel model) {
        table.setModel(model);
    }

    public JTable getTable() {
        return table;
    }
}
