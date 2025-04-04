package presentation.Controller;

import com.mongodb.client.MongoDatabase;
import persistence.Connection;
import presentation.View.DashboardView;
import metier.Gestion.StatistiqueGestion;
import metier.pojo.Categorie;
import metier.service.Statistique;
import presentation.View.MenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class DashboardControlleur {
    private DashboardView view;
    private StatistiqueGestion statistiqueGestion;

    public DashboardControlleur() {
    }

    public DashboardControlleur(DashboardView view, StatistiqueGestion statistiqueGestion) {
        this.view = view;
        this.statistiqueGestion = statistiqueGestion;
        updateView();

        // Optional: Set up a timer to refresh data periodically
        Timer timer = new Timer(60000, new ActionListener() { // Refresh every 60 seconds
            @Override
            public void actionPerformed(ActionEvent e) {
                updateView();
            }
        });
        timer.start();
    }

    public DashboardView getView() {
        return view;
    }

    public void updateView() {
        try {
            Statistique statistique = statistiqueGestion.getStatistique();
            double heuresTravailleesSemaine = statistique.calculerHeuresTravailleesSemaine();
            double heuresTravailleesMois = statistique.calculerHeuresTravailleesMois();
            double heuresTravailleesAnnee = statistique.calculerHeuresTravailleesAnnee();

            long heuresTravailleesSemaineMillis = (long) (heuresTravailleesSemaine * 3600000);
            long heuresTravailleesMoisMillis = (long) (heuresTravailleesMois * 3600000);
            long heuresTravailleesAnneeMillis = (long) (heuresTravailleesAnnee * 3600000);


            view.updateStatistic("Heure de travail par semaine", statistique.formatMillisToTimeString(heuresTravailleesSemaineMillis));
            view.updateStatistic("Heure de travail par mois", statistique.formatMillisToTimeString(heuresTravailleesMoisMillis));
            view.updateStatistic("Heure de travail par annee", statistique.formatMillisToTimeString(heuresTravailleesAnneeMillis));
            view.updateStatistic("Nombre des Projets", String.valueOf(statistique.compterNombreProjets()));
            view.updateStatistic("Nombre des Seances", String.valueOf(statistique.compterNombreSeances()));
            view.updateStatistic("Nombre des Tâches", String.valueOf(statistique.compterNombreTaches()));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle error, maybe update the view with an error message
        }
    }



public static void initialize() {
        try {
            Connection connection = new Connection();
            MongoDatabase database = connection.getDatabase();
            MenuView menuView = new MenuView();
            MenuControlleur menuControlleur = new MenuControlleur(menuView);
            DashboardView dashboardView = new DashboardView(menuControlleur);
            Statistique statistique = new Statistique(database); // Initialize with your MongoDB instance
            StatistiqueGestion statistiqueGestion = new StatistiqueGestion(statistique);
            DashboardControlleur dashboardControlleur = new DashboardControlleur(dashboardView, statistiqueGestion);
        } catch (Exception e) {
            System.err.println("Error initializing DashboardControlleur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initialize();
        DashboardView dashboardView = new DashboardView(new MenuControlleur(new MenuView()));
        dashboardView.setVisible(true);
    }
}
