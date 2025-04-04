package presentation.View;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;


public class AjoutDocumentView extends JFrame {
    private JButton enregistrer;
    private JButton retour;

    private JTextField nom;
    private JDateChooser dateAjout;

    private JButton importButton;
    private String url;


    public AjoutDocumentView() {
        setTitle("Tracker");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 300);
        setResizable(false);
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(d.width / 2 - this.getSize().width / 2, d.height / 2 - this.getSize().height / 2);
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Header
        JLabel headerLabel = new JLabel("Ajouter Document");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        formPanel.add(headerLabel, gbc);

        // Nom input
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Nom:"), gbc);
        gbc.gridx++;

        nom = new JTextField(20);
        formPanel.add(nom, gbc);

        // Date d'ajout input
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Date d'ajout:"), gbc);
        gbc.gridx++;

        dateAjout = createDateChooser();
        formPanel.add(dateAjout, gbc);

        // File input
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Importer un fichier:"), gbc);
        gbc.gridx++;
        importButton = new JButton("Importer");
        formPanel.add(importButton, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(98, 22, 203, 70));

        enregistrer = new JButton("Enregistrer");
        retour = new JButton("Retour");

        buttonPanel.add(enregistrer);
        buttonPanel.add(retour);

        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().add(contentPanel);
    }

    private JDateChooser createDateChooser() {
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd-MM-yyyy HH:mm");
        dateChooser.setDate(Calendar.getInstance().getTime());
        return dateChooser;
    }

    public JButton getEnregistrerButton() {
        return enregistrer;
    }

    public JButton getRetourButton() {
        return retour;
    }

    public JButton getImportButton() {
        return importButton;
    }

    public JTextField getNom() {
        return nom;
    }

    public JDateChooser getDateAjout() {
        return dateAjout;
    }

    public String getFileUrl() {
        return url;
    }

    public void setFileUrl(String url) {
        this.url = url;
    }

    public static void main(String[] args) {
            AjoutDocumentView frame = new AjoutDocumentView();
            frame.setVisible(true);

    }
}
