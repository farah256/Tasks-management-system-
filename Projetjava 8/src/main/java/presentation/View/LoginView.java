package presentation.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField tEmail;
    private JButton conn;
    public LoginView() {
        this.setTitle("Gestion des Taches");
        this.setResizable(false);
        this.setSize(800,400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(d.width/2-this.getSize().width/2, d.height/2-this.getSize().height/2);

        ImageIcon imageIcon = new ImageIcon("download.jpg");
        JLabel label = new JLabel();
        label.setIcon(imageIcon);
        this .getContentPane().setBackground(Color.white);
        this.add(label);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        JLabel email = new JLabel("Email");
        tEmail = new JTextField();
        tEmail.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        tEmail.setPreferredSize(new Dimension(200, 30));
        conn = new JButton("Connexion");
        conn.setBackground(new Color(100,90,150));
        email.setFont(new Font("MV Boli",Font.PLAIN,15));
        panel.setBackground(Color.white);
        panel.add(email,gbc);
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(tEmail,gbc);
        gbc.gridy = 2;
        panel.add(conn,gbc);
        this.setLayout(new BorderLayout());
        this.add(label, BorderLayout.WEST);
        this.add(panel, BorderLayout.CENTER);




    }
    public JTextField getEmailField() {
        return tEmail;
    }

    public JButton getConnButton() {
        return conn;
    }
    public void afficher(){
        this.setVisible(true);
    }

}


