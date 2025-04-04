package presentation.View;

import metier.pojo.Projet;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProjetTableModel extends AbstractTableModel {
    private List<Projet> projets;
    private String[] columns = {"Id","Description", "Categorie", "Type", "Date début", "Date fin"};

    public ProjetTableModel() {
        this.projets = new ArrayList<>();
        fireTableDataChanged();
    }

    public void setProjets(List<Projet> projets) {
        this.projets = projets;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return projets.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Projet projet = projets.get(rowIndex);
        switch (columnIndex) {

            case 0:
                return projet.getIdProjet();

            case 1:
                return projet.getDescription();
            case 2:
                return projet.getCategorie();
            case 3:
                return projet.getType();
            case 4:
                return projet.getDate_debut();
            case 5:
                return projet.getDate_fin();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
