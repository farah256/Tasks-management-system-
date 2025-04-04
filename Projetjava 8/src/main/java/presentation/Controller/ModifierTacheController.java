package presentation.Controller;

import metier.Gestion.ProjetGestion;
import metier.Gestion.TacheGestion;
import metier.pojo.Etat;
import metier.pojo.Projet;
import metier.pojo.Tache;
import persistence.Connection;
import persistence.ProjetDAO;
import persistence.TacheDAO;
import presentation.View.ModifierProjetview;
import presentation.View.ModifierTacheView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import static presentation.Controller.ListeProjetControlleur.displayProjects;
import static presentation.Controller.ListeTacheController.displayTaches;

public class ModifierTacheController {
    private ModifierTacheView view;
    private TacheGestion tacheGestion;
    private Tache selectedTache;


    public ModifierTacheController(ModifierTacheView view, TacheGestion tacheGestion) {
        this.view = view;
        this.tacheGestion = tacheGestion;

        this.view.getModifierButton().addActionListener(new ModifierTacheController.ModifierButtonListener());
        this.view.getRetourButton().addActionListener(new ModifierTacheController.RetourButtonListener());

    }
    public void setProjectInfo(Tache tache) {
        selectedTache = tache;
        view.getDescriptionField().setText(tache.getDescription());
        view.getDateDebut().setDate(tache.getDate_debut());
        view.getDateFin().setDate(tache.getDate_fin());
        view.getFiniCheckbox().setSelected(tache.getEtat() == Etat.finie);
        view.getAttentCheckbox().setSelected(tache.getEtat() == Etat.attente);
    }
    class ModifierButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String description = view.getDescriptionField().getText();
            Date dateDebut = view.getDateDebut().getDate();
            Date dateFin = view.getDateFin().getDate();
            Etat etat = null;
            if (view.getFiniCheckbox().isSelected()) {
                etat = Etat.finie;
            } else if (view.getAttentCheckbox().isSelected()) {
                etat = Etat.attente;
            }
            selectedTache.setDescription(description);
            selectedTache.setDate_debut(dateDebut);
            selectedTache.setDate_fin(dateFin);
            selectedTache.setEtat(etat);


            tacheGestion.update(selectedTache,selectedTache.getIdTache());
            displayTaches();

            JOptionPane.showMessageDialog(view, "Task updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();
        }
    }
    class RetourButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
        }
    }
    public static void initialize() {
        try {
            Connection connection = new Connection();
            TacheDAO tacheDAO = new TacheDAO(connection.getDatabase());
            TacheGestion tacheGestion1 = new TacheGestion(tacheDAO);
            ModifierTacheView view = new ModifierTacheView();
            ModifierTacheController controller = new ModifierTacheController(view,tacheGestion1);
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initialize();
    }
}
