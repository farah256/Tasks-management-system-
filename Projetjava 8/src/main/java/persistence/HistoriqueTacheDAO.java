package persistence;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import metier.pojo.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class HistoriqueTacheDAO {
    private MongoDatabase tachedatabase;

    public HistoriqueTacheDAO(MongoDatabase tachedatabase) {
        this.tachedatabase = tachedatabase;
    }

    public List<Tache> getallTache() {
        List<Tache> taches = new ArrayList<>();
        try {
            MongoCollection<Document> tachcollection = tachedatabase.getCollection("historique");
            System.out.println("Collection de taches sélectionnée avec succès.");
            System.out.println("get the range of documents as a list");
            List<Document> tacheList = tachcollection.find().into(new ArrayList<>());
            for (Document projetDoc : tacheList) {
                Tache tache = new Tache();
                tache.setIdTache(projetDoc.getString("IdTache"));
                tache.setDescription(projetDoc.getString("description"));
                tache.setDate_debut(projetDoc.getDate("Date_debut"));
                tache.setDate_fin(projetDoc.getDate(" Date_fin"));
                Categorie categorieEnum = Categorie.valueOf(projetDoc.getString("Categorie"));
                tache.setCategorie(categorieEnum);
                Etat etatEnum = Etat.valueOf(projetDoc.getString("Etat"));
                tache.setEtat(etatEnum);


                taches.add(tache);
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la récupération des taches : " + e.getMessage());
            e.printStackTrace();
        }
        return taches;
    }
    public List<Tache> filtrerTachesParMotCle(String motCle) {
        List<Tache> tachesFiltres = new ArrayList<>();
        try {
            MongoCollection<Document> tacheCollection = tachedatabase.getCollection("historique");
            System.out.println("Collection de taches sélectionnée avec succès.");

            // Création d'un filtre pour rechercher des correspondances partielles dans la description
            Document filter = new Document("description", new Document("$regex", motCle));

            // Recherche des projets correspondant au filtre
            List<Document> filteredTasks = tacheCollection.find(filter).into(new ArrayList<>());

            // Convertir les documents en objets Tache
            for (Document tacheDoc : filteredTasks) {
                Tache tache = new Tache();
                tache.setIdTache(tacheDoc.getObjectId("_id").toString()); // Utilisation de l'ID généré par MongoDB
                tache.setDescription(tacheDoc.getString("description"));
                tache.setDate_debut(tacheDoc.getDate("Date_debut"));
                tache.setDate_fin(tacheDoc.getDate("Date_fin"));
                tache.setIdList(tacheDoc.getString("idList"));
                tache.setIdProjet(tacheDoc.getString("IdProjet"));
                tache.setIdSeance(tacheDoc.getString("IdSeance"));
                Etat etatEnum = Etat.valueOf(tacheDoc.getString("Etat"));
                tache.setEtat(etatEnum);
                Categorie categorieEnum = Categorie.valueOf(tacheDoc.getString("Categorie"));
                tache.setCategorie(categorieEnum);
                tachesFiltres.add(tache);
            }

        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors du filtrage des taches par mot-clé dans la description : " + e.getMessage());
            e.printStackTrace();
        }
        return tachesFiltres;
    }
    public List<Tache> filtrerTaches(Etat etat, Categorie categorie) {
        List<Tache> tachesFiltres = new ArrayList<>();
        try {
            MongoCollection<Document> tacheCollection = tachedatabase.getCollection("historique");
            System.out.println("Collection des taches sélectionnée avec succès.");

            // Création d'un filtre pour rechercher des tâches par état et catégorie
            Document filter = new Document();
            filter.append("Etat", etat.name());
            filter.append("Categorie", categorie.name());

            // Si une date de début et une date de fin sont spécifiées, ajoutez le filtre pour les dates

            // Recherche des tâches correspondant au filtre
            List<Document> filteredTasks = tacheCollection.find(filter).into(new ArrayList<>());

            // Convertir les documents en objets Tache
            for (Document tacheDoc : filteredTasks) {
                Tache tache = new Tache();
                tache.setIdTache(tacheDoc.getObjectId("_id").toString());
                tache.setDescription(tacheDoc.getString("description"));
                tache.setDate_debut(tacheDoc.getDate("Date_debut"));
                tache.setDate_fin(tacheDoc.getDate("Date_fin"));
                tache.setIdList(tacheDoc.getString("idList"));
                tache.setIdProjet(tacheDoc.getString("IdProjet"));
                tache.setIdSeance(tacheDoc.getString("IdSeance"));
                Categorie categorieEnum = Categorie.valueOf(tacheDoc.getString("Categorie"));
                tache.setCategorie(categorieEnum);
                Etat etatEnum = Etat.valueOf(tacheDoc.getString("Etat"));
                tache.setEtat(etatEnum);

                tachesFiltres.add(tache);
            }

        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors du filtrage des taches par type, catégorie et intervalle de dates : " + e.getMessage());
            e.printStackTrace();
        }
        return tachesFiltres;
    }

}