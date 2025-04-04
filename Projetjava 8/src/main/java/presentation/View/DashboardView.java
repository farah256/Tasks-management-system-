package presentation.View;

import presentation.Controller.MenuControlleur;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DashboardView extends JFrame {
    private MenuControlleur menuControlleur;
    private Map<String, JLabel> statisticLabels;

    public DashboardView(MenuControlleur menuControlleur) {
        this.menuControlleur = menuControlleur;
        statisticLabels = new HashMap<>();

        setTitle("Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        String s = "Hello, Professeur";
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(d.width / 2 - this.getSize().width / 2, d.height / 2 - this.getSize().height / 2);
        JPanel sidePanel = menuControlleur.getView().createSidePanel();
        JPanel contentPanel = createContentPanel();
        JPanel headerPanel = menuControlleur.getView().createHeaderPanel(s);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(sidePanel, BorderLayout.WEST);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(headerPanel, BorderLayout.NORTH);
    }

    public JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new GridLayout(3, 2, 40, 40));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(120, 40, 120, 40));
        contentPanel.setBackground(new Color(220, 220, 220));
        addStatisticContainer(contentPanel, "Heure de travail par semaine", "0H", new Font("Arial", Font.BOLD, 20));
        addStatisticContainer(contentPanel, "Heure de travail par mois", "0H", new Font("Arial", Font.BOLD, 20));
        addStatisticContainer(contentPanel, "Heure de travail par annee", "0H", new Font("Arial", Font.BOLD, 20));
        addStatisticContainer(contentPanel, "Nombre des Projets", "0", new Font("Arial", Font.BOLD, 20));
        addStatisticContainer(contentPanel, "Nombre des Seances", "0", new Font("Arial", Font.BOLD, 20));
        addStatisticContainer(contentPanel, "Nombre des Tâches", "0", new Font("Arial", Font.BOLD, 20));


        return contentPanel;
    }

    public void addStatisticContainer(JPanel parentPanel, String statistic, String value, Font font) {
        JPanel statContainer = new JPanel();
        statContainer.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        JTextArea statLabel = new JTextArea(statistic);
        statLabel.setWrapStyleWord(true);
        statLabel.setLineWrap(true);
        statLabel.setFont(font);
        statLabel.setOpaque(false); // Makes the background transparent
        statLabel.setEditable(false);
        statLabel.setFocusable(false);

        JLabel statValue = new JLabel(value);
        statValue.setHorizontalAlignment(SwingConstants.CENTER);
        statValue.setFont(font);

        // Store the label in the map for later updates
        statisticLabels.put(statistic, statValue);

        statContainer.setLayout(new BorderLayout());
        statContainer.add(statLabel, BorderLayout.CENTER);
        statContainer.add(statValue, BorderLayout.SOUTH);
        statContainer.setBackground(Color.WHITE);

        parentPanel.add(statContainer);
    }

    public void updateStatistic(String statistic, String value) {
        JLabel label = statisticLabels.get(statistic);
        if (label != null) {
            label.setText(value);
        } else {
            System.err.println("Statistic label not found: " + statistic);
        }
    }

    public static void main(String[] args) {
        MenuView menuView = new MenuView();
        MenuControlleur menuControlleur = new MenuControlleur(menuView);
        DashboardView frame = new DashboardView(menuControlleur);
        frame.setVisible(true);
    }
}
