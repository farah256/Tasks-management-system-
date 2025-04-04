package metier.pojo;

import org.bson.types.Binary;
import java.util.Date;

public class Document {

    private String IdTache;
    private String IdProjet;
    private String IdSeance;
    private String IdDocument;
    private String Nom;
    private Date Date_ajout;
    private String url;

    public Document() {
    }

    public Document(String nom, Date date_ajout, String url) {
        this.Nom = nom;
        this.Date_ajout = date_ajout;
        this.url = url;
    }

    public String getIdTache() {
        return IdTache;
    }

    public void setIdTache(String idTache) {
        this.IdTache = idTache;
    }

    public String getIdProjet() {
        return IdProjet;
    }

    public void setIdProjet(String idProjet) {
        this.IdProjet = idProjet;
    }

    public String getIdSeance() {
        return IdSeance;
    }

    public void setIdSeance(String idSeance) {
        this.IdSeance = idSeance;
    }

    public String getIdDocument() {
        return IdDocument;
    }

    public void setIdDocument(String idDocument) {
        this.IdDocument = idDocument;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        this.Nom = nom;
    }

    public Date getDate_ajout() {
        return Date_ajout;
    }

    public void setDate_ajout(Date date_ajout) {
        this.Date_ajout = date_ajout;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
