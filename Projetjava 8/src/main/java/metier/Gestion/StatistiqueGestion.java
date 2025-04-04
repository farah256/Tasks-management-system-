package metier.Gestion;

import metier.pojo.Categorie;
import metier.service.Statistique;

public class StatistiqueGestion {
    private Statistique statistique;



    public StatistiqueGestion(Statistique statistique) {
        this.statistique = statistique;
    }

    public Statistique getStatistique() {
        return statistique;
    }

    public double calculerTotalHeuresTravaillees(String id) {
        return statistique.calculerTotalHeuresTravaillees(id);
    }

    public long compterDocumentsParProjet(String id) {
        return statistique.compterDocumentsParProjet(id);
    }
    public double getHeuresTravailleesSemaine() {
        return statistique.calculerHeuresTravailleesSemaine();
    }

    public double getHeuresTravailleesMois() {
        return statistique.calculerHeuresTravailleesMois();
    }

    public double getHeuresTravailleesAnnee() {
        return statistique.calculerHeuresTravailleesAnnee();
    }


    public long compterNombreProjets() {
        return statistique.compterNombreProjets();
    }

    public long compterNombreTaches() {
       return statistique.compterNombreTaches();
    }

    public long compterNombreSeances() {
       return statistique.compterNombreSeances();
    }

}
