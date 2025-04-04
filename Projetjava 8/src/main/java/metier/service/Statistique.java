package metier.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import metier.pojo.Categorie;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.security.cert.CollectionCertStoreParameters;
import java.util.Calendar;
import java.util.Date;

public class Statistique {
    private MongoDatabase projetdatabase;

    public Statistique(MongoDatabase projetdatabase) {
        this.projetdatabase = projetdatabase;
    }
    public double calculerTotalHeuresTravaillees(String id) {
        try {
            MongoCollection<Document> collectionProjet = projetdatabase.getCollection("projet");
            Document documentProjet = collectionProjet.find(Filters.eq("_id", new ObjectId(id))).first();

            if (documentProjet != null) {
                Date dateDebut = (Date) documentProjet.get("dateDebut");
                Date dateFin = (Date) documentProjet.get("dateFin");

                long totalMillisecondes = dateFin.getTime() - dateDebut.getTime();
                // Convertir les millisecondes en heures
                return totalMillisecondes / (1000.0 * 60 * 60);
            } else {
                System.out.println("Projet non trouvé avec l'identifiant : " + id);
                return 0.0;
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du calcul du total des heures travaillées : " + e.getMessage());
            e.printStackTrace();
            return 0.0;
        }
    }
    public long compterDocumentsParProjet(String id) {
        try {
            MongoCollection<Document> collectionProjet = projetdatabase.getCollection("projet");

            // Utiliser l'identifiant unique du projet pour filtrer les documents associés à ce projet
            Document query = new Document("idProjet", id);

            // Compter le nombre de documents dans la collection correspondant au projet spécifié
            long count = collectionProjet.countDocuments(query);

            return count;
        } catch (Exception e) {
            System.err.println("Erreur lors du comptage des documents par projet : " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
    private double calculerTotalHeuresTravailleesParPeriode(String id, int periode) {
        try {
            MongoCollection<Document> collectionProjet = projetdatabase.getCollection("projet");
            Document documentProjet = collectionProjet.find(Filters.eq("_id", new ObjectId(id))).first();

            if (documentProjet != null) {
                Date dateDebut = (Date) documentProjet.get("dateDebut");
                Date dateFin = (Date) documentProjet.get("dateFin");

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateDebut);
                int debutPeriode = calendar.get(periode);
                calendar.setTime(dateFin);
                int finPeriode = calendar.get(periode);

                long totalMillisecondes = dateFin.getTime() - dateDebut.getTime();
                double heuresProjet = totalMillisecondes / (1000.0 * 60 * 60);

                int nombrePeriodes = finPeriode - debutPeriode + 1;
                return heuresProjet / nombrePeriodes;
            } else {
                System.out.println("Projet non trouvé avec l'identifiant : " + id);
                return 0.0;
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du calcul du total des heures travaillées : " + e.getMessage());
            e.printStackTrace();
            return 0.0;
        }
    }

    public double calculerHeuresTravailleesSemaine() {
        return calculerHeuresTravailleesPeriode(Calendar.WEEK_OF_YEAR);
    }

    public double calculerHeuresTravailleesMois() {
        return calculerHeuresTravailleesPeriode(Calendar.MONTH);
    }

    public double calculerHeuresTravailleesAnnee() {
        return calculerHeuresTravailleesPeriode(Calendar.YEAR);
    }

    private double calculerHeuresTravailleesPeriode(int periode) {
        double totalHeures = 0.0;

        try {
            MongoCollection<Document> collectionProjet = projetdatabase.getCollection("projet");

            for (Document documentProjet : collectionProjet.find()) {
                Date dateDebut = (Date) documentProjet.get("dateDebut");
                Date dateFin = (Date) documentProjet.get("dateFin");

                if (dateDebut != null && dateFin != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateDebut);
                    int debutPeriode = calendar.get(periode);
                    calendar.setTime(dateFin);
                    int finPeriode = calendar.get(periode);

                    long totalMillisecondes = dateFin.getTime() - dateDebut.getTime();
                    double heuresProjet = totalMillisecondes / (1000.0 * 60 * 60);

                    int nombrePeriodes = finPeriode - debutPeriode + 1;
                    totalHeures += heuresProjet / nombrePeriodes;
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du calcul du nombre d'heures travaillées par période : " + e.getMessage());
            e.printStackTrace();
        }

        return totalHeures;
    }

//    public double calculerPourcentageHeuresTravailleesParCategorieSemaine(Categorie categorie) {
//        return calculerPourcentageHeuresTravailleesParCategoriePeriode(categorie, Calendar.WEEK_OF_YEAR);
//    }
//
//    public double calculerPourcentageHeuresTravailleesParCategorieMois(Categorie categorie) {
//        return calculerPourcentageHeuresTravailleesParCategoriePeriode(categorie, Calendar.MONTH);
//    }
//
//    public double calculerPourcentageHeuresTravailleesParCategorieAnnee(Categorie categorie) {
//        return calculerPourcentageHeuresTravailleesParCategoriePeriode(categorie, Calendar.YEAR);
//    }
//
//    private double calculerPourcentageHeuresTravailleesParCategoriePeriode(Categorie categorie, int periode) {
//        double totalHeures = 0.0;
//        double heuresCategorie = 0.0;
//
//        try {
//            MongoCollection<Document> collectionProjet = projetdatabase.getCollection("projet");
//
//            for (Document documentProjet : collectionProjet.find()) {
//                Date dateDebut = (Date) documentProjet.get("dateDebut");
//                Date dateFin = (Date) documentProjet.get("dateFin");
//
//                if (dateDebut != null && dateFin != null) {
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.setTime(dateDebut);
//                    int debutPeriode = calendar.get(periode);
//                    calendar.setTime(dateFin);
//                    int finPeriode = calendar.get(periode);
//
//                    long totalMillisecondes = dateFin.getTime() - dateDebut.getTime();
//                    double heuresProjet = totalMillisecondes / (1000.0 * 60 * 60);
//                    int nombrePeriodes = finPeriode - debutPeriode + 1;
//
//                    totalHeures += heuresProjet / nombrePeriodes;
//
//                    // Check if the project belongs to the specified category
//                    if (documentProjet.getString("categorie").equals(categorie.name())) {
//                        heuresCategorie += heuresProjet / nombrePeriodes;
//                    }
//                }
//            }
//
//            if (totalHeures > 0) {
//                return (heuresCategorie / totalHeures) * 100;
//            } else {
//                return 0.0;
//            }
//
//        } catch (Exception e) {
//            System.err.println("Erreur lors du calcul du pourcentage d'heures travaillées par catégorie : " + e.getMessage());
//            e.printStackTrace();
//            return 0.0;
//        }
//    }

    public String formatMillisToTimeString(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        minutes = minutes % 60;
        hours = hours % 24;

        return String.format("%d J %d H %d M", days, hours, minutes);
    }

    public long compterNombreProjets() {
        try {
            MongoCollection<Document> collectionProjet = projetdatabase.getCollection("projet");

            // Count all documents in the "projet" collection
            return collectionProjet.countDocuments();
        } catch (Exception e) {
            System.err.println("Erreur lors du comptage du nombre de projets : " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public long compterNombreTaches() {
        try {
            MongoCollection<Document> collectionTache = projetdatabase.getCollection("tache");

            // Count all documents in the "tache" collection
            return collectionTache.countDocuments();
        } catch (Exception e) {
            System.err.println("Erreur lors du comptage du nombre de tâches : " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public long compterNombreSeances() {
        try {
            MongoCollection<Document> collectionSeance = projetdatabase.getCollection("seance");

            // Count all documents in the "seance" collection
            return collectionSeance.countDocuments();
        } catch (Exception e) {
            System.err.println("Erreur lors du comptage du nombre de séances : " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }


}
