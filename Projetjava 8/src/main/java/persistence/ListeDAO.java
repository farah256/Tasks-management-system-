package persistence;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import metier.pojo.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListeDAO  {
    private MongoDatabase listedatabase;

    public ListeDAO(MongoDatabase listedatabase) {
        this.listedatabase = listedatabase;
    }

    public boolean create(ListeTache listeTache) {
        System.out.println("Création d'une liste dans la base de données.");
        try {
            MongoCollection<Document> listeCollection = listedatabase.getCollection("listeTache");

            // Création d'un document à partir des attributs du projet
            Document listdoc = new Document("description", listeTache.getDescription());


            // Insertion du document dans la collection

            listeCollection.insertOne(listdoc);

            String id = listdoc.getObjectId("_id").toString();
            System.out.println("Liste ajouté avec succès dans la collection 'tache'.");
            // Mise à jour de l'attribut idProjet du document avec l'ID généré
            listeCollection.updateOne(Filters.eq("_id", listdoc.getObjectId("_id")),
                    Updates.set("IdList", id));

            System.out.println("liste ajouter avec succés");
            return true;

        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de l'ajout du liste dans la collection 'tache': " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    public ListeTache read(String id) {
        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
            MongoCollection<Document> listeCollection = listedatabase.getCollection("listeTache");
            System.out.println("liste collection selected successflly");
            //getting the iterable object
            Document tachdocument = listeCollection.find(new Document("IdList", id)).first();
            System.out.println("Liste document= " + tachdocument);


        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la lecture du liste : " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }



    public void delete(String id) {
        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
            MongoCollection<Document> listeCollection = listedatabase.getCollection("listeTache");
            System.out.println("liste collection selected successflly");

            DeleteResult deleteResult = listeCollection.deleteOne(Filters.eq("IdList", id));
            long deletedCount = deleteResult.getDeletedCount();

            if (deletedCount == 0) {
                System.out.println("Aucun document avec l'ID spécifié n'a été trouvé.");
            } else {
                System.out.println("Nombre de documents supprimés : " + deletedCount);
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de suppression du document dans la collection 'listeTache': " + e.getMessage());
            e.printStackTrace();
        }

    }


    public List<ListeTache> getAll() {

        List<ListeTache> listeTaches = new ArrayList<>();
        try {
            MongoCollection<Document> listeCollection = listedatabase.getCollection("listeTache");
            System.out.println("Collection de listes sélectionnée avec succès.");
            System.out.println("get the range of documents as a list");
            List<Document> list = listeCollection.find().into(new ArrayList<>());
            for (Document listDoc : list) {
                ListeTache listeTache = new ListeTache();
                listeTache.setIdList(listDoc.getString("IdList"));
                listeTache.setDescription(listDoc.getString("description"));

                listeTaches.add(listeTache);
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la récupération des listes : " + e.getMessage());
            e.printStackTrace();
        }
        return listeTaches;
    }
}
