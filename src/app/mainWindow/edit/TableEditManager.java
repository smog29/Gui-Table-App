package app.mainWindow.edit;
import java.util.Stack;

public class TableEditManager {

    private static final int MAX_SIZE = 50;

    private Stack<TableEditInfo[]> undoStack = new Stack<>();

    private Stack<TableEditInfo[]> redoStack = new Stack<>();

    public TableEditManager(){ }


    public void push(TableEditInfo editInfo){
        TableEditInfo[] arr = new TableEditInfo[] {editInfo};

        undoStack.push(arr);

        redoStack.clear();

        if(undoStack.size() == MAX_SIZE) {
            undoStack = (Stack<TableEditInfo[]>) undoStack.subList(9, MAX_SIZE);
        }
    }

    public void push(TableEditInfo[] editInfo){
        undoStack.push(editInfo);

        redoStack.clear();

        if(undoStack.size() == MAX_SIZE) {
            undoStack = (Stack<TableEditInfo[]>) undoStack.subList(9, MAX_SIZE);
        }
    }

    public TableEditInfo[] undo(){
        if(undoStack.empty()){
            return null;
        }

        TableEditInfo[] info = undoStack.pop();

        redoStack.push(info);

        return info;
    }

    public TableEditInfo[] redo(){
        if(redoStack.empty()){
            return null;
        }

        TableEditInfo[] info = redoStack.pop();

        undoStack.push(info);

        if(undoStack.size() == MAX_SIZE) {
            undoStack = (Stack<TableEditInfo[]>) undoStack.subList(9, MAX_SIZE);
        }

        return info;
    }

}
