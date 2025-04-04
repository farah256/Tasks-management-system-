package presentation.Controller;

import metier.Gestion.HistoriqueGestion;
import metier.Gestion.HistoriqueTacheGestion;
import metier.Gestion.TacheGestion;
import metier.pojo.Categorie;
import metier.pojo.Etat;
import metier.pojo.Tache;
import persistence.Connection;
import persistence.HistoriqueTacheDAO;
import persistence.TacheDAO;
import presentation.View.FiltrerTacheView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FiltrerHistoriqueTache {
    private FiltrerTacheView view;
    private HistoriqueTacheGestion historiqueGestion;

    public FiltrerHistoriqueTache(FiltrerTacheView view, HistoriqueTacheGestion historiqueTacheGestion) {
        this.view = view;
        this.historiqueGestion = historiqueTacheGestion;
        view.getEnregistrerButton().addActionListener(new FiltrerHistoriqueTache.EnregistrerButtonListener());

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

            List<Tache> tachesFiltres=historiqueGestion.filtrerTaches(etat,categorie);
            if (!tachesFiltres.isEmpty()) {
                view.dispose();
                HistoriqueTacheController.displayFilteredTaches(view);
            } else {
                JOptionPane.showMessageDialog(view, "Aucunne taches ne correspond aux critères sélectionnés.", "Aucun Résultat", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }



    public static void initialize() {
        try {
            Connection connection = new Connection();
            HistoriqueTacheDAO historiqueTacheDAO = new HistoriqueTacheDAO(connection.getDatabase());
            HistoriqueTacheGestion tacheGestion1 = new HistoriqueTacheGestion(historiqueTacheDAO);
            FiltrerTacheView view = new FiltrerTacheView();
            FiltrerHistoriqueTache controller = new FiltrerHistoriqueTache(view,tacheGestion1);
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        initialize();
    }


}



