package persistence;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import metier.pojo.Categorie;
import metier.pojo.Projet;
import metier.pojo.Type;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class HistoriqueProjetDAO {

    private MongoDatabase projetdatabase;

    public HistoriqueProjetDAO(MongoDatabase projetdatabase) {
        this.projetdatabase = projetdatabase;
    }
    public List<Projet> getAllProject() {
        List<Projet> projets = new ArrayList<>();
        try {
            MongoCollection<Document> projetCollection = projetdatabase.getCollection("historiqueprojet");
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
    public List<Projet> filtrerProjetsParTypeEtCategorie(Type type, Categorie categorie) {
        List<Projet> projetsFiltres = new ArrayList<>();
        try {
            MongoCollection<Document> projetCollection = projetdatabase.getCollection("historiqueprojet");
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
    public List<Projet> filtrerProjetsParMotCle(String motCle) {
        List<Projet> projetsFiltres = new ArrayList<>();
        try {
            MongoCollection<Document> projetCollection = projetdatabase.getCollection("historiqueprojet");
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





}
