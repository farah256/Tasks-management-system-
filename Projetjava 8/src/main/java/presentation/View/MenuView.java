package presentation.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuView extends JFrame {
    private  JButton dash;
    private  JButton calendrier;
    private  JButton projet;
    private  JButton tache;
    private  JButton tacheList;
    private  JButton projetHistorique;
    private  JButton tacheHistorique;



    public MenuView() {
            setTitle("Tracker");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1200, 800);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(d.width/2-this.getSize().width/2, d.height/2-this.getSize().height/2);
            dash = new JButton("Dashboard");
            calendrier = new JButton("Calendrier");
            projet = new JButton("Projets");
            tache = new JButton("Taches");
            tacheList = new JButton("Listes des Taches");
            projetHistorique = new JButton("Historique des Projets");
            tacheHistorique = new JButton("Historique des Taches");

    }
    public JPanel createSidePanel(){

        JPanel sidePanel = new JPanel(new GridBagLayout());
        sidePanel.setBackground(new Color(10,100,100));
        sidePanel.setPreferredSize(new Dimension(250, getHeight()));


        GridBagConstraints gbcDash = new GridBagConstraints();
        gbcDash.gridx = 0;
        gbcDash.gridy = 0;
        gbcDash.insets = new Insets(0, 0, 10, 0);
        gbcDash.fill = GridBagConstraints.HORIZONTAL;
        gbcDash.weightx = 1.0;
        gbcDash.anchor = GridBagConstraints.NORTH;
        sidePanel.add(dash, gbcDash);
        GridBagConstraints gbcCal = new GridBagConstraints();
        gbcCal.gridx = 0;
        gbcCal.gridy = 1;
        gbcCal.insets = new Insets(0, 0, 10, 0);
        gbcCal.fill = GridBagConstraints.HORIZONTAL;
        gbcCal.weightx = 1.0; // Equal width for buttons
        gbcCal.anchor = GridBagConstraints.NORTH;
        sidePanel.add(calendrier, gbcCal);
        GridBagConstraints gbcProjects = new GridBagConstraints();
        gbcProjects.gridx = 0;
        gbcProjects.gridy = 2;
        gbcProjects.insets = new Insets(0, 0, 10, 0);
        gbcProjects.fill = GridBagConstraints.HORIZONTAL;
        gbcProjects.weightx = 1.0; // Equal width for buttons
        gbcProjects.anchor = GridBagConstraints.NORTH;
        sidePanel.add(projet, gbcProjects);
        GridBagConstraints gbcTasks = new GridBagConstraints();
        gbcTasks.gridx = 0;
        gbcTasks.gridy = 3;
        gbcTasks.insets = new Insets(0, 0, 10, 0);
        gbcTasks.fill = GridBagConstraints.HORIZONTAL;
        gbcTasks.weightx = 1.0; // Equal width for buttons
        gbcTasks.anchor = GridBagConstraints.NORTH;
        sidePanel.add(tache, gbcTasks);
        GridBagConstraints gbcList = new GridBagConstraints();
        gbcList.gridx = 0;
        gbcList.gridy = 4;
        gbcList.insets = new Insets(0, 0, 10, 0);
        gbcList.fill = GridBagConstraints.HORIZONTAL;
        gbcList.weightx = 1.0; // Equal width for buttons
        gbcList.anchor = GridBagConstraints.NORTH;
        sidePanel.add(tacheList, gbcList);
        GridBagConstraints gbcph = new GridBagConstraints();
        gbcph.gridx = 0;
        gbcph.gridy = 5;
        gbcph.insets = new Insets(0, 0, 10, 0);
        gbcph.fill = GridBagConstraints.HORIZONTAL;
        gbcph.weightx = 1.0; // Equal width for buttons
        gbcph.anchor = GridBagConstraints.NORTH;
        sidePanel.add(projetHistorique, gbcph);
        GridBagConstraints gbcth = new GridBagConstraints();
        gbcth.gridx = 0;
        gbcth.gridy = 6;
        gbcth.insets = new Insets(0, 0, 10, 0);
        gbcth.fill = GridBagConstraints.HORIZONTAL;
        gbcth.weightx = 1.0;
        gbcth.anchor = GridBagConstraints.NORTH;
        sidePanel.add(tacheHistorique, gbcth);
             return sidePanel;
    }
    public JPanel createHeaderPanel(String s){
        JPanel headerPanel = new JPanel();
        JLabel Label = new JLabel(s);
        Label.setFont(new Font("Arial", Font.BOLD, 20));
        Label.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(Label);

        return headerPanel;
    }
    public void initialize(){
        String s = "Welcome";
        JPanel sidePanel = createSidePanel();
        JPanel headerPanel = createHeaderPanel(s);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(sidePanel, BorderLayout.WEST);
        getContentPane().add(headerPanel, BorderLayout.NORTH);
    }
    public void addDashboardButtonListener(ActionListener listener) {
        dash.addActionListener(listener);
    }

    public void addCalendrierButtonListener(ActionListener listener) {
        calendrier.addActionListener(listener);
    }
    public void addProjetButtonListener(ActionListener listener) {
        projet.addActionListener(listener);
    }
    public void addTacheButtonListener(ActionListener listener) {
        tache.addActionListener(listener);
    }
    public void addListeButtonListener(ActionListener listener) {
        tacheList.addActionListener(listener);
    }
    public void addHistoriqueProjetButtonListener(ActionListener listener) {
        projetHistorique.addActionListener(listener);
    }
    public void addHistoriqueTacheButtonListener(ActionListener listener) {
        tacheHistorique.addActionListener(listener);
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuView frame = new MenuView();
            frame.initialize();
            frame.setVisible(true);
        });
    }
}
