package presentation.Controller;

import metier.Gestion.SeanceGestion;
import metier.pojo.Seance;
import persistence.Connection;
import persistence.SeanceDAO;
import presentation.View.ModifierSeanceView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import static presentation.Controller.ReadProjetController.displaySeance;

public class ModifierSeanceController {
    private ModifierSeanceView view;
    private SeanceGestion seanceGestion;
    private Seance selectedSeance;
    private String id;

    public ModifierSeanceController(ModifierSeanceView view, SeanceGestion seanceGestion,String id) {
        this.view = view;
        this.id=id;
        this.seanceGestion = seanceGestion;
        this.view.getModifierButton().addActionListener(new ModifierButtonListener());
        this.view.getRetourButton().addActionListener(new RetourButtonListener());
    }

    public void setSeanceInfo(Seance seance) {
        selectedSeance = seance;
        // Set the session information in the view
        view.getDescriptionField().setText(seance.getDescription());
        view.getDateDebut().setDate(seance.getDate_debut());
        view.getDateFin().setDate(seance.getDate_fin());
        view.getnote().setText(seance.getNote());
    }

    class ModifierButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String description = view.getDescriptionField().getText();
            Date dateDebut = view.getDateDebut().getDate();
            Date dateFin = view.getDateFin().getDate();
            String note = view.getnote().getText();

            selectedSeance.setDescription(description);
            selectedSeance.setDate_debut(dateDebut);
            selectedSeance.setDate_fin(dateFin);
            selectedSeance.setNote(note);

            seanceGestion.update(selectedSeance, selectedSeance.getIdSeance());
            displaySeance(id);

            JOptionPane.showMessageDialog(view, "Session updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();
        }
    }

    public static void initialize(String id) {
        try {
            Connection connection = new Connection();
            SeanceDAO seanceDAO = new SeanceDAO(connection.getDatabase());
            SeanceGestion seanceGestion = new SeanceGestion(seanceDAO);
            ModifierSeanceView view = new ModifierSeanceView();
            ModifierSeanceController controller = new ModifierSeanceController(view, seanceGestion,id);
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class RetourButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
        }
    }
}
