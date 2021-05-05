package app.mainWindow.toolBar;

import app.mainWindow.MainWindow;
import app.mainWindow.edit.TableEditInfo;
import app.mainWindow.mainPanel.MainPanel;
import utils.Utils;
import javax.swing.*;
import java.awt.*;


public class MainToolBar extends JPanel {

    private JToolBar toolBar;

    //buttons for toolBar
    private JButton exitBtn, authorBtn, helpBtn, undoBtn, redoBtn, sumBtn, averageBtn, min_maxBtn;


    private final MainWindow mainWindow;

    private final MainPanel mainPanel;

    public MainToolBar(MainWindow window){
        this.mainWindow = window;
        mainPanel = window.getMainPanel();

        this.setBackground(Color.LIGHT_GRAY);

        this.setLayout(new FlowLayout(FlowLayout.LEFT));


        //creating toolbar
        toolBar = new JToolBar();
        createToolBarButtons();
        toolBar.setFloatable(false);
        toolBar.setBorder(BorderFactory.createEmptyBorder());
        toolBar.setBackground(Color.LIGHT_GRAY);

        this.add(toolBar);
    }

    private void createToolBarButtons(){
        exitBtn = Utils.createJButton(
                null,
                    e -> mainWindow.disposeAndExit(),
                false,
                null,
                "/close.jpg"
                );

        authorBtn = Utils.createJButton(
                null,
                e -> mainWindow.showAuthor(),
                false,
                null,
                "/author.png"
        );

        helpBtn = Utils.createJButton(
                null,
                e -> mainWindow.showHelp(),
                false,
                null,
                "/help.png"
        );

        undoBtn = Utils.createJButton(
                null,
                e -> {
                    TableEditInfo[] edit = mainPanel.tableEditManager.undo();

                    if(edit != null){
                        for(TableEditInfo editInfo : edit){
                            mainPanel.topPanel.getTable().jtable.setValueAt(editInfo.getPreviousData(), editInfo.getRow(), editInfo.getColumn());
                        }
                    }
                },
                false,
                null,
                "/back-arrow.png"
        );

        redoBtn = Utils.createJButton(
                null,
                e -> {
                    TableEditInfo[] edit = mainPanel.tableEditManager.redo();

                    if(edit != null){
                        for(TableEditInfo editInfo : edit){
                            mainPanel.topPanel.getTable().jtable.setValueAt(editInfo.getCurrentData(), editInfo.getRow(), editInfo.getColumn());
                        }
                    }
                },
                false,
                null,
                "/redo-arrow.png"
        );

        sumBtn = Utils.createJButton(
                null,
                e -> {
                    double sum = mainPanel.topPanel.getTable().getTableElementsSum();
                    mainPanel.bottomPanel.showDataInTextArea("Suma: " + sum);
                },
                false,
                null,
                "/sum2.png"
        );

        averageBtn = Utils.createJButton(
                null,
                e -> {
                    double average = mainPanel.topPanel.getTable().getTableElementsAverage();
                    mainPanel.bottomPanel.showDataInTextArea("Srednia: " + average);
                },
                false,
                null,
                "/average.png"
        );

        min_maxBtn = Utils.createJButton(
                null,
                e -> {
                    double min = mainPanel.topPanel.getTable().getTableMinElement();
                    double max = mainPanel.topPanel.getTable().getTableMaxElement();
                    mainPanel.bottomPanel.showDataInTextArea("Min = " + min + " Max = " + max);
                },
                false,
                null,
                "/maxmin.png"
        );
        exitBtn.setBackground(Color.LIGHT_GRAY);
        authorBtn.setBackground(Color.LIGHT_GRAY);
        helpBtn.setBackground(Color.LIGHT_GRAY);
        sumBtn.setBackground(Color.LIGHT_GRAY);
        averageBtn.setBackground(Color.LIGHT_GRAY);
        min_maxBtn.setBackground(Color.LIGHT_GRAY);
        undoBtn.setBackground(Color.LIGHT_GRAY);
        redoBtn.setBackground(Color.LIGHT_GRAY);



        toolBar.add(exitBtn);
        toolBar.add(authorBtn);
        toolBar.add(helpBtn);
        toolBar.addSeparator(new Dimension(50,0));
        toolBar.add(undoBtn);
        toolBar.add(redoBtn);
        toolBar.addSeparator(new Dimension(50,0));
        toolBar.add(sumBtn);
        toolBar.add(averageBtn);
        toolBar.add(min_maxBtn);
    }



}
