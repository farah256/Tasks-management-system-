package metier.Gestion;

import metier.pojo.Document;
import persistence.DocumentDAO;

import java.util.List;


public class DocumentGestion {
    private DocumentDAO documentDAO;
    public DocumentGestion(DocumentDAO documentDAO) {
        this.documentDAO = documentDAO;
    }

    public boolean create(Document obj) {
        return this.documentDAO.create(obj);
    }


    public Document read(String id) {
        return this.documentDAO.read(id);
    }


    public void update(Document obj, String id) {
        this.documentDAO.update(obj,id);
    }


    public void delete(String id) {
        this.documentDAO.delete(id);
    }


    public List getAll() {
        return this.documentDAO.getAll();
    }
    public List<Document> filtrerdocumentParMotCle(String motCle) {return this.documentDAO.filtrerdocumentParMotCle(motCle);}

    public List<Document> getDocumentsByProjectId(String projectId) {return this.documentDAO.getDocumentsByProjectId(projectId);}
    public List<Document> getDocumentsByTaskId(String taskId) {return this.documentDAO.getDocumentsByTaskId(taskId);}
    public List<Document> getDocumentsBySeanceId(String seanceId){return this.documentDAO.getDocumentsBySeanceId(seanceId);
    }
    }