package persistence;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import metier.pojo.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class DocumentDAO implements DAO<Document> {

    private MongoDatabase documentDatabase;

    public DocumentDAO(MongoDatabase documentDatabase) {
        this.documentDatabase = documentDatabase;
    }

    @Override
    public boolean create(Document document) {
        System.out.println("Creating a document in the database.");
        try {
            MongoCollection<org.bson.Document> docu = documentDatabase.getCollection("document");
            org.bson.Document doc = new org.bson.Document("Nom_doc", document.getNom())
                    .append("Date_ajout", document.getDate_ajout())
                    .append("IdSeance", document.getIdSeance())
                    .append("IdProjet", document.getIdProjet())
                    .append("IdTache", document.getIdTache())
                    .append("Url", document.getUrl());

            docu.insertOne(doc);
            String id = doc.getObjectId("_id").toString();
            docu.updateOne(Filters.eq("_id", doc.getObjectId("_id")),
                    Updates.set("IdDocument", id));
            System.out.println("Document added successfully to the 'document' collection.");
            return true;
        } catch (Exception e) {
            System.err.println("An error occurred while adding the document to the 'document' collection: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Document read(String id) {
        try {
            MongoCollection<org.bson.Document> docu = documentDatabase.getCollection("document");
            org.bson.Document document = docu.find(Filters.eq("_id", new ObjectId(id))).first();
            if (document != null) {
                Document doc = new Document();
                doc.setIdDocument(document.getObjectId("_id").toString());
                doc.setNom(document.getString("Nom_doc"));
                doc.setDate_ajout(document.getDate("Date_ajout"));
                doc.setIdSeance(document.getString("IdSeance"));
                doc.setIdProjet(document.getString("IdProjet"));
                doc.setIdTache(document.getString("IdTache"));
                doc.setUrl(document.getString("Url"));
                return doc;
            } else {
                System.out.println("No document found with the specified ID.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while reading the document by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Document document, String id) {
        try {
            MongoCollection<org.bson.Document> docu = documentDatabase.getCollection("document");

            // Create the update document with the new values
            org.bson.Document updateDocument = new org.bson.Document("$set", new org.bson.Document()
                    .append("Nom_doc", document.getNom())
                    .append("Date_ajout", document.getDate_ajout())
                    .append("IdSeance", document.getIdSeance())
                    .append("IdProjet", document.getIdProjet())
                    .append("IdTache", document.getIdTache())
                    .append("Url", document.getUrl()));

            // Retrieve the previous version of the document before the update
            org.bson.Document oldDocument = docu.findOneAndUpdate(
                    Filters.eq("_id", new ObjectId(id)),
                    updateDocument
            );
            System.out.println("Old document = " + oldDocument);

            // Retrieve the new version of the document after the update
            FindOneAndUpdateOptions optionAfter = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
            org.bson.Document newDocument = docu.findOneAndUpdate(
                    Filters.eq("_id", new ObjectId(id)),
                    updateDocument,
                    optionAfter
            );
            System.out.println("New document = " + newDocument);

            System.out.println("Document updated successfully in the 'document' collection.");

        } catch (Exception e) {
            System.err.println("An error occurred while updating the document in the 'document' collection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            MongoCollection<org.bson.Document> docu = documentDatabase.getCollection("document");

            DeleteResult deleteResult = docu.deleteOne(Filters.eq("_id", new ObjectId(id)));
            long deletedCount = deleteResult.getDeletedCount();

            if (deletedCount == 0) {
                System.out.println("No document with the specified ID found.");
            } else {
                System.out.println("Number of documents deleted: " + deletedCount);
            }

        } catch (Exception e) {
            System.err.println("An error occurred while deleting the document in the 'document' collection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Document> getAll() {
        List<Document> documents = new ArrayList<>();
        try {
            MongoCollection<org.bson.Document> docu = documentDatabase.getCollection("document");

            // Retrieve the range of documents as a list
            List<org.bson.Document> listdoct = docu.find().into(new ArrayList<>());
            for (org.bson.Document doc : listdoct) {
                Document document = new Document();
                document.setIdDocument(doc.getObjectId("_id").toString());
                document.setNom(doc.getString("Nom_doc"));
                document.setDate_ajout(doc.getDate("Date_ajout"));
                document.setIdTache(doc.getString("IdTache"));
                document.setIdProjet(doc.getString("IdProjet"));
                document.setIdSeance(doc.getString("IdSeance"));
                document.setUrl(doc.getString("Url"));
                documents.add(document);
            }

        } catch (Exception e) {
            System.err.println("An error occurred while retrieving the documents: " + e.getMessage());
            e.printStackTrace();
        }
        return documents;
    }

    public List<Document> filtrerdocumentParMotCle(String motCle) {
        List<Document> documentsFiltres = new ArrayList<>();
        try {
            MongoCollection<org.bson.Document> documentCollection = documentDatabase.getCollection("document");

            // Create a filter to search for partial matches in the description
            org.bson.Document filter = new org.bson.Document("Nom_doc", new org.bson.Document("$regex", motCle));

            // Search for documents matching the filter
            List<org.bson.Document> filteredDocuments = documentCollection.find(filter).into(new ArrayList<>());

            // Convert the documents to objects
            for (org.bson.Document doc : filteredDocuments) {
                Document document = new Document();
                document.setIdDocument(doc.getObjectId("_id").toString());
                document.setNom(doc.getString("Nom_doc"));
                document.setDate_ajout(doc.getDate("Date_ajout"));
                document.setIdTache(doc.getString("IdTache"));
                document.setIdProjet(doc.getString("IdProjet"));
                document.setIdSeance(doc.getString("IdSeance"));
                document.setUrl(doc.getString("Url"));
                documentsFiltres.add(document);
            }

        } catch (Exception e) {
            System.err.println("An error occurred while filtering documents by keyword in the description: " + e.getMessage());
            e.printStackTrace();
        }
        return documentsFiltres;
    }

    public List<Document> getDocumentsByProjectId(String projectId) {
        List<Document> documents = new ArrayList<>();
        try {
            MongoCollection<org.bson.Document> documentCollection = documentDatabase.getCollection("document");
            System.out.println("Collection de documents sélectionnée avec succès.");

            // Query the database to find documents with the specified project ID
            List<org.bson.Document> documentList = documentCollection.find(Filters.eq("IdProjet", projectId)).into(new ArrayList<>());
            System.out.println("Nombre de documents trouvés pour le projet " + projectId + ": " + documentList.size());

            // Convert each Document into a Document object
            for (org.bson.Document doc : documentList) {
                Document document = new Document();
                document.setIdDocument(doc.getObjectId("_id").toString());
                document.setNom(doc.getString("Nom_doc"));
                document.setDate_ajout(doc.getDate("Date_ajout"));
                document.setIdTache(doc.getString("IdTache"));
                document.setIdProjet(doc.getString("IdProjet"));
                document.setIdSeance(doc.getString("IdSeance"));
                document.setUrl(doc.getString("Url"));
                documents.add(document);
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la récupération des documents pour le projet " + projectId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return documents;
    }

    public List<Document> getDocumentsByTaskId(String taskId) {
        List<Document> documents = new ArrayList<>();
        try {
            MongoCollection<org.bson.Document> documentCollection = documentDatabase.getCollection("document");
            System.out.println("Collection de documents sélectionnée avec succès.");

            // Query the database to find documents with the specified task ID
            List<org.bson.Document> documentList = documentCollection.find(Filters.eq("IdTache", taskId)).into(new ArrayList<>());
            System.out.println("Nombre de documents trouvés pour la tâche " + taskId + ": " + documentList.size());

            // Convert each Document into a Document object
            for (org.bson.Document doc : documentList) {
                Document document = new Document();
                document.setIdDocument(doc.getObjectId("_id").toString());
                document.setNom(doc.getString("Nom_doc"));
                document.setDate_ajout(doc.getDate("Date_ajout"));
                document.setIdTache(doc.getString("IdTache"));
                document.setIdProjet(doc.getString("IdProjet"));
                document.setIdSeance(doc.getString("IdSeance"));
                document.setUrl(doc.getString("Url"));
                documents.add(document);

            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la récupération des documents pour la tâche " + taskId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return documents;
    }

    public List<Document> getDocumentsBySeanceId(String seanceId) {
        List<Document> documents = new ArrayList<>();
        try {
            MongoCollection<org.bson.Document> documentCollection = documentDatabase.getCollection("document");
            System.out.println("Collection de documents sélectionnée avec succès.");

            // Query the database to find documents with the specified seance ID
            List<org.bson.Document> documentList = documentCollection.find(Filters.eq("IdSeance", seanceId)).into(new ArrayList<>());
            System.out.println("Nombre de documents trouvés pour la séance " + seanceId + ": " + documentList.size());

            // Convert each Document into a Document object
            for (org.bson.Document doc : documentList) {
                Document document = new Document();
                document.setIdDocument(doc.getObjectId("_id").toString());
                document.setNom(doc.getString("Nom_doc"));
                document.setDate_ajout(doc.getDate("Date_ajout"));
                document.setIdTache(doc.getString("IdTache"));
                document.setIdProjet(doc.getString("IdProjet"));
                document.setIdSeance(doc.getString("IdSeance"));
                document.setUrl(doc.getString("Url"));
                documents.add(document);

            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la récupération des documents pour la séance " + seanceId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return documents;
    }
}
