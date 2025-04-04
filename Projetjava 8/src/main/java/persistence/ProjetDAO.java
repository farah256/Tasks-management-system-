package persistence;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import metier.pojo.Categorie;
import metier.pojo.Projet;
import metier.pojo.Type;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class ProjetDAO implements DAO<Projet> {
    private MongoDatabase projetdatabase;

    public ProjetDAO(MongoDatabase projetdatabase) {
        this.projetdatabase = projetdatabase;
    }

    @Override
    public boolean create(Projet projet) {
        System.out.println("Création d'un projet dans la base de données.");
        try {
            MongoCollection<Document> projetCollection = projetdatabase.getCollection("projet");

            // Création d'un document à partir des attributs du projet
            Document doc = new Document("Categorie", projet.getCategorie().name())
                    .append("description", projet.getDescription())
                    .append("dateDebut", projet.getDate_debut())
                    .append("dateFin", projet.getDate_fin())
                    .append("type", projet.getType().name());

            // Insertion du document dans la collection
            projetCollection.insertOne(doc);

            // Récupération de l'ID généré par MongoDB
            String id = doc.getObjectId("_id").toString();

            // Mise à jour de l'attribut idProjet du document avec l'ID généré
            projetCollection.updateOne(Filters.eq("_id", doc.getObjectId("_id")),
                    Updates.set("IdProjet", id));

            System.out.println("Document ajouté avec succès dans la collection 'projet'.");
            return true;
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de l'ajout du document dans la collection 'projet': " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    // Méthode pour obtenir le prochain ID à partir de la séquence
    public Projet read(String id) {
        Projet projet = null;
        try {
            MongoCollection<Document> projetCollection = projetdatabase.getCollection("projet");
            System.out.println("Collection de projets sélectionnée avec succès.");

            // Rechercher le projet par son ID
            Document projetDoc = projetCollection.find(Filters.eq("_id", new ObjectId(id))).first();

            // Si le projet est trouvé, convertir le document en objet Projet
            if (projetDoc != null) {
                projet = new Projet();
                projet.setIdProjet(projetDoc.getObjectId("_id").toString());
                projet.setDescription(projetDoc.getString("description"));
                projet.setDate_debut(projetDoc.getDate("dateDebut"));
                projet.setDate_fin(projetDoc.getDate("dateFin"));
                Categorie categorieEnum = Categorie.valueOf(projetDoc.getString("Categorie"));
                projet.setCategorie(categorieEnum);
                Type typeEnum = Type.valueOf(projetDoc.getString("type"));
                projet.setType(typeEnum);
            } else {
                System.out.println("Aucun projet trouvé avec l'ID spécifié.");
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la lecture du projet par ID : " + e.getMessage());
            e.printStackTrace();
        }
        return projet;
    }


//    @Override
//    public Projet read(String id) {
//        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
//            MongoDatabase projetdatabase = mongoClient.getDatabase("your_database_name"); // Replace "your_database_name" with your actual database name
//            MongoCollection<Document> projetcollection = projetdatabase.getCollection("projet");
//            System.out.println("projet collection selected successfully");
//
//            // Get the document matching the provided project ID
//            Document projetDoc = projetcollection.find(new Document("IdProjet", id)).first();
//            System.out.println("projet document= " + projetDoc);
//
//            // If the document is found, construct and return a Projet object
//            if (projetDoc != null) {
//                Projet projet = new Projet();
//                projet.setIdProjet(projetDoc.getString("IdProjet")); // Assuming "IdProjet" is a string field
//                projet.setIdProjet(projetDoc.getString("IdProjet"));
//                projet.setDescription(projetDoc.getString("description"));
//                projet.setDate_debut(projetDoc.getDate("dateDebut"));
//                projet.setDate_fin(projetDoc.getDate("dateFin"));
//                Categorie categorieEnum = Categorie.valueOf(projetDoc.getString("Categorie"));
//                projet.setCategorie(categorieEnum);
//                Type typeEnum = Type.valueOf(projetDoc.getString("type"));
//                projet.setType(typeEnum);
//                return projet;
//            } else {
//                System.out.println("Project not found for ID: " + id);
//            }
//        } catch (MongoException e) {
//            System.err.println("MongoDB Exception: " + e.getMessage());
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.err.println("An error occurred while reading the project: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
//    }
//


    @Override
        public void update(Projet projet, String id) {
            try {
                MongoCollection<Document> projetCollection = projetdatabase.getCollection("projet");
                System.out.println("projet collection selected successfully");

                // Création du document de mise à jour avec les nouvelles valeurs
                Document updateDocument = new Document("$set", new Document()
                        .append("Categorie",projet.getCategorie().name())
                        .append("description", projet.getDescription())
                        .append("dateDebut", projet.getDate_debut())
                        .append("dateFin", projet.getDate_fin())
                        .append("type", projet.getType().name())
                );

                // Récupère la version précédente du document avant la mise à jour
                Document oldDocument = projetCollection.findOneAndUpdate(
                        Filters.eq("IdProjet", id),
                        updateDocument
                );
                System.out.println("old doc = " + oldDocument);

                // Récupère la nouvelle version du document après la mise à jour
                FindOneAndUpdateOptions optionAfter = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
                Document newDocument = projetCollection.findOneAndUpdate(
                        Filters.eq("IdProjet", id),
                        updateDocument,
                        optionAfter
                );
                System.out.println("new doc = " + newDocument);

                System.out.println("Document mis à jour avec succès dans la collection 'projet'.");
            } catch (Exception e) {
                System.err.println("Une erreur est survenue lors de la mise à jour du document dans la collection 'projet': " + e.getMessage());
                e.printStackTrace();
            }
        }


    @Override
        public void delete (String id){
        try (MongoClient mongoClient=new MongoClient("localhost",27017)) {
            MongoCollection<Document> projetcollection = projetdatabase.getCollection("projet");
            System.out.println("projet collection selected successflly");

            DeleteResult deleteResult = projetcollection.deleteOne(Filters.eq("IdProjet", id));
            long deletedCount = deleteResult.getDeletedCount();

            if (deletedCount == 0) {
                System.out.println("Aucun document avec l'ID spécifié n'a été trouvé.");
            } else {
                System.out.println("Nombre de documents supprimés : " + deletedCount);
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de suppression du document dans la collection 'projet': " + e.getMessage());
            e.printStackTrace();
        }

    }



    @Override
    public List<Projet> getAll() {
        List<Projet> projets = new ArrayList<>();
        try {
            MongoCollection<Document> projetCollection = projetdatabase.getCollection("projet");
            System.out.println("Collection de projets sélectionnée avec succès.");
            System.out.println("get the range of documents as a list");
            List<Document> projectList = projetCollection.find().into(new ArrayList<>());
            for (Document projetDoc : projectList) {
                Projet projet = new Projet();
                projet.setIdProjet(projetDoc.getString("IdProjet"));
                projet.setDescription(projetDoc.getString("description"));
                projet.setDate_debut(projetDoc.getDate("dateDebut"));
                projet.setDate_fin(projetDoc.getDate("dateFin"));
                Categorie categorieEnum = Categorie.valueOf(projetDoc.getString("Categorie"));
                projet.setCategorie(categorieEnum);
                Type typeEnum = Type.valueOf(projetDoc.getString("type"));
                projet.setType(typeEnum);

                projets.add(projet);
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la récupération des projets : " + e.getMessage());
            e.printStackTrace();
        }
        return projets;
    }

    public List<Projet> filtrerProjetsParCategorie(Categorie categorie) {
        List<Projet> projetsFiltres = new ArrayList<>();
        try {
            MongoCollection<Document> projetCollection = projetdatabase.getCollection("projet");
            System.out.println("Collection de projets sélectionnée avec succès.");

            // Création d'un filtre pour rechercher des projets par catégorie
            Document filter = new Document("Categorie", categorie.name());

            // Recherche des projets correspondant au filtre
            List<Document> filteredProjects = projetCollection.find(filter).into(new ArrayList<>());

            // Convertir les documents en objets Projet
            for (Document projetDoc : filteredProjects) {
                Projet projet = new Projet();
                projet.setIdProjet(projetDoc.getObjectId("_id").toString());
                projet.setDescription(projetDoc.getString("description"));
                projet.setDate_debut(projetDoc.getDate("dateDebut"));
                projet.setDate_fin(projetDoc.getDate("dateFin"));
                Categorie categorieEnum = Categorie.valueOf(projetDoc.getString("Categorie"));
                projet.setCategorie(categorieEnum);
                Type typeEnum = Type.valueOf(projetDoc.getString("type"));
                projet.setType(typeEnum);
                projetsFiltres.add(projet);
            }

        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors du filtrage des projets par catégorie : " + e.getMessage());
            e.printStackTrace();
        }
        return projetsFiltres;
    }

    public List<Projet> filtrerProjetsParType(Type type) {
        List<Projet> projetsFiltres = new ArrayList<>();
        try {
            MongoCollection<Document> projetCollection = projetdatabase.getCollection("projet");
            System.out.println("Collection de projets sélectionnée avec succès.");

            // Création d'un filtre pour rechercher des projets par type
            Document filter = new Document("type", type.name());

            // Recherche des projets correspondant au filtre
            List<Document> filteredProjects = projetCollection.find(filter).into(new ArrayList<>());

            // Convertir les documents en objets Projet
            for (Document projetDoc : filteredProjects) {
                Projet projet = new Projet();
                projet.setIdProjet(projetDoc.getObjectId("_id").toString());
                projet.setDescription(projetDoc.getString("description"));
                projet.setDate_debut(projetDoc.getDate("dateDebut"));
                projet.setDate_fin(projetDoc.getDate("dateFin"));
                Categorie categorieEnum = Categorie.valueOf(projetDoc.getString("Categorie"));
                projet.setCategorie(categorieEnum);
                Type typeEnum = Type.valueOf(projetDoc.getString("type"));
                projet.setType(typeEnum);
                projetsFiltres.add(projet);
            }

        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors du filtrage des projets par type : " + e.getMessage());
            e.printStackTrace();
        }
        return projetsFiltres;
    }

    public List<Projet> filtrerProjetsParMotCle(String motCle) {
        List<Projet> projetsFiltres = new ArrayList<>();
        try {
            MongoCollection<Document> projetCollection = projetdatabase.getCollection("projet");
            System.out.println("Collection de projets sélectionnée avec succès.");

            // Create a regex filter to search for partial matches in the description
            Pattern regexPattern = Pattern.compile(motCle, Pattern.CASE_INSENSITIVE);
            Document filter = new Document("description", regexPattern);

            // Search for projects matching the filter
            List<Document> filteredProjects = projetCollection.find(filter).into(new ArrayList<>());

            // Convert documents to Projet objects
            for (Document projetDoc : filteredProjects) {
                Projet projet = new Projet();
                projet.setIdProjet(projetDoc.getObjectId("_id").toString());
                projet.setDescription(projetDoc.getString("description"));
                projet.setDate_debut(projetDoc.getDate("dateDebut"));
                projet.setDate_fin(projetDoc.getDate("dateFin"));
                String categorieString = projetDoc.getString("Categorie");
                Categorie categorieEnum = Categorie.valueOf(categorieString);
                projet.setCategorie(categorieEnum);
                String typeString = projetDoc.getString("type");
                Type typeEnum = Type.valueOf(typeString);
                projet.setType(typeEnum);
                projetsFiltres.add(projet);
            }

        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors du filtrage des projets par mot-clé dans la description : " + e.getMessage());
            e.printStackTrace();
        }
        return projetsFiltres;
    }

    public void duplicate(String id) {
        try {
            // Recherche du projet avec l'ID spécifié
            Document projetDocument = projetdatabase.getCollection("projet").find(Filters.eq("_id", new ObjectId(id))).first();

            // Si le projet existe
            if (projetDocument != null) {
                // Création d'une copie du projet
                Projet duplicatedProjet = new Projet();
                duplicatedProjet.setDescription(projetDocument.getString("description"));
                duplicatedProjet.setDate_debut(projetDocument.getDate("dateDebut"));
                duplicatedProjet.setDate_fin(projetDocument.getDate("dateFin"));
                Categorie categorie = Categorie.valueOf(projetDocument.getString("Categorie"));
                duplicatedProjet.setCategorie(categorie);
                Type type = Type.valueOf(projetDocument.getString("type"));
                duplicatedProjet.setType(type);

                // Création du projet dupliqué dans la base de données
                create(duplicatedProjet);

                System.out.println("Le projet a été dupliqué avec succès.");
            } else {
                // Si aucun projet n'est trouvé avec l'ID spécifié
                System.out.println("Aucun projet trouvé avec l'ID spécifié pour la duplication.");
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la duplication du projet : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cloturer (String id) {
        try {

            System.out.println("Collection de projets sélectionnée avec succès.");

            // Rechercher le projet à clôturer
            Document projetDocument = projetdatabase.getCollection("projet").find(Filters.eq("_id", new ObjectId(id))).first();

            if (projetDocument != null) {
                // Copier le projet clôturé vers la collection "historique"
                projetdatabase.getCollection("historiqueprojet").insertOne(projetDocument);

                // Supprimer le projet de la collection principale
                DeleteResult deleteResult = projetdatabase.getCollection("projet").deleteOne(Filters.eq("_id", new ObjectId(id)));

                long deletedCount = deleteResult.getDeletedCount();

                if (deletedCount > 0) {
                    System.out.println("Le projet a été clôturé avec succès.");

                } else {
                    System.out.println("Erreur lors de la clôture de projet: la tâche n'a pas été supprimée de la collection principale.");
                }
            } else {
                // Si aucune tâche n'est trouvée avec l'ID spécifié
                System.out.println("Aucune projet trouvée avec l'ID spécifié pour la clôture.");
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la clôture du projet : " + e.getMessage());
            e.printStackTrace();
        }
    }
    public List<Projet> filtrerProjetsParTypeEtCategorie(Type type, Categorie categorie) {
        List<Projet> projetsFiltres = new ArrayList<>();
        try {
            MongoCollection<Document> projetCollection = projetdatabase.getCollection("projet");
            System.out.println("Collection de projets sélectionnée avec succès.");

            // Création d'un filtre pour rechercher des projets par type et catégorie
            Document filter = new Document();
            filter.append("type", type.name());
            filter.append("Categorie", categorie.name());

            // Recherche des projets correspondant au filtre
            List<Document> filteredProjects = projetCollection.find(filter).into(new ArrayList<>());

            // Convertir les documents en objets Projet
            for (Document projetDoc : filteredProjects) {
                Projet projet = new Projet();
                projet.setIdProjet(projetDoc.getObjectId("_id").toString());
                projet.setDescription(projetDoc.getString("description"));
                projet.setDate_debut(projetDoc.getDate("dateDebut"));
                projet.setDate_fin(projetDoc.getDate("dateFin"));
                Categorie categorieEnum = Categorie.valueOf(projetDoc.getString("Categorie"));
                projet.setCategorie(categorieEnum);
                Type typeEnum = Type.valueOf(projetDoc.getString("type"));
                projet.setType(typeEnum);
                projetsFiltres.add(projet);
            }

        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors du filtrage des projets par type et catégorie : " + e.getMessage());
            e.printStackTrace();
        }
        return projetsFiltres;
    }


}

































