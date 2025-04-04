package metier.Gestion;

import metier.pojo.ListeTache;
import persistence.ListeDAO;

import java.util.List;

public class ListeGestion {


    private ListeDAO listeDAO;
    public ListeGestion(ListeDAO listeDAO) {
        this.listeDAO = listeDAO;
    }

    public boolean create(ListeTache obj) {
        return this.listeDAO.create(obj);
    }

    public ListeTache read(String id) {
        return this.listeDAO.read(id);
    }

    public void delete(String id) {
        this.listeDAO.delete(id);
    }

    public List<ListeTache> getAll() {
        return  this.listeDAO.getAll();
    }
}