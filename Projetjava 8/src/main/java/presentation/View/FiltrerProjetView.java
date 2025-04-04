package presentation.View;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;

public class FiltrerProjetView extends JFrame {
    private JButton enregistrerButton;
    private JCheckBox enseignementCheckbox;
    private JCheckBox encadrementCheckbox;
    private JCheckBox pfa;
    private JCheckBox cours;
    private JCheckBox td;
    private JCheckBox pfe;
    private JCheckBox tp;
    private JCheckBox exam;
    private JCheckBox these;

    public FiltrerProjetView() {
        setTitle("Tracker");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
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
        JLabel headerLabel = new JLabel("Filtrer Projet");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        formPanel.add(headerLabel, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Catégorie:"), gbc);
        gbc.gridx++;
        enseignementCheckbox = new JCheckBox("Enseignement");
        formPanel.add(enseignementCheckbox, gbc);
        gbc.gridy++;
        encadrementCheckbox = new JCheckBox("Encadrement");
        formPanel.add(encadrementCheckbox, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Type:"), gbc);
        gbc.gridx++;
        cours = new JCheckBox("Cours");
        formPanel.add(cours, gbc);
        gbc.gridx++;
        these = new JCheckBox("Thèse");
        formPanel.add(these, gbc);
        gbc.gridy++;
        gbc.gridx = 1;
        td = new JCheckBox("TD");
        formPanel.add(td, gbc);
        gbc.gridx++;
        pfa = new JCheckBox("PFA");
        formPanel.add(pfa, gbc);
        gbc.gridy++;
        gbc.gridx = 1;
        exam = new JCheckBox("Examen");
        formPanel.add(exam, gbc);
        gbc.gridx++;
        pfe = new JCheckBox("PFE");
        formPanel.add(pfe, gbc);
        gbc.gridy++;
        gbc.gridx = 1;
        tp = new JCheckBox("TP");
        formPanel.add(tp, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.white);

        enregistrerButton = new JButton("Filtrer");
        buttonPanel.add(enregistrerButton);

        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().add(contentPanel);
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

    public JCheckBox getPfa() {
        return pfa;
    }

    public JCheckBox getCours() {
        return cours;
    }

    public JCheckBox getTd() {
        return td;
    }

    public JCheckBox getPfe() {
        return pfe;
    }

    public JCheckBox getTp() {
        return tp;
    }

    public JCheckBox getExam() {
        return exam;
    }

    public JCheckBox getThese() {
        return these;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FiltrerProjetView frame = new FiltrerProjetView();
            frame.setVisible(true);
        });
    }


}
