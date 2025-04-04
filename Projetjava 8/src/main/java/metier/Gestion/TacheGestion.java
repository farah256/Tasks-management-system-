package metier.Gestion;

import metier.pojo.Categorie;
import metier.pojo.Etat;
import metier.pojo.Tache;
import persistence.TacheDAO;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class TacheGestion {
    private TacheDAO tacheDAO;

    public TacheGestion(TacheDAO tacheDAO) {
        this.tacheDAO = tacheDAO;
    }

    public boolean create(Tache obj) {
        return this.tacheDAO.create(obj);
    }


    public Tache read(String id) {
        return this.tacheDAO.read(id);
    }


    public void update(Tache obj, String id) {
        this.tacheDAO.update(obj,id);
    }


    public void delete(String id) {
        this.tacheDAO.delete(id);
    }


    public List getAll() {
        return this.tacheDAO.getAll();
    }
    public List<Tache> filtrerProjetsParCategorie(Categorie categorie) {
        return this.tacheDAO.filtrerTachesParCategorie(categorie);
    }
    public List<Tache> trierTachesParDate() {
        return this.tacheDAO.trierTachesParDate();
    }
    public List<Tache> filtrerTachesParEtat(Etat etat) {
        return this.tacheDAO.filtrerTachesParEtat(etat);
    }

    public List<Tache> filtrerProjetsParMotCle(String motCle) {
        return this.tacheDAO.filtrerTachesParMotCle(motCle);

    }
    public List<Tache> filtrerTachesParDate(LocalDateTime date) {
        return this.tacheDAO.filtrerTachesParDate(date);

    }
    public List<Tache> filtrerTachesParDateDebut(Date dateDebut) {
        return this.tacheDAO.filtrerTachesParDateDebut(dateDebut);
    }
    public List<Tache> filtrerTaches(Etat etat, Categorie categorie) {
        return this.tacheDAO.filtrerTaches(etat,categorie);
    }
    public List<Tache>getTachesByProjectId(String projetId){
        return this.tacheDAO.getTachesByProjectId(projetId);
    }

    public List<Tache>getTachesByListId(String ListId){
        return this.tacheDAO.getTachesByListId(ListId);
    }
    public List<Tache>getTachesBySeanceId(String seanceId){
        return this.tacheDAO.getTachesBySeanceId(seanceId);
    }


    public void duplicate(String id) {
        this.tacheDAO.duplicate(id);
    }
    public void cloturer (String id) {
        this.tacheDAO.cloturer(id);
    }

}
