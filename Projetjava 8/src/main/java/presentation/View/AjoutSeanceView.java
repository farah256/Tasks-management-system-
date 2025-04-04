package presentation.View;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class AjoutSeanceView extends JFrame{
    private JButton enregistrer ;
    private JButton retour ;
    private JTextField descriptionField;
    private JDateChooser dateDebut;
    private JDateChooser dateFin;
    private JTextArea note;
    public AjoutSeanceView() {
        setTitle("Tracker");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
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
        JLabel headerLabel = new JLabel("Ajouter Séance");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        formPanel.add(headerLabel, gbc);

        // Description input
        gbc.gridy++;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx++;
         descriptionField = new JTextField(25);
        formPanel.add(descriptionField, gbc);

        // Date debut input
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
        formPanel.add(new JLabel("Note:"), gbc);
        gbc.gridx++;
         note = new JTextArea(5, 20);
        note.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(note);
        formPanel.add(scrollPane, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(98, 22, 203,70));

         enregistrer = new JButton("Enregistrer");
         retour = new JButton("Retour");

        buttonPanel.add(enregistrer);
        buttonPanel.add(retour);

        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().add(contentPanel);}

    private JDateChooser createDateChooser() {
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd-MM-yyyy HH:mm");
        dateChooser.setDate(Calendar.getInstance().getTime());
        return dateChooser;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AjoutSeanceView frame = new AjoutSeanceView();
            frame.setVisible(true);
        });
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
    public JTextArea getnote(){
        return note;
    }

}
