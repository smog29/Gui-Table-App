package app.mainWindow.listModel;

import javax.swing.*;
import java.util.Vector;

public class ListModel<E> extends AbstractListModel<E> {

    private Vector<E> v = new Vector<>();

    public ListModel(Object[] objects){
        for(Object obj : objects){
            v.addElement((E) obj);
        }
    }


    @Override
    public int getSize() {
        return v.size();
    }

    @Override
    public E getElementAt(int index) {
        return v.get(index);
    }
}
