package presentation.Controller;

import metier.Gestion.ProjetGestion;
import metier.pojo.Categorie;
import metier.pojo.Projet;
import metier.pojo.Type;
import persistence.Connection;
import persistence.ProjetDAO;
import presentation.View.AjoutProjetView;
import presentation.View.ModifierProjetview;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import static presentation.Controller.ListeProjetControlleur.displayProjects;

public class ModiferProjetController {
    private ModifierProjetview view;
    private ProjetGestion projetGestion;
    private Projet selectedProject;

    public ModiferProjetController() {
    }

    public ModiferProjetController(ModifierProjetview view, ProjetGestion projetGestion) {
        this.view = view;
        this.projetGestion = projetGestion;
        this.view.getModifierButton().addActionListener(new ModifierButtonListener());
        this.view.getRetourButton().addActionListener(new RetourButtonListener());
    }

    public void setProjectInfo(Projet projet) {
        selectedProject = projet;
        view.getDescriptionField().setText(projet.getDescription());
        view.getDateDebut().setDate(projet.getDate_debut());
        view.getDateFin().setDate(projet.getDate_fin());

    }

    class ModifierButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String description = view.getDescriptionField().getText();
            Date dateDebut = view.getDateDebut().getDate();
            Date dateFin = view.getDateFin().getDate();

            selectedProject.setDescription(description);
            selectedProject.setDate_debut(dateDebut);
            selectedProject.setDate_fin(dateFin);

            projetGestion.update(selectedProject, selectedProject.getIdProjet());
            displayProjects();

            JOptionPane.showMessageDialog(view, "Project updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            view.dispose();
        }
    }
    public static void initialize() {
        try {
            Connection connection = new Connection();
            ProjetDAO projetDAO = new ProjetDAO(connection.getDatabase());
            ProjetGestion projetGestion1 = new ProjetGestion(projetDAO);
            ModifierProjetview view = new ModifierProjetview();
            ModiferProjetController controller = new ModiferProjetController(view,projetGestion1);
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
