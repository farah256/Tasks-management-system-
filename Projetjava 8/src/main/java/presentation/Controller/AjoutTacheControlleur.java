package presentation.Controller;

import metier.Gestion.TacheGestion;
import metier.pojo.*;
import persistence.Connection;
import persistence.TacheDAO;
import presentation.View.AjoutTacheView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;


public class AjoutTacheControlleur {
    private AjoutTacheView view;
    private TacheGestion tacheGestion;
    private String projectId;
    private String seanceId;
    private String listId;

    public AjoutTacheControlleur(AjoutTacheView view,TacheGestion tacheGestion,String projectId, String listId, String seanceId) {
        this.tacheGestion = tacheGestion;
        this.view = view;
        this.projectId = projectId;
        this.listId = listId;
        this.seanceId = seanceId;
        view.getEnregistrerButton().addActionListener(new AjoutTacheControlleur.EnregistrerButtonListener());
        view.getRetourButton().addActionListener(new AjoutTacheControlleur.RetourButtonListener());


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
            Etat etat=null;
            if (view.getAttentCheckbox().isSelected()) {
                etat=Etat.attente;
            } else if (view.getFiniCheckbox().isSelected()) {
                etat=Etat.finie;
            }

            Tache tache = new Tache(description, dateDebut, dateFin, categorie,etat);
            tache.setIdProjet(projectId);
            tache.setIdSeance(seanceId);
            tache.setIdList(listId);


            if (tacheGestion.create(tache)) {
                view.dispose();
                JOptionPane.showMessageDialog(view, "la tache s'ajoute correctement dans la base de donné.", "Succe", JOptionPane.INFORMATION_MESSAGE);
                ReadListController.displayTachesListes(listId);
                view.getDescriptionField().setText("");
                view.getDateDebut().setDate(null);
                view.getDateFin().setDate(null);
                view.getEnseignementCheckbox().setSelected(false);
                view.getEncadrementCheckbox().setSelected(false);
                ReadProjetController.displayTachesprojet(projectId);
                ReadSeanceController.displayTaches(seanceId);
                ListeTacheController.displayTaches();


            } else {
                JOptionPane.showMessageDialog(view, "La tache ne s'ajoute pas avec succées", "Eurreur", JOptionPane.ERROR_MESSAGE);

            }
        }
    }

    class RetourButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose(); }
    }



    public static void initialize(String projectId, String listId, String seanceId) {
        try {
            Connection connection = new Connection();
            TacheDAO tacheDAO = new TacheDAO(connection.getDatabase());
            TacheGestion tacheGestion1 = new TacheGestion(tacheDAO);
            AjoutTacheView view = new AjoutTacheView();
            AjoutTacheControlleur controller = new AjoutTacheControlleur(view,tacheGestion1, projectId, listId, seanceId);

            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initialize("null","null","null");
    }
}