package presentation.Controller;

import metier.Gestion.SeanceGestion;
import metier.pojo.Seance;
import persistence.Connection;
import persistence.SeanceDAO;
import presentation.View.AjoutSeanceView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class AjoutSeanceControlleur {
    private AjoutSeanceView view;
    private static SeanceGestion seanceGestion;
    private String projectId;

    public AjoutSeanceControlleur(AjoutSeanceView view, SeanceGestion seanceGestion, String projectId) {
        this.seanceGestion = seanceGestion;
        this.view = view;
        this.projectId = projectId;
        view.getEnregistrerButton().addActionListener(new EnregistrerButtonListener());
        view.getRetourButton().addActionListener(new RetourButtonListener());
    }

    class EnregistrerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String description = view.getDescriptionField().getText();
            String note = view.getnote().getText();
            Date dateDebut = view.getDateDebut().getDate();
            Date dateFin = view.getDateFin().getDate();

            Seance seance = new Seance(description, dateDebut, dateFin, note);
            seance.setIdProjet(projectId);

            if (seanceGestion.create(seance)) {
                view.dispose();
                JOptionPane.showMessageDialog(view, "The session was successfully added to the database.", "Success", JOptionPane.INFORMATION_MESSAGE);
                ReadProjetController.displaySeance(projectId);
                view.getDescriptionField().setText("");
                view.getnote().setText("");
                view.getDateDebut().setDate(null);
                view.getDateFin().setDate(null);
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add the session.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class RetourButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
        }
    }

    public static void initialize(String projectId) {
        try {
            Connection connection = new Connection();
            SeanceDAO seanceDAO = new SeanceDAO(connection.getDatabase());
            SeanceGestion seanceGestion = new SeanceGestion(seanceDAO);
            AjoutSeanceView view = new AjoutSeanceView();
            AjoutSeanceControlleur controller = new AjoutSeanceControlleur(view, seanceGestion, projectId); // Pass the project ID

            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String projectId = "null";
        initialize(projectId);
    }
}