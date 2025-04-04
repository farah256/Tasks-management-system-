package presentation.Controller;

import com.mongodb.client.MongoDatabase;
import metier.Gestion.*;
import metier.service.Statistique;
import persistence.*;
import presentation.View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

public class MenuControlleur {

    private JFrame currentFrame;
    private MenuView view;

    public MenuControlleur(MenuView view) {
        this.view = view;
        this.currentFrame = view;

        // Register ActionListeners
        this.view.addDashboardButtonListener(new DashboardButtonListener());
        this.view.addCalendrierButtonListener(new CalendrierButtonListener());
        this.view.addProjetButtonListener(new ProjetButtonListener());
        this.view.addTacheButtonListener(new TacheButtonListener());
        this.view.addListeButtonListener(new ListeButtonListener());
        this.view.addHistoriqueProjetButtonListener(new HistoriqueProjetButtonListener());
        this.view.addHistoriqueTacheButtonListener(new HistoriqueTacheButtonListener());
    }
    public void showNewFrame(JFrame newFrame) {
        // Show the new frame
        if (currentFrame != null) {
            currentFrame.dispose();
        }
        currentFrame = newFrame;
        currentFrame.setVisible(true);
    }

    // Define separate ActionListener classes

    class DashboardButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Connection connection = new Connection();
            MongoDatabase database = connection.getDatabase();
            MenuView menuView = new MenuView();
            MenuControlleur menuControlleur = new MenuControlleur(menuView);
            DashboardView dashboardView = new DashboardView(MenuControlleur.this);
            Statistique statistique = new Statistique(database); // Initialize with your MongoDB instance
            StatistiqueGestion statistiqueGestion = new StatistiqueGestion(statistique);
            DashboardControlleur dashboardControlleur = new DashboardControlleur(dashboardView, statistiqueGestion);
            showNewFrame(dashboardControlleur.getView());
        }
    }

    class CalendrierButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Desktop desktop = java.awt.Desktop.getDesktop();
                desktop.browse( new URI("https://calendar.google.com"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    class ProjetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Connection connection = new Connection();
            ProjetDAO projetDAO = new ProjetDAO(connection.getDatabase());
            ProjetGestion projetGestion = new ProjetGestion(projetDAO);
            ListProjetView listProjetView = new ListProjetView(MenuControlleur.this);
            ListeProjetControlleur controller = new ListeProjetControlleur(listProjetView, projetGestion);
            showNewFrame(controller.getView());

        }
    }
    class TacheButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Connection connection = new Connection();
            TacheDAO tacheDAO = new TacheDAO(connection.getDatabase());
            TacheGestion tacheGestion = new TacheGestion(tacheDAO);
            ListTacheView listTacheView = new ListTacheView(MenuControlleur.this.getView());

            ListeTacheController controller = new ListeTacheController(listTacheView, tacheGestion);
            showNewFrame(controller.getView());


        }

    }class ListeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Connection connection = new Connection();
            ListeDAO listeDAO = new ListeDAO(connection.getDatabase());
            ListeGestion listeGestion = new ListeGestion(listeDAO);
            ListeView listeView = new ListeView(MenuControlleur.this);

            ListController controller = new ListController(listeView, listeGestion);
            showNewFrame(controller.getView());

        }

    }class HistoriqueProjetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Connection connection = new Connection();
            HistoriqueProjetDAO historiqueProjetDAO = new HistoriqueProjetDAO(connection.getDatabase());
            HistoriqueGestion historiqueGestion = new HistoriqueGestion(historiqueProjetDAO);
            HistoriqueProjetView historiqueProjetView = new HistoriqueProjetView(MenuControlleur.this.getView());

            HistoriqueProjetControlleur controller = new HistoriqueProjetControlleur(historiqueProjetView, historiqueGestion);
            showNewFrame(controller.getView());

        }

    }
    class HistoriqueTacheButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Connection connection = new Connection();
            HistoriqueTacheDAO historiqueTacheDAO = new HistoriqueTacheDAO(connection.getDatabase());
            HistoriqueTacheGestion historiqueGestion = new HistoriqueTacheGestion(historiqueTacheDAO);
            HistoriqueTacheView historiqueTacheView = new HistoriqueTacheView(MenuControlleur.this.getView());

            HistoriqueTacheController controller = new HistoriqueTacheController(historiqueTacheView, historiqueGestion);
            showNewFrame(controller.getView());
        }

    }
    public MenuView getView() {
        return view;
    }



    public static void main(String[] args) {
        MenuView menuView = new MenuView();
        MenuControlleur menuControlleur = new MenuControlleur(menuView);
        menuView.initialize();
        menuView.setVisible(true);

    }
}
