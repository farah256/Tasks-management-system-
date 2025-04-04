package presentation.View;

import metier.pojo.ListeTache;
import metier.pojo.Projet;
import metier.pojo.Tache;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ListTableModel extends AbstractTableModel {
    private List<ListeTache> taches;
    private String[] columns = {"Id","Description"};

    public ListTableModel() {
        this.taches = new ArrayList<>();
        fireTableDataChanged();
    }

    public void setTaches(List<ListeTache> taches) {
        this.taches = taches;
        fireTableDataChanged();
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
        ListeTache tache = taches.get(rowIndex);
        switch (columnIndex) {

            case 0:
                return tache.getIdList();

            case 1:
                return tache.getDescription();


            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
