package presentation.View;

import metier.pojo.Document;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class DocumentTableModel extends AbstractTableModel {
    private List<Document> documents;
    private String[] columns = {"Id", "Nom", "Date d'ajout", "Fichier"};

    public DocumentTableModel() {
        this.documents = new ArrayList<>();
        fireTableDataChanged();
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return documents.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Document document = documents.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return document.getIdDocument();
            case 1:
                return document.getNom();
            case 2:
                return document.getDate_ajout();
            case 3:
                return document.getUrl();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public Document getDocumentAt(int rowIndex) {
        return documents.get(rowIndex);
    }
}
