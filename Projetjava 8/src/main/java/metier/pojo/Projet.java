package metier.pojo;

import java.util.Date;



public class Projet {

    private String IdProjet;
    private  String description;
    private  Date Date_debut;
    private  Date Date_fin;
    private  Categorie Categorie;
    private  Type Type;




    public Projet(String description ,Date date_debut, Date date_fin, Categorie categorie, Type type) {

        this.description = description;
        Date_debut = date_debut;
        Date_fin = date_fin;
        Categorie = categorie;
        Type = type;
    }

    public Projet() {

    }


    public String getIdProjet() {
        return IdProjet;
    }

    public void setIdProjet(String idProjet){IdProjet= idProjet;
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

    public   Date getDate_fin() {
        return Date_fin;
    }

    public void setDate_fin(Date date_fin) {
        Date_fin = date_fin;
    }

    public  Categorie getCategorie() {
        return Categorie;
    }

    public void setCategorie(Categorie categorie) {
        Categorie = categorie;
    }

    public  Type getType() {
        return Type;
    }

    public void setType(Type type) {
        Type = type;
    }
    public String toString() {
        return "Projet{" +
                "idProjet=" + IdProjet +
                ", description='" + description + '\'' +
                ", date_debut=" + Date_debut +
                ", date_fin=" + Date_fin +
                ", categorie='" + Categorie + '\'' +
                ", type='" + Type + '\'' +
                '}';
    }
}
