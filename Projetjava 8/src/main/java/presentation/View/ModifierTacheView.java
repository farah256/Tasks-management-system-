package presentation.View;
import metier.pojo.Type;
import com.toedter.calendar.JDateChooser;
import metier.pojo.Categorie;
import metier.pojo.Projet;
import persistence.ProjetDAO;
import persistence.TacheDAO;
import presentation.Controller.AjoutProjetControlleur;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

import static metier.pojo.Type.*;

public class ModifierTacheView extends JFrame {
    private TacheDAO tacheDAO;
    private JButton modifier ;
    private JButton retour ;

    private JTextField descriptionField;
    private JDateChooser dateDebut;
    private JDateChooser dateFin;
    private JCheckBox finiCheckbox;
    private JCheckBox attentCheckbox;

    public ModifierTacheView() {
        setTitle("Tracker");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 300);
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

        // Header
        JLabel headerLabel = new JLabel("Modifier Tache");
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
        formPanel.add(new JLabel("Etat:"), gbc);
        gbc.gridx++;
        finiCheckbox = new JCheckBox("Finie");
        formPanel.add(finiCheckbox, gbc);
        gbc.gridx++;
        attentCheckbox = new JCheckBox("Attente");
        formPanel.add(attentCheckbox, gbc);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        modifier = new JButton("Modifier");
        retour = new JButton("Retour");


        buttonPanel.add(modifier);
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
    public JButton getModifierButton() {
        return modifier;
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
    public JCheckBox getFiniCheckbox(){
        return finiCheckbox;
    }

    public JCheckBox getAttentCheckbox(){
        return attentCheckbox;
    }






    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModifierTacheView frame = new ModifierTacheView();
            frame.setVisible(true);
        });
    }
}