package utils;

import graphics.IconLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Utils {

    public static JButton createJButton(String text, ActionListener listener, boolean focusable, Font font, String imagePath){
        JButton button = new JButton();

        if(text != null){
            button.setText(text);
        }

        button.addActionListener(listener);
        button.setFocusable(focusable);

        if(font != null){
            button.setFont(font);
        }

        if(imagePath != null){
            button.setIcon(IconLoader.loadIcon(imagePath));
        }

        return button;
    }

}
