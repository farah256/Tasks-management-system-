package presentation.Controller;

import com.mongodb.client.MongoDatabase;
import metier.Gestion.StatistiqueGestion;
import metier.service.Statistique;
import persistence.Connection;
import presentation.Model.LoginModel;
import presentation.View.DashboardView;
import presentation.View.LoginView;
import presentation.View.MenuView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginControlleur implements ActionListener {
    private LoginModel model;
    private LoginView view;


    public LoginControlleur(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;
        view.getConnButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getConnButton()) {
            try {
                String email = view.getEmailField().getText();
                validateEmail(email);
                model.setEmail(email);
                Connection connection = new Connection();
                MongoDatabase database = connection.getDatabase();
                MenuView menuView = new MenuView();
                MenuControlleur menuControlleur = new MenuControlleur(menuView);
                DashboardView dashboardView = new DashboardView(menuControlleur);
                Statistique statistique = new Statistique(database); // Initialize with your MongoDB instance
                StatistiqueGestion statistiqueGestion = new StatistiqueGestion(statistique);
                DashboardControlleur dashboardControlleur = new DashboardControlleur(dashboardView, statistiqueGestion);
                menuControlleur.showNewFrame(dashboardControlleur.getView());
                view.dispose();
            }catch (IllegalArgumentException exception){
                JOptionPane.showMessageDialog(view, "Email format invalid!", "Error", JOptionPane.ERROR_MESSAGE);

            }

        }
    }
    private void validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail.com";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Email format invalid!");
        }
    }


}
