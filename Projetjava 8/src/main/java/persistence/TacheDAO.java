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

public class TacheDAO implements DAO<Tache> {
    private MongoDatabase tachedatabase;

    public TacheDAO(MongoDatabase tachedatabase) {
        this.tachedatabase = tachedatabase;
    }

    @Override
    public boolean create(Tache tache) {
        System.out.println("Création d'un document dans la base de données.");
        try {
            MongoCollection<Document> tacheCollection = tachedatabase.getCollection("tache");

            // Création d'un document à partir des attributs du projet
            Document tachedoc = new Document("description", tache.getDescription())
                    .append("Etat", tache.getEtat().name())
                    .append("Date_debut", tache.getDate_debut())
                    .append("Date_fin", tache. getDate_fin())
                    .append("Categorie", tache.getCategorie().name())
                    .append("idList", tache.getIdList())
                    .append("IdTache", tache.getIdTache())
                    .append("IdProjet", tache.getIdProjet())
                    .append("IdSeance", tache.getIdSeance());


            // Insertion du document dans la collection

            tacheCollection.insertOne(tachedoc);

            String id = tachedoc.getObjectId("_id").toString();
            System.out.println("Document ajouté avec succès dans la collection 'tache'.");
            // Mise à jour de l'attribut idProjet du document avec l'ID généré
            tacheCollection.updateOne(Filters.eq("_id", tachedoc.getObjectId("_id")),
                    Updates.set("IdTache", id));

            System.out.println("document ajouter avec succés");
            return true;

        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de l'ajout du document dans la collection 'tache': " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Tache read(String id) {
        Tache tache = null;
        try {
            MongoCollection<Document> tacheCollection = tachedatabase.getCollection("tache");
            System.out.println("Collection de taches sélectionnée avec succès.");

            // Rechercher le projet par son ID
            Document tacheDoc = tacheCollection.find(Filters.eq("_id", new ObjectId(id))).first();

            // Si le projet est trouvé, convertir le document en objet Projet
            if (tacheDoc != null) {
                tache = new Tache();
                tache.setIdTache(tacheDoc.getObjectId("_id").toString()); // Utilisation de l'ID généré par MongoDB
                tache.setDescription(tacheDoc.getString("description"));
                tache.setDate_debut(tacheDoc.getDate("Date_debut")); // Utilisation de la casse correcte pour "Date_debut"
                tache.setDate_fin(tacheDoc.getDate("Date_fin"));
                tache.setIdList(tacheDoc.getString("idList"));
                tache.setIdProjet(tacheDoc.getString("IdProjet"));
                tache.setIdSeance(tacheDoc.getString("IdSeance"));// Utilisation de la casse correcte pour "Date_fin"
                String categorieString = tacheDoc.getString("Categorie");
                String etatString = tacheDoc.getString("Etat");
                Etat etatEnum = Etat.valueOf(etatString);
                tache.setEtat(etatEnum);
                Categorie categorieEnum = Categorie.valueOf(categorieString);
                tache.setCategorie(categorieEnum);
            } else {
                System.out.println("Aucun tache trouvé avec l'ID spécifié.");
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la lecture du tache par ID : " + e.getMessage());
            e.printStackTrace();
        }
        return tache;
    }
    @Override

    public void update(Tache tache, String id) {
        try {
            MongoCollection<Document> tachcollection = tachedatabase.getCollection("tache");
            System.out.println("tache collection selected successfully");

            // Création du document de mise à jour avec les nouvelles valeurs
            Document updateDocument = new Document("$set", new Document()
                    .append("description", tache.getDescription())
                    .append("Date_debut", tache.getDate_debut())
                    .append("Date_fin", tache.getDate_fin())
                    .append("Etat", tache.getEtat().name())); // Store the enum value as string

            // Récupère la version précédente du document avant la mise à jour
            Document oldDocument = tachcollection.findOneAndUpdate(
                    Filters.eq("IdTache", id),
                    updateDocument
            );
            System.out.println("old doc = " + oldDocument);

            // Récupère la nouvelle version du document après la mise à jour
            FindOneAndUpdateOptions optionAfter = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
            Document newDocument = tachcollection.findOneAndUpdate(
                    Filters.eq("IdTache", id),
                    updateDocument,
                    optionAfter
            );
            System.out.println("new doc = " + newDocument);

            System.out.println("Document mis à jour avec succès dans la collection tache'.");
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la mise à jour du document dans la collection 'tache': " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
            MongoCollection<Document> tachcollection = tachedatabase.getCollection("tache");
            System.out.println("tache collection selected successflly");

            DeleteResult deleteResult = tachcollection.deleteOne(Filters.eq("IdTache", id));
            long deletedCount = deleteResult.getDeletedCount();

            if (deletedCount == 0) {
                System.out.println("Aucun document avec l'ID spécifié n'a été trouvé.");
            } else {
                System.out.println("Nombre de documents supprimés : " + deletedCount);
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de suppression du document dans la collection 'tache': " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public List<Tache> getAll() {

        List<Tache> taches = new ArrayList<>();
        try {
            MongoCollection<Document> tachcollection = tachedatabase.getCollection("tache");
            System.out.println("Collection de taches sélectionnée avec succès.");
            System.out.println("get the range of documents as a list");
            List<Document> tacheList = tachcollection.find().into(new ArrayList<>());
            for (Document projetDoc : tacheList) {
                Tache tache = new Tache();
                tache.setIdTache(projetDoc.getString("IdTache"));
                tache.setDescription(projetDoc.getString("description"));
                tache.setDate_debut(projetDoc.getDate("Date_debut"));
                tache.setDate_fin(projetDoc.getDate("Date_fin"));
                Categorie categorieEnum = Categorie.valueOf(projetDoc.getString("Categorie"));
                tache.setCategorie(categorieEnum);
                Etat etatEnum=Etat.valueOf(projetDoc.getString("Etat"));
                tache.setEtat(etatEnum);


                taches.add(tache);
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la récupération des taches : " + e.getMessage());
            e.printStackTrace();
        }
        return taches;
    }

    public List<Tache> trierTachesParDate() {
        List<Tache> TachesTries = new ArrayList<>();
        try {
            MongoCollection<Document> tacheCollection = tachedatabase.getCollection("tache");
            System.out.println("Collection de taches sélectionnée avec succès.");

            // Tri des tâches par date de début (descending)
            List<Document> sortedTasks = tacheCollection.find()
                    .sort(Sorts.descending("Date_debut"))
                    .into(new ArrayList<>());

            // Convertir les documents en objets Tache
            for (Document tacheDoc : sortedTasks) {
                Tache tache = new Tache();
                tache.setIdTache(tacheDoc.getObjectId("_id").toString()); // Utilisation de l'ID généré par MongoDB
                tache.setDescription(tacheDoc.getString("description"));
                tache.setDate_debut(tacheDoc.getDate("Date_debut")); // Utilisation de la casse correcte pour "Date_debut"
                tache.setDate_fin(tacheDoc.getDate("Date_fin"));
                tache.setIdList(tacheDoc.getString("idList"));
                tache.setIdProjet(tacheDoc.getString("IdProjet"));
                tache.setIdSeance(tacheDoc.getString("IdSeance"));// Utilisation de la casse correcte pour "Date_fin"
                String categorieString = tacheDoc.getString("Categorie");
                String etatString = tacheDoc.getString("Etat");
                Etat etatEnum = Etat.valueOf(etatString);
                tache.setEtat(etatEnum);
                Categorie categorieEnum = Categorie.valueOf(categorieString);
                tache.setCategorie(categorieEnum);
                TachesTries.add(tache);
            }

        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors du tri des taches par date : " + e.getMessage());
            e.printStackTrace();
        }
        return TachesTries;
    }


    public List<Tache> filtrerTachesParCategorie(Categorie categorie) {
        List<Tache> tachesFiltres = new ArrayList<>();
        try {
            MongoCollection<Document> tacheCollection = tachedatabase.getCollection("tache");
            System.out.println("Collection de taches sélectionnée avec succès.");

            // Création d'un filtre pour rechercher des tâches par catégorie
            Document filter = new Document("Categorie", categorie.name());

            // Recherche des tâches correspondant au filtre
            List<Document> filteredTasks = tacheCollection.find(filter).into(new ArrayList<>());

            // Convertir les documents en objets Tache
            for (Document tacheDoc : filteredTasks) {
                // Vérifiez si le document n'est pas nul
                if (tacheDoc != null) {
                    Tache tache = new Tache();
                    tache.setIdTache(String.valueOf(tacheDoc.getLong("idTache")));
                    Etat EtaEnum = Etat.valueOf(tacheDoc.getString("Etat"));
                    tache.setEtat(EtaEnum);
                    tache.setDescription(tacheDoc.getString("description"));
                    tache.setDate_debut(tacheDoc.getDate("Date_debut"));
                    tache.setDate_fin(tacheDoc.getDate("Date_fin"));
                    tache.setIdList(tacheDoc.getString("idList"));
                    tache.setIdProjet(tacheDoc.getString("IdProjet"));
                    tache.setIdSeance(tacheDoc.getString("IdSeance"));
                    Categorie categorieEnum = Categorie.valueOf(tacheDoc.getString("Categorie"));
                    tache.setCategorie(categorieEnum);
                    tachesFiltres.add(tache);
                }
            }

        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors du filtrage des taches par catégorie : " + e.getMessage());
            e.printStackTrace();
        }
        return tachesFiltres;
    }

    public List<Tache> filtrerTachesParEtat(Etat etat) {
        List<Tache> tachesFiltres = new ArrayList<>();
        try {
            MongoCollection<Document> tacheCollection = tachedatabase.getCollection("tache");
            System.out.println("Collection de taches sélectionnée avec succès.");

            // Création d'un filtre pour rechercher des tâches par état
            Document filter = new Document("Etat", etat.name());

            // Recherche des tâches correspondant au filtre
            List<Document> filteredTasks = tacheCollection.find(filter).into(new ArrayList<>());

            // Convertir les documents en objets Tache
            for (Document tacheDoc : filteredTasks) {
                // Vérifiez si le document n'est pas nul
                if (tacheDoc != null) {
                    Tache tache = new Tache();
                    tache.setIdTache(String.valueOf(tacheDoc.getLong("idTache")));
                    Etat etatEnum = Etat.valueOf(tacheDoc.getString("Etat"));
                    tache.setEtat(etatEnum);
                    tache.setDescription(tacheDoc.getString("description"));
                    tache.setDate_debut(tacheDoc.getDate("Date_debut"));
                    tache.setDate_fin(tacheDoc.getDate("Date_fin"));
                    tache.setIdList(tacheDoc.getString("idList"));
                    tache.setIdProjet(tacheDoc.getString("IdProjet"));
                    tache.setIdSeance(tacheDoc.getString("IdSeance"));
                    Categorie categorieEnum = Categorie.valueOf(tacheDoc.getString("Categorie"));
                    tache.setCategorie(categorieEnum);
                    tachesFiltres.add(tache);
                }
            }

        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors du filtrage des taches par état : " + e.getMessage());
            e.printStackTrace();
        }
        return tachesFiltres;
    }

    public List<Tache> filtrerTachesParMotCle(String motCle) {
        List<Tache> tachesFiltres = new ArrayList<>();
        try {
            MongoCollection<Document> tacheCollection = tachedatabase.getCollection("tache");
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


    public void duplicate(String id) {
        try {
            // Recherche de la tâche avec l'ID spécifié
            Document tacheDocument = tachedatabase.getCollection("tache").find(Filters.eq("_id", new ObjectId(id))).first();

            // Si la tâche existe
            if (tacheDocument != null) {
                // Création d'une copie de la tâche
                Tache duplicatedTache = new Tache();
                duplicatedTache.setDescription(tacheDocument.getString("description"));
                duplicatedTache.setDate_debut(tacheDocument.getDate("Date_debut"));
                duplicatedTache.setDate_fin(tacheDocument.getDate("Date_fin"));
                duplicatedTache.setIdList(tacheDocument.getString("idList"));
                duplicatedTache.setIdProjet(tacheDocument.getString("IdProjet"));
                duplicatedTache.setIdSeance(tacheDocument.getString("IdSeance"));
                Categorie categorie = Categorie.valueOf(tacheDocument.getString("Categorie"));
                duplicatedTache.setCategorie(categorie);
                Etat etat = Etat.valueOf(tacheDocument.getString("Etat"));
                duplicatedTache.setEtat(etat);

                // Création de la tâche dupliquée dans la base de données
                create(duplicatedTache);

                System.out.println("La tâche a été dupliquée avec succès.");
            } else {
                // Si aucune tâche n'est trouvée avec l'ID spécifié
                System.out.println("Aucune tâche trouvée avec l'ID spécifié pour la duplication.");
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la duplication de la tâche : " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void cloturer(String id) {
        try {
            // Récupérer la tâche à clôturer
            Document tacheDocument = tachedatabase.getCollection("tache").find(Filters.eq("_id", new ObjectId(id))).first();

            // Si la tâche existe
            if (tacheDocument != null) {
                // Insérer la tâche dans la collection historique
                tachedatabase.getCollection("historique").insertOne(tacheDocument);

                // Supprimer la tâche de la collection principale
                DeleteResult deleteResult = tachedatabase.getCollection("tache").deleteOne(Filters.eq("_id", new ObjectId(id)));
                long deletedCount = deleteResult.getDeletedCount();

                if (deletedCount > 0) {
                    System.out.println("La tâche a été clôturée avec succès.");
                } else {
                    System.out.println("Erreur lors de la clôture de la tâche : la tâche n'a pas été supprimée de la collection principale.");
                }
            } else {
                // Si aucune tâche n'est trouvée avec l'ID spécifié
                System.out.println("Aucune tâche trouvée avec l'ID spécifié pour la clôture.");
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la clôture de la tâche : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Tache> filtrerTachesParDate(LocalDateTime date) {
        List<Tache> tachesFiltrees = new ArrayList<>();
        try {
            MongoCollection<Document> tacheCollection = tachedatabase.getCollection("tache");
            System.out.println("Collection de taches sélectionnée avec succès.");

            // Création d'un filtre pour rechercher des tâches par date de début
            Document filter = new Document("Date_debut", Date.from(date.atZone(ZoneId.systemDefault()).toInstant()));

            // Recherche des tâches correspondant au filtre
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
                tachesFiltrees.add(tache);
            }

        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors du filtrage des taches par date de début : " + e.getMessage());
            e.printStackTrace();
        }
        return tachesFiltrees;
    }

    public List<Tache> filtrerTachesParDateDebut(Date dateDebut) {
        List<Tache> tachesFiltrees = new ArrayList<>();
        try {
            MongoCollection<Document> tacheCollection = tachedatabase.getCollection("tache");
            System.out.println("Collection de taches sélectionnée avec succès.");

            // Création d'un filtre pour rechercher des taches par dateDebut
            // Création d'un filtre pour rechercher des tâches par dateDebut
            Document filter = new Document("Date_debut", new Document("$gte", dateDebut));

            // Recherche des taches correspondant au filtre
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
                Etat etatEnum = Etat.valueOf(tacheDoc.getString("Etat"));
                tache.setEtat(etatEnum);
                Categorie categorieEnum = Categorie.valueOf(tacheDoc.getString("Categorie"));
                tache.setCategorie(categorieEnum);
                tachesFiltrees.add(tache);
            }

        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors du filtrage des taches par dateDebut : " + e.getMessage());
            e.printStackTrace();
        }
        return tachesFiltrees;
    }
//    public void insererList(String idListe, String idTache) {
//        try {
//            // Récupérer la liste de tâches à laquelle vous souhaitez ajouter la tâche
//            Document listeDocument = tachedatabase.getCollection("ListeTache").find(Filters.eq("_id", new ObjectId(idListe))).first();
//
//            // Si la liste de tâches existe
//            if (listeDocument != null) {
//                // Ajouter l'ID de la tâche à la liste des tâches de la liste
//                List<String> taches = listeDocument.getList("taches", String.class);
//                taches.add(idTache);
//
//                // Mettre à jour la liste de tâches dans la base de données
//                tachedatabase.getCollection("ListeTache").updateOne(Filters.eq("_id", new ObjectId(idListe)),
//                        Updates.set("taches", taches));
//
//                System.out.println("La tâche a été ajoutée à la liste de tâches avec succès.");
//            } else {
//                // Si aucune liste de tâches n'est trouvée avec l'ID spécifié
//                System.out.println("Aucune liste de tâches trouvée avec l'ID spécifié pour ajouter la tâche.");
//            }
//        } catch (Exception e) {
//            System.err.println("Une erreur est survenue lors de l'ajout de la tâche à la liste de tâches : " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
public List<Tache> filtrerTaches(Etat etat, Categorie categorie) {
    List<Tache> tachesFiltres = new ArrayList<>();
    try {
        MongoCollection<Document> tacheCollection = tachedatabase.getCollection("tache");
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
    public List<Tache> getTachesByProjectId(String projectId) {
        List<Tache> taches = new ArrayList<>();
        try {
            MongoCollection<Document> tacheCollection = tachedatabase.getCollection("tache");
            System.out.println("Collection de taches sélectionnée avec succès.");

            List<Document> tacheList = tacheCollection.find(Filters.eq("IdProjet", projectId)).into(new ArrayList<>());
            System.out.println("Nombre de documents trouvés: " + tacheList.size());

            for (Document tacheDoc : tacheList) {
                System.out.println("Document: " + tacheDoc.toJson());
                Tache tache = new Tache();
                tache.setIdTache(tacheDoc.getString("IdTache"));
                tache.setDescription(tacheDoc.getString("description"));
                tache.setDate_debut(tacheDoc.getDate("Date_debut"));
                tache.setDate_fin(tacheDoc.getDate("Date_fin"));
                tache.setIdProjet(tacheDoc.getString("IdProjet"));
                Categorie categorieEnum = Categorie.valueOf(tacheDoc.getString("Categorie"));
                tache.setCategorie(categorieEnum);
                Etat etatEnum = Etat.valueOf(tacheDoc.getString("Etat"));
                tache.setEtat(etatEnum);

                taches.add(tache);
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la récupération des taches : " + e.getMessage());
            e.printStackTrace();
        }
        return taches;
    }
    public List<Tache> getTachesByListId(String ListId) {
        List<Tache> taches = new ArrayList<>();
        try {
            MongoCollection<Document> tacheCollection = tachedatabase.getCollection("tache");
            System.out.println("Collection de taches sélectionnée avec succès.");

            List<Document> tacheList = tacheCollection.find(Filters.eq("idList", ListId)).into(new ArrayList<>());
            System.out.println("Nombre de documents trouvés: " + tacheList.size());

            for (Document tacheDoc : tacheList) {
                System.out.println("Document: " + tacheDoc.toJson());
                Tache tache = new Tache();
                tache.setIdTache(tacheDoc.getString("IdTache"));
                tache.setDescription(tacheDoc.getString("description"));
                tache.setDate_debut(tacheDoc.getDate("Date_debut"));
                tache.setDate_fin(tacheDoc.getDate("Date_fin"));
                tache.setIdList(tacheDoc.getString("idList"));
                Categorie categorieEnum = Categorie.valueOf(tacheDoc.getString("Categorie"));
                tache.setCategorie(categorieEnum);
                Etat etatEnum = Etat.valueOf(tacheDoc.getString("Etat"));
                tache.setEtat(etatEnum);

                taches.add(tache);
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la récupération des taches : " + e.getMessage());
            e.printStackTrace();
        }
        return taches;
    }
    public List<Tache> getTachesBySeanceId(String seanceId) {
        List<Tache> taches = new ArrayList<>();
        try {
            MongoCollection<Document> tachcollection = tachedatabase.getCollection("tache");
            System.out.println("Collection de taches sélectionnée avec succès.");

            List<Document> tacheList = tachcollection.find(Filters.eq("IdSeance",seanceId)).into(new ArrayList<>());
            for (Document projetDoc : tacheList) {
                System.out.println("Document: " + projetDoc.toJson());
                Tache tache = new Tache();
                tache.setIdTache(projetDoc.getString("IdTache"));
                tache.setDescription(projetDoc.getString("description"));
                tache.setDate_debut(projetDoc.getDate("Date_debut"));
                tache.setDate_fin(projetDoc.getDate("Date_fin"));
                tache.setIdTache(projetDoc.getString("IdSeance"));
                Categorie categorieEnum = Categorie.valueOf(projetDoc.getString("Categorie"));
                tache.setCategorie(categorieEnum);
                Etat etatEnum=Etat.valueOf(projetDoc.getString("Etat"));
                tache.setEtat(etatEnum);


                taches.add(tache);
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la récupération des taches : " + e.getMessage());
            e.printStackTrace();
        }
        return taches;
    }



}
















