package metier.Gestion;

import metier.pojo.Categorie;
import metier.pojo.Etat;
import metier.pojo.Tache;
import persistence.HistoriqueTacheDAO;

import java.util.List;

public class HistoriqueTacheGestion {
    private HistoriqueTacheDAO historiqueTacheDAO;
    public HistoriqueTacheGestion(HistoriqueTacheDAO historiqueTacheDAO) {
        this.historiqueTacheDAO = historiqueTacheDAO;
    }
    public List<Tache>  filtrerTachesParMotCle(String motCle) {
        return this.historiqueTacheDAO.filtrerTachesParMotCle(motCle);

    }

    public List<Tache> filtrerTaches(Etat etat, Categorie categorie) {
        return this.historiqueTacheDAO.filtrerTaches(etat,categorie);
    }

    public List getallTache(){
        return this.historiqueTacheDAO.getallTache();
    }

}

