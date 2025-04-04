package metier.Gestion;

import metier.pojo.Categorie;
import metier.pojo.Projet;
import metier.pojo.Type;
import persistence.HistoriqueProjetDAO;
import persistence.ProjetDAO;

import java.util.List;

public class HistoriqueGestion {
    private HistoriqueProjetDAO historiqueProjetDAO;
    public HistoriqueGestion(HistoriqueProjetDAO historiqueProjetDAO) {
        this.historiqueProjetDAO = historiqueProjetDAO;
    }
    public List<Projet> filtrerProjetsParMotCle(String motCle) {
        return this.historiqueProjetDAO.filtrerProjetsParMotCle(motCle);

    }
    public List<Projet> filtrerProjetsParTypeEtCategorie(Type type, Categorie categorie) {
        return this.historiqueProjetDAO.filtrerProjetsParTypeEtCategorie(type,categorie);
    }

    public List getAllProject() {
        return this.historiqueProjetDAO.getAllProject();
    }
}
