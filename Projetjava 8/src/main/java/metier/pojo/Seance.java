package metier.pojo;

import java.util.Date;

public class Seance {

    private  String IdSeance;
    private  String description;
    private  Date Date_debut;
    private  Date Date_fin;
    private  String Note;
    private String IdProjet;


    public Seance() {
    }

    public Seance( String description, Date date_debut, Date date_fin, String note) {

        this.description = description;
        Date_debut = date_debut;
        Date_fin = date_fin;
        Note = note;
    }

    public Seance(String description, Date date_debut, Date date_fin, String note, String idProjet) {
        this.description = description;
        Date_debut = date_debut;
        Date_fin = date_fin;
        Note = note;
        IdProjet = idProjet;
    }

    public  String getIdSeance() {
        return IdSeance;
    }

    public void setIdSeance(String idSeance) {
        IdSeance = String.valueOf(idSeance);
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

    public  String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getIdProjet() {
        return IdProjet;
    }

    public void setIdProjet(String idProjet) {
        IdProjet = idProjet;
    }
}
