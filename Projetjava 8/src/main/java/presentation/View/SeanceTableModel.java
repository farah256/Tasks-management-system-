package presentation.View;

import metier.pojo.Seance;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class SeanceTableModel extends AbstractTableModel {
    private List<Seance> seances;
    private String[] columns = {"Id","Description", "Note", "Date début", "Date fin"};

    public SeanceTableModel() {
        this.seances = new ArrayList<>();
        fireTableDataChanged();
    }

    public  void setSeances(List<Seance> seances) {
        this.seances = seances;
        fireTableDataChanged(); // Notify the table that the data has changed
    }

    @Override
    public int getRowCount() {
        return seances.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Seance seance = seances.get(rowIndex);
        switch (columnIndex) {

            case 0:
                return seance.getIdSeance();

            case 1:
                return seance.getDescription();
            case 2:
                return seance.getNote();

            case 3:
                return seance.getDate_debut();
            case 4:
                return seance.getDate_fin();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
