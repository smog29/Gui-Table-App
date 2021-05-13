package app.mainWindow.mainPanel.table;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.Map;

public class TableModel extends AbstractTableModel {

    private final String[] headers;

    public final int rowCount, columnCount;

    private final Object[] data;

    public TableModel(int rowCount, int columnCount, String[] headers){
        super();
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.headers = headers;

        data = new Object[rowCount * columnCount];
    }

    @Override
    public String getColumnName(int index) {
        return headers[index];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex * rowCount + columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data[rowIndex * rowCount + columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }


    public double getTableElementsSum(){
        double sum = 0;

        for(int i = 0 ; i < rowCount ; i++){
            for(int j = 0 ; j < columnCount ; j++){
                sum += (Double.parseDouble((String) getValueAt(i,j)));
            }
        }

        return sum;
    }

    public double getTableElementsAverage(){
        double numberOfElements = rowCount * columnCount;

        return  getTableElementsSum() /  numberOfElements;
    }

    public double getTableMinElement(){
        double min = (Double.parseDouble((String) getValueAt(0,0)));

        for(int i = 0 ; i < rowCount ; i++){
            for(int j = 0 ; j < columnCount ; j++){
                double value = (Double.parseDouble((String) getValueAt(i,j)));

                if(min > value){
                    min = value;
                }
            }
        }
        return min;
    }

    public double getTableMaxElement(){
        double max = (Double.parseDouble((String) getValueAt(0,0)));

        for(int i = 0 ; i < rowCount ; i++){
            for(int j = 0 ; j < columnCount ; j++){
                double value = (Double.parseDouble((String) getValueAt(i,j)));

                if(max < value){
                    max = value;
                }
            }
        }
        return max;
    }

    public Map<Object, Integer> getOccurrences(){
        Map<Object, Integer> map = new HashMap<>();

        for(Object obj : data){
            Integer value = map.get(obj);

            map.put(obj, value == null ? 1 : value + 1);
        }

        return map;
    }

    public double[] toDoubleArray(){
        double[] doubleData = new double[rowCount * columnCount];

        for(int i = 0 ; i < doubleData.length ; i++){
            doubleData[i] = Double.parseDouble((String) data[i]);
        }

        return doubleData;
    }

    public Object[] getData() {
        return data;
    }
}
