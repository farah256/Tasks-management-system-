package metier.Gestion;

import metier.pojo.Projet;
import metier.pojo.Seance;
import persistence.SeanceDAO;

import java.util.List;

public class SeanceGestion {
    private SeanceDAO seanceDAO;

    public SeanceGestion(SeanceDAO seanceDAO) {
        this.seanceDAO = seanceDAO;
    }

    public  boolean create(Seance obj) {
       return this.seanceDAO.create(obj);
    }


    public Seance read(String id) {
        return this.seanceDAO.read(id);
    }

    public void update(Seance obj, String id) {
        this.seanceDAO.update(obj,id);
    }


    public void delete(String id) {
        this.seanceDAO.delete(id);
    }


    public List<Seance> getAll() {
        return this.seanceDAO.getAll();
    }
    public List<Seance> getSeancesByProjectId(String projectId) {return  this.seanceDAO.getSeancesByProjectId(projectId);}






    }
