package presentation.View;

import metier.pojo.Projet;
import metier.pojo.Tache;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TacheTableModel extends AbstractTableModel {
    private List<Tache> taches;
    private String[] columns = {"Id","Description", "Categorie", "Date début", "Date fin","etat"};

    public TacheTableModel() {
        this.taches = new ArrayList<>();
        fireTableDataChanged();
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
        fireTableDataChanged(); // Notify the table that the data has changed
    }

    @Override
    public int getRowCount() {
        return taches.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tache tache = taches.get(rowIndex);
        switch (columnIndex) {

            case 0:
                return tache.getIdTache();

            case 1:
                return tache.getDescription();
            case 2:
                return tache.getCategorie();
            case 3:
                return tache.getDate_debut();
            case 4:
                return tache.getDate_fin();
            case 5:
                return tache.getEtat();


            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
