package presentation.Controller;

import metier.Gestion.ProjetGestion;
import metier.Gestion.TacheGestion;
import metier.pojo.*;
import persistence.Connection;
import persistence.ProjetDAO;
import persistence.TacheDAO;
import presentation.View.FiltrerProjetView;
import presentation.View.FiltrerTacheView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class FiltrerTacheController {
    private FiltrerTacheView view;
    private TacheGestion tacheGestion;

    public FiltrerTacheController(FiltrerTacheView view, TacheGestion tacheGestion) {
        this.view = view;
        this.tacheGestion = tacheGestion;
        view.getEnregistrerButton().addActionListener(new FiltrerTacheController.EnregistrerButtonListener());

    }

    class EnregistrerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Récupérer la catégorie sélectionnée par l'utilisateur

            Categorie categorie = null;
            if (view.getEnseignementCheckbox().isSelected()) {
                categorie = Categorie.Enseignement;
            } else if (view.getEncadrementCheckbox().isSelected()) {
                categorie = Categorie.Encadrement;
            }
            Etat etat=null;
            if (view.getAttente().isSelected()){
                etat=Etat.attente;
            } else if (view.getFinie().isSelected()) {
                etat=Etat.finie;
            }
            // Récupérer la date sélectionnée par l'utilisateur

            List<Tache> tachesFiltres=tacheGestion.filtrerTaches(etat,categorie);
            if (!tachesFiltres.isEmpty()) {
                view.dispose();
                ListeTacheController.displayFilteredTaches(view);
            } else {
                JOptionPane.showMessageDialog(view, "Aucunne taches ne correspond aux critères sélectionnés.", "Aucun Résultat", JOptionPane.INFORMATION_MESSAGE);
            }




        }
    }



    public static void initialize() {
        try {
            Connection connection = new Connection();
            TacheDAO tacheDAO = new TacheDAO(connection.getDatabase());
            TacheGestion tacheGestion1 = new TacheGestion(tacheDAO);
            FiltrerTacheView view = new FiltrerTacheView();
            FiltrerTacheController controller = new FiltrerTacheController(view,tacheGestion1);
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        initialize();
    }


}


