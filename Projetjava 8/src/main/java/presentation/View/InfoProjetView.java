package presentation.View;

import javax.swing.*;
import java.awt.*;

public class InfoProjetView extends JFrame {
    private JLabel statistic1Label;
    private JLabel statistic2Label;


    public InfoProjetView() {
        setTitle("Statistics");
        setSize(400, 200);

        // Initialize labels without initial values
        statistic1Label = new JLabel("Heures de Travailles: ");
        statistic2Label = new JLabel("Nombre des Documents: ");

        // Set fonts and colors
        Font font = new Font("Arial", Font.BOLD, 20);

        // Apply styling to labels
        statistic1Label.setFont(font);
        statistic1Label.setForeground(Color.BLACK);
        statistic1Label.setHorizontalAlignment(SwingConstants.CENTER);

        statistic2Label.setFont(font);
        statistic2Label.setForeground(Color.BLACK);
        statistic2Label.setHorizontalAlignment(SwingConstants.CENTER);



        // Set layout to GridLayout with 2 rows and 1 column
        setLayout(new GridLayout(2, 1));

        // Add labels to the frame
        add(statistic1Label);
        add(statistic2Label);

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    // Method to update statistics values
    public void updateStatistics(double value1, long value2) {
        statistic1Label.setText("Heures de Travailles: " + value1);
        statistic2Label.setText("Nombre des Documents: " + value2);
    }


    public static void main(String[] args) {
        // Example usage
        SwingUtilities.invokeLater(() -> {
            InfoProjetView frame = new InfoProjetView();
            frame.setVisible(true);
        });
    }
}
