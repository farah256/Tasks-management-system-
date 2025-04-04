package metier.Gestion;

import metier.pojo.Categorie;
import metier.pojo.Projet;
import metier.pojo.Type;
import persistence.ProjetDAO;

import java.util.List;

public class ProjetGestion {
    private ProjetDAO projetDAO;

    public ProjetGestion(ProjetDAO projetDAO) {
        this.projetDAO = projetDAO;
    }

    public boolean create(Projet obj) {
      return this.projetDAO.create(obj);

    }

    public Projet read(String id) {
        return this.projetDAO.read(id);
    }


    public void update(Projet obj, String id) {
        this.projetDAO.update(obj,id);
    }


    public void delete(String id) {
        this.projetDAO.delete(id);
    }


    public List getAll() {
        return this.projetDAO.getAll();
    }
    public List<Projet> filtrerProjetsParCategorie(Categorie categorie) {
        return this.projetDAO.filtrerProjetsParCategorie(categorie);
    }
    public List<Projet> filtrerProjetsParType(Type type) {
        return this.projetDAO.filtrerProjetsParType(type);

    }

    public List<Projet> filtrerProjetsParMotCle(String motCle) {
        return this.projetDAO.filtrerProjetsParMotCle(motCle);

    }
    public List<Projet> filtrerProjetsParTypeEtCategorie(Type type, Categorie categorie) {
        return this.projetDAO.filtrerProjetsParTypeEtCategorie(type,categorie);
    }

    public void duplicate(String id) {
        this.projetDAO.duplicate(id);
    }
    public void cloturer (String id) {
        this.projetDAO.cloturer(id);
    }

    }
