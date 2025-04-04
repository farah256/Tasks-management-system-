package presentation;

import presentation.Controller.LoginControlleur;
import presentation.Model.LoginModel;
import presentation.View.LoginView;
public  class Main{
    public static void main(String[] args) {
        LoginModel model = new LoginModel();
        LoginView view = new LoginView();
        LoginControlleur loginControlleur = new LoginControlleur(model,view);
        view.afficher();

    }
}