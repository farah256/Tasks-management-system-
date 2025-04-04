package presentation.Controller;

import metier.Gestion.HistoriqueGestion;
import metier.Gestion.HistoriqueTacheGestion;
import metier.pojo.*;
import persistence.Connection;
import persistence.HistoriqueProjetDAO;
import persistence.HistoriqueTacheDAO;
import presentation.View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HistoriqueTacheController {
    private static HistoriqueTacheGestion historiqueGestion;
    private static HistoriqueTacheView view;
    private static TacheTableModel tacheTableModel;

    public HistoriqueTacheController(HistoriqueTacheView view, HistoriqueTacheGestion historiqueGestion) {
        this.view = view;
        this.historiqueGestion = historiqueGestion;
        this.tacheTableModel = new TacheTableModel();
        this.view.setTableModel(tacheTableModel);
        this.view.addSearchButtonListener(new HistoriqueTacheController.SearchButtonListener());
        this.view.addFiltrerButtonListener(new HistoriqueTacheController.FiltrerButtonListener());

        displayHistoriqueTaches();

    }
    public static void displayHistoriqueTaches() {
        List<Tache> taches = historiqueGestion.getallTache();
        tacheTableModel.setTaches(taches); // Met à jour le modèle de tableau avec les nouveaux projets
    }


    class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchText = view.getSearchText();
            List<Tache> taches;
            if (!searchText.isEmpty()) {
                taches = historiqueGestion.filtrerTachesParMotCle(searchText);
            } else {
                taches = historiqueGestion.getallTache();
            }
            if (!taches.isEmpty()) {
                tacheTableModel.setTaches(taches);
            } else {
                JOptionPane.showMessageDialog(view, "No taches found.", "No taches Found", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    class FiltrerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            FiltrerHistoriqueTache filtrertachesControlleur = new FiltrerHistoriqueTache(new FiltrerTacheView(), historiqueGestion);
            filtrertachesControlleur.initialize();






        }
    }
    public static void initialize() {
        try {
            Connection connection = new Connection();
            HistoriqueTacheDAO historiqueTacheDAO = new HistoriqueTacheDAO(connection.getDatabase());
            HistoriqueTacheGestion historiqueGestion1 = new HistoriqueTacheGestion(historiqueTacheDAO);
            HistoriqueTacheView historiqueTacheView = new HistoriqueTacheView(new MenuControlleur(new MenuView()).getView());

            HistoriqueTacheController controller = new HistoriqueTacheController( historiqueTacheView, historiqueGestion1 );
            new MenuControlleur(new MenuView()).showNewFrame( historiqueTacheView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void displayFilteredTaches(FiltrerTacheView view) {
        // Récupérer la catégorie sélectionnée par l'utilisateur
        Categorie categorie = null;
        if (view.getEnseignementCheckbox().isSelected()) {
            categorie = Categorie.Enseignement;
        } else if (view.getEncadrementCheckbox().isSelected()) {
            categorie = Categorie.Encadrement;
        }

        // Récupérer le type sélectionné par l'utilisateur
        Etat etat=null;
        if (view.getAttente().isSelected()){
            etat=Etat.attente;
        } else if (view.getFinie().isSelected()) {
            etat=Etat.finie;
        }
        // Récupérer la date sélectionnée par l'utilisateur

        List<Tache> taches = historiqueGestion.filtrerTaches(etat,categorie);
        tacheTableModel.setTaches(taches);
    }


    public HistoriqueTacheView getView() {
        return view;
    }






    public static void main(String[] args) {
        initialize();
    }
}


