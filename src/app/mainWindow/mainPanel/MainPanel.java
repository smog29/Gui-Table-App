package app.mainWindow.mainPanel;

import app.mainWindow.MainWindow;
import app.mainWindow.edit.TableEditManager;
import app.mainWindow.file.FileManager;
import app.mainWindow.mainPanel.bottom.BottomPanel;
import app.mainWindow.mainPanel.top.TopPanel;
import graphics.IconLoader;
import utils.Utils;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MainPanel extends JPanel {

    public final TableEditManager tableEditManager;

    public final TopPanel topPanel;

    public final BottomPanel bottomPanel;

    private FileManager fileManager;


    public MainPanel(MainWindow mainWindow) {
        this.setLayout(new GridLayout(2,1));

        tableEditManager = new TableEditManager();

        fileManager = new FileManager(mainWindow);


        topPanel = new TopPanel(this, mainWindow);
        this.add(topPanel);

        bottomPanel = new BottomPanel(this);
        this.add(bottomPanel);
    }


    public FileManager getFileManager() {
        return fileManager;
    }
}
