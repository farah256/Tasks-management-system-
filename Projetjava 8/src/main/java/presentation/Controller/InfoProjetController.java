package presentation.Controller;

import com.mongodb.client.MongoDatabase;
import metier.Gestion.StatistiqueGestion;
import metier.service.Statistique;
import persistence.Connection;
import presentation.View.InfoProjetView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoProjetController {
    private InfoProjetView view;
    private StatistiqueGestion statistiqueGestion;
    private String projetId;

    public InfoProjetController(InfoProjetView view, StatistiqueGestion statistiqueGestion, String projetId) {
        this.view = view;
        this.projetId = projetId;
        this.statistiqueGestion = statistiqueGestion;
        updateView();
    }

    public void updateView() {
        try {
            // Retrieve statistics from the StatistiqueGestion object
            double heuresTravailleesSemaine = statistiqueGestion.calculerTotalHeuresTravaillees(projetId);
            long nombreDocumentsParProjet = statistiqueGestion.compterDocumentsParProjet(projetId);

            // Update the view with the retrieved statistics
            view.updateStatistics(heuresTravailleesSemaine,nombreDocumentsParProjet);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle error, maybe update the view with an error message
        }
    }


    public static void initialize(String projetId) {
        try {
            // Initialize necessary components
            Connection connection = new Connection();
            MongoDatabase database = connection.getDatabase();
            Statistique statistique = new Statistique(database);
            StatistiqueGestion statistiqueGestion = new StatistiqueGestion(statistique);
            InfoProjetView infoProjetView = new InfoProjetView();
            InfoProjetController controller = new InfoProjetController(infoProjetView, statistiqueGestion, projetId);
            infoProjetView.setVisible(true);
        } catch (Exception e) {
            System.err.println("Error initializing InfoProjetController: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initialize("664631be372d8629a18d24d4");
    }
}
