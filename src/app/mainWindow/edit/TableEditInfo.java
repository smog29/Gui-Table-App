package app.mainWindow.edit;

public class TableEditInfo {

    private Object previousData;
    private Object currentData;
    private int row, column;

    public TableEditInfo(Object previousData, Object currentData, int row, int column){
        this.previousData = previousData;
        this.currentData = currentData;
        this.row = row;
        this.column = column;
    }


    public Object getPreviousData() {
        return previousData;
    }

    public Object getCurrentData(){ return currentData; }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
