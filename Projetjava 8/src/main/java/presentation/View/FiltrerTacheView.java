package presentation.View;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class FiltrerTacheView extends JFrame {
    JDateChooser dateDebutChooser;
    private JButton enregistrerButton;
    private JCheckBox finie;
    private JCheckBox attente;
    private JCheckBox enseignementCheckbox;
    private JCheckBox encadrementCheckbox;
    public FiltrerTacheView() {
        setTitle("Tracker");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setResizable(false);
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(d.width/2-this.getSize().width/2, d.height/2-this.getSize().height/2);
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        JLabel headerLabel = new JLabel("Filtrer Taches");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        formPanel.add(headerLabel, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Catégorie:"), gbc);
        gbc.gridx++;
        enseignementCheckbox = new JCheckBox("Enseignement");
        formPanel.add(enseignementCheckbox, gbc);
        gbc.gridx++;
        encadrementCheckbox = new JCheckBox("Encadrement");
        formPanel.add(encadrementCheckbox, gbc);
        gbc.gridx = 0;

        gbc.gridy++;
        formPanel.add(new JLabel("Etat:"), gbc);
        gbc.gridx++;
        attente = new JCheckBox("En Attente");
        formPanel.add(attente, gbc);
        gbc.gridx++;

        finie = new JCheckBox("Finie");
        formPanel.add(finie, gbc);
        gbc.gridx=0;
        gbc.gridy++;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.white);

        enregistrerButton = new JButton("Filtrer");


        buttonPanel.add(enregistrerButton);


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
        return enregistrerButton;
    }

    public JCheckBox getEnseignementCheckbox() {
        return enseignementCheckbox;
    }

    public JCheckBox getEncadrementCheckbox() {
        return encadrementCheckbox;
    }


    public JCheckBox getFinie() {
        return finie;
    }



    public JCheckBox getAttente() {
        return attente;
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FiltrerTacheView frame = new FiltrerTacheView();
            frame.setVisible(true);
        });

    }


}
