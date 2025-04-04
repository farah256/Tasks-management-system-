package presentation.Controller;

import metier.Gestion.ProjetGestion;
import metier.pojo.Categorie;
import metier.pojo.Projet;
import metier.pojo.Type;
import persistence.Connection;
import persistence.ProjetDAO;
import presentation.View.AjoutProjetView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;


public class AjoutProjetControlleur {
    private AjoutProjetView view;
    private ProjetGestion projetGestion;



    public AjoutProjetControlleur(AjoutProjetView view,ProjetGestion projetGestion) {
        this.projetGestion = projetGestion;
        this.view = view;
        view.getEnregistrerButton().addActionListener(new AjoutProjetControlleur.EnregistrerButtonListener());
        view.getRetourButton().addActionListener(new AjoutProjetControlleur.RetourButtonListener());


    }



    class EnregistrerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String description = view.getDescriptionField().getText();
            Date dateDebut = view.getDateDebut().getDate();
            Date dateFin = view.getDateFin().getDate();
            Categorie categorie = null;
            if (view.getEnseignementCheckbox().isSelected()) {
                categorie = Categorie.Enseignement;
            } else if (view.getEncadrementCheckbox().isSelected()) {
                categorie = Categorie.Encadrement;
            }
            Type type = null;
            if (view.getCours().isSelected()) {
                type = Type.Cours;
            } else if (view.getThese().isSelected()) {
                type = Type.These;
            } else if (view.getTd().isSelected()) {
                type = Type.TD;
            } else if (view.getPfa().isSelected()) {
                type = Type.PFA;
            } else if (view.getExam().isSelected()) {
                type = Type.Examen;
            } else if (view.getPfe().isSelected()) {
                type = Type.PFE;
            } else if (view.getTp().isSelected()) {
                type = Type.TP;
            }
            Projet projet = new Projet(description, dateDebut, dateFin, categorie, type);

            if (projetGestion.create(projet)) {
                view.dispose();
                JOptionPane.showMessageDialog(view, "Le projet  s'ajoute correctement dans la base de donné", "Succe", JOptionPane.INFORMATION_MESSAGE);
                view.getDescriptionField().setText("");
                view.getDateDebut().setDate(null);
                view.getDateFin().setDate(null);
                view.getEnseignementCheckbox().setSelected(false);
                view.getEncadrementCheckbox().setSelected(false);
                view.getCours().setSelected(false);
                view.getThese().setSelected(false);
                view.getTd().setSelected(false);
                view.getPfa().setSelected(false);
                view.getExam().setSelected(false);
                view.getPfe().setSelected(false);
                view.getTp().setSelected(false);
                ListeProjetControlleur.displayProjects();

            } else {
                JOptionPane.showMessageDialog(view, "le projet ne s'ajoute pas correctement dans la base de donné.", "Erreur", JOptionPane.ERROR_MESSAGE);
                ListeProjetControlleur.displayProjects();

            }
        }
    }

    class RetourButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose(); // Fermer la fenêtre d'ajout de projet
        }
    }



    public static void initialize() {
        try {
            Connection connection = new Connection();
            ProjetDAO projetDAO = new ProjetDAO(connection.getDatabase());
            ProjetGestion projetGestion1 = new ProjetGestion(projetDAO);
            AjoutProjetView view = new AjoutProjetView();
            AjoutProjetControlleur controller = new AjoutProjetControlleur(view,projetGestion1);

            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initialize();
    }
}

