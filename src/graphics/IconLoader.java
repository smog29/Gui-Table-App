package graphics;

import javax.swing.*;

public class IconLoader {

    public static Icon loadIcon(String path){
        try{
            return new ImageIcon(IconLoader.class.getResource(path));
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
