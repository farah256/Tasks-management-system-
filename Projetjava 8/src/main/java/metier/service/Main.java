package metier.service;

import com.mongodb.client.MongoDatabase;
import metier.pojo.Categorie;
import persistence.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection = new Connection();
        MongoDatabase database = connection.getDatabase();
        Statistique statistique = new Statistique(database);
        System.out.println(statistique.calculerTotalHeuresTravaillees("664631be372d8629a18d24d4"));
//        System.out.println(statistique.compterNombreDocuments());
        System.out.println(statistique.compterNombreSeances());
//        System.out.println(statistique.compterNombreProjets());
//        System.out.println(statistique.compterNombreTaches());


    }
}
