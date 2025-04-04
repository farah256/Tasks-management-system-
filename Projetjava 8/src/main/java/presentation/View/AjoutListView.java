package presentation.View;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class AjoutListView extends JFrame {
    private JButton enregistrer ;
    private JButton retour ;

    private JTextField descriptionField;
    public AjoutListView() {
        setTitle("Tracker");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 200);
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
        JLabel headerLabel = new JLabel("Ajouter Liste");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        formPanel.add(headerLabel, gbc);

        // Description input
        gbc.gridy++;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx++;
         descriptionField = new JTextField(20);
        formPanel.add(descriptionField, gbc);



        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(98, 22, 203,70));

        enregistrer = new JButton("Enregistrer");
        retour = new JButton("Retour");

        buttonPanel.add(enregistrer);
        buttonPanel.add(retour);

        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().add(contentPanel);
    } public JButton getEnregistrerButton() {
        return enregistrer;
    }

    public JButton getRetourButton() {
        return retour;
    }


    public  JTextField getDescriptionField(){
        return descriptionField;

    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AjoutListView frame = new AjoutListView();
            frame.setVisible(true);
        });
    }
}
