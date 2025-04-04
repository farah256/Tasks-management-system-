package persistence;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import metier.pojo.Categorie;
import metier.pojo.Projet;
import metier.pojo.Seance;
import metier.pojo.Type;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class SeanceDAO implements DAO<Seance>{

    private MongoDatabase seancedatabase;
    public SeanceDAO(MongoDatabase seancedatabase) {
        this.seancedatabase = seancedatabase;
    }


    @Override
    public boolean create(Seance seance) {
        System.out.println("Création d'un document dans la base de données.");
        try {
            MongoCollection<Document> snc = seancedatabase.getCollection("seance");

            // Création d'un document à partir des attributs du projet
            Document seanc = new Document("description", seance.getDescription())
                    .append("Date_debut",seance.getDate_debut())
                    .append("Date_fin",seance.getDate_fin())
                    .append("Note",seance.getNote())
                    .append("IdProjet",seance.getIdProjet());


            // Insertion du document dans la collection
            snc.insertOne(seanc);
            String id = seanc.getObjectId("_id").toString();
            snc.updateOne(Filters.eq("_id", seanc.getObjectId("_id")),
                    Updates.set("IdSeance", id));
            System.out.println("Document ajouté avec succès dans la collection 'seance'.");
            return true;

        }catch (Exception e) {
            System.err.println("Une erreur est survenue lors de l'ajout du document dans la collection 'seance': " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Seance read(String id) {
        Seance seance = null;
        try {
            MongoCollection<Document> seanceCollection = seancedatabase.getCollection("seance");
            System.out.println("Collection de seances sélectionnée avec succès.");

            // Rechercher la séance par son ID
            Document seanceDoc = seanceCollection.find(Filters.eq("_id", new ObjectId(id))).first();

            // Si la séance est trouvée, convertir le document en objet Seance
            if (seanceDoc != null) {
                seance = new Seance();
                seance.setIdSeance(seanceDoc.getObjectId("_id").toString());
                seance.setDescription(seanceDoc.getString("description"));
                seance.setDate_debut(seanceDoc.getDate("Date_debut"));
                seance.setDate_fin(seanceDoc.getDate("Date_fin"));
                seance.setNote(seanceDoc.getString("Note"));
                seance.setIdProjet(seanceDoc.getString("IdProjet"));
            } else {
                // Si aucun document de séance n'est trouvé, imprimer un message approprié
                System.out.println("Aucune séance trouvée avec l'ID spécifié.");
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la lecture de la séance par ID : " + e.getMessage());
            e.printStackTrace();
        }
        return seance;
    }


    @Override
    public void update(Seance seance, String id) {
        try {
            MongoCollection<Document> snc = seancedatabase.getCollection("seance");

            // Création du document de mise à jour avec les nouvelles valeurs
            Document updateDocument = new Document("$set", new Document()
                    .append("description", seance.getDescription())
                    .append("Date_debut", seance.getDate_debut())
                    .append("Date_fin", seance.getDate_fin())
                    .append("Note", seance.getNote())
                    .append("IdProjet", seance.getIdProjet())
            );

            // Récupère la version précédente du document avant la mise à jour
            Document oldDocument = snc.findOneAndUpdate(
                    Filters.eq("IdSeance", id),
                    updateDocument
            );
            System.out.println("old doc = " + oldDocument);

            // Récupère la nouvelle version du document après la mise à jour
            FindOneAndUpdateOptions optionAfter = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
            Document newDocument = snc.findOneAndUpdate(
                    Filters.eq("IdSeance", id),
                    updateDocument,
                    optionAfter
            );
            System.out.println("new doc = " + newDocument);

            System.out.println("Document mis à jour avec succès dans la collection 'seance'.");
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la mise à jour du document dans la collection 'seance': " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void delete(String id) {
        try (MongoClient mongoClient=new MongoClient("localhost",27017)) {
            MongoCollection<Document>  snc = seancedatabase.getCollection("seance");
            System.out.println("projet collection selected successflly");

            DeleteResult deleteResult = snc.deleteOne(Filters.eq("IdSeance", id));
            long deletedCount = deleteResult.getDeletedCount();

            if (deletedCount == 0) {
                System.out.println("Aucun document avec l'ID spécifié n'a été trouvé.");
            } else {
                System.out.println("Nombre de documents supprimés : " + deletedCount);
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de suppression du document dans la collection 'seance': " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public List<Seance> getAll() {
        List<Seance> seances = new ArrayList<>();
        try {
            MongoCollection<Document> seanceCollection = seancedatabase.getCollection("seance");
            System.out.println("Collection de seances sélectionnée avec succès.");

            // Retrieve documents from the collection
            List<Document> seanceList = seanceCollection.find().into(new ArrayList<>());
            System.out.println("Nombre de seances trouvées: " + seanceList.size());

            // Convert each Document into a Seance object
            for (Document seanceDoc : seanceList) {
                System.out.println("Document: " + seanceDoc.toJson()); // Debugging output

                Seance seance = new Seance();
                seance.setIdSeance(seanceDoc.getString("IdSeance"));
                seance.setDescription(seanceDoc.getString("description"));
                seance.setDate_debut(seanceDoc.getDate("Date_debut"));
                seance.setDate_fin(seanceDoc.getDate("Date_fin"));
                seance.setNote(seanceDoc.getString("Note"));
                seance.setIdProjet(seanceDoc.getString("IdProjet"));

                seances.add(seance);
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la récupération des seances : " + e.getMessage());
            e.printStackTrace();
        }
        return seances;
    }
    public List<Seance> getSeancesByProjectId(String projectId) {
        List<Seance> seances = new ArrayList<>();
        try {
            MongoCollection<Document> seanceCollection = seancedatabase.getCollection("seance");
            System.out.println("Collection de seances sélectionnée avec succès.");

            // Query the database to find seances with the specified project ID
            List<Document> seanceList = seanceCollection.find(Filters.eq("IdProjet", projectId)).into(new ArrayList<>());
            System.out.println("Nombre de seances trouvées pour le projet " + projectId + ": " + seanceList.size());

            // Convert each Document into a Seance object
            for (Document seanceDoc : seanceList) {
                System.out.println("Document: " + seanceDoc.toJson()); // Debugging output

                Seance seance = new Seance();
                seance.setIdSeance(seanceDoc.getString("IdSeance"));
                seance.setDescription(seanceDoc.getString("description"));
                seance.setDate_debut(seanceDoc.getDate("Date_debut"));
                seance.setDate_fin(seanceDoc.getDate("Date_fin"));
                seance.setNote(seanceDoc.getString("Note"));
                seance.setIdProjet(seanceDoc.getString("IdProjet"));

                seances.add(seance);
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la récupération des seances pour le projet " + projectId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return seances;
    }





}

