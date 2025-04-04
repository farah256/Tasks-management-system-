package metier.pojo;

import java.util.Date;


public class Tache {


    private String IdTache;
    private  String description;
    private  Date Date_debut;
    private  Date Date_fin;
    private  Categorie Categorie;
    private Etat Etat;
    private String idList;
    private String IdProjet;
    private String IdSeance;



    public Tache() {
    }

    public Tache(String description, Date date_debut, Date date_fin, metier.pojo.Categorie categorie) {
        this.description = description;
        Date_debut = date_debut;
        Date_fin = date_fin;
        Categorie = categorie;
    }

    public Tache(String description, Date date_debut, Date date_fin, Categorie categorie, Etat etat) {

        this.description = description;
        Date_debut = date_debut;
        Date_fin = date_fin;
        Categorie = categorie;
        Etat=etat;
    }

    public Etat getEtat() {
        return Etat;
    }

    public void setEtat(Etat etat) {
        Etat = etat;
    }

    public String getIdTache() {
        return IdTache;
    }

    public void setIdTache(String idTache) {
        IdTache =String.valueOf(idTache) ;
    }

    public  String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  Date getDate_debut() {
        return Date_debut;
    }

    public void setDate_debut(Date date_debut) {
        Date_debut = date_debut;
    }

    public  Date getDate_fin() {
        return Date_fin;
    }

    public void setDate_fin(Date date_fin) {
        Date_fin = date_fin;
    }

    public   Categorie getCategorie() {
        return Categorie;
    }

    public void setCategorie(Categorie categorie) {
        Categorie = categorie;
    }

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public String getIdProjet() {
        return IdProjet;
    }

    public void setIdProjet(String idProjet) {
        IdProjet = idProjet;
    }

    public String getIdSeance() {
        return IdSeance;
    }

    public void setIdSeance(String idSeance) {
        IdSeance = idSeance;
    }

    @Override
    public String toString() {
        return "Tache{" +
                "IdTache='" + IdTache + '\'' +
                ", description='" + description + '\'' +
                ", Date_debut=" + Date_debut +
                ", Date_fin=" + Date_fin +
                ", Categorie=" + Categorie +
                ", Etat=" + Etat +
                ", idList='" + idList + '\'' +
                ", IdProjet='" + IdProjet + '\'' +
                ", IdSeance='" + IdSeance + '\'' +
                '}';
    }
}
