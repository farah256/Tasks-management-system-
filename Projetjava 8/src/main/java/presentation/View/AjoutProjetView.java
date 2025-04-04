package presentation.View;
import metier.pojo.Type;
import com.toedter.calendar.JDateChooser;
import metier.pojo.Categorie;
import metier.pojo.Projet;
import persistence.ProjetDAO;
import presentation.Controller.AjoutProjetControlleur;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

import static metier.pojo.Type.*;

public class AjoutProjetView extends JFrame {
    private ProjetDAO projetDAO;
    private JButton enregistrer ;
    private JButton retour ;

    private JTextField descriptionField;
    private JDateChooser dateDebut;
    private JDateChooser dateFin;
    private JCheckBox enseignementCheckbox;
    private JCheckBox encadrementCheckbox;
    private JCheckBox pfa;
    private JCheckBox Cours;
    private JCheckBox td;
    private JCheckBox pfe;
    private JCheckBox tp;
    private JCheckBox exam;
    private JCheckBox these;



    public AjoutProjetView() {
        setTitle("Tracker");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
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

        JLabel headerLabel = new JLabel("Ajouter Projet");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        formPanel.add(headerLabel, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx++;
        descriptionField = new JTextField(20);
        formPanel.add(descriptionField, gbc);

        gbc.gridx=0;
        gbc.gridy++;
        formPanel.add(new JLabel("Date début:"), gbc);
        gbc.gridx++;

        dateDebut = createDateChooser();
        formPanel.add(dateDebut, gbc);

        gbc.gridx=0;
        gbc.gridy++;
        formPanel.add(new JLabel("Date fin:"), gbc);
        gbc.gridx++;

        dateFin = createDateChooser();
        formPanel.add(dateFin, gbc);

        gbc.gridx=0;
        gbc.gridy++;
        formPanel.add(new JLabel("Catégorie:"), gbc);
        gbc.gridx++;
        enseignementCheckbox = new JCheckBox("Enseignement");
        formPanel.add(enseignementCheckbox, gbc);
        gbc.gridy++;
        encadrementCheckbox = new JCheckBox("Encadrement");
        formPanel.add(encadrementCheckbox, gbc);
        gbc.gridx=0;
        gbc.gridy++;
        formPanel.add(new JLabel("Type:"), gbc);
        gbc.gridx++;
        Cours = new JCheckBox("Cours");
        formPanel.add(Cours, gbc);
        gbc.gridx++;
        these = new JCheckBox("Thése");
        formPanel.add(these, gbc);
        gbc.gridy++;
        gbc.gridx=1;
        td = new JCheckBox("TD");
        formPanel.add(td, gbc);
        gbc.gridx++;
        pfa = new JCheckBox("PFA");
        formPanel.add(pfa, gbc);
        gbc.gridy++;
        gbc.gridx=1;
        exam = new JCheckBox("Examen");
        formPanel.add(exam, gbc);
        gbc.gridx++;
        pfe = new JCheckBox("PFE");
        formPanel.add(pfe, gbc);
        gbc.gridy++;
        gbc.gridx=1;
        tp = new JCheckBox("TP");
        formPanel.add(tp, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(98, 22, 203,70));

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


    public  JTextField getDescriptionField(){
        return descriptionField;

    }
    public JDateChooser getDateDebut(){
        return dateDebut;
    }
    public JDateChooser getDateFin(){
        return dateFin;
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
        return Cours;
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
            AjoutProjetView frame = new AjoutProjetView();
            frame.setVisible(true);
        });
    }
}
