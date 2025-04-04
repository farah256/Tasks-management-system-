package persistence;

import com.mongodb.client.MongoDatabase;
import metier.pojo.ListeTache;
import metier.pojo.Tache;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Connect to MongoDB
        Connection connection = new Connection();
        MongoDatabase database = connection.getDatabase(); // Replace "your_database_name" with your actual database name

        // Create an instance of ListeDAO
        TacheDAO tacheDAO = new TacheDAO(database);
        Tache tachess=new Tache();
        String projectId = "664396ba4f27fc0e46b0b201"; // Changez l'ID du projet pour un test réel
        List<Tache> taches = tacheDAO.getTachesByProjectId(projectId);

        // Afficher les résultats
        System.out.println("Nombre de tâches trouvées: " + taches.size());
        for (Tache tache : taches) {
            System.out.println(tache);
        }

    }
}

