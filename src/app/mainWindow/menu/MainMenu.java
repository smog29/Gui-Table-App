package app.mainWindow.menu;

import app.mainWindow.MainWindow;
import app.mainWindow.edit.TableEditInfo;
import graphics.IconLoader;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainMenu extends JMenuBar {

    private JMenu fileMenu, editMenu, helpMenu;

    //file menu items
    private JMenuItem exitItem, saveItem, saveAsItem, loadItem;

    //edit menu items
    private JMenuItem undoItem, redoItem;

    //help menu items
    private JMenuItem authorItem, helpItem;

    private final MainWindow mainWindow;

    public MainMenu(MainWindow mainWindow){
        this.mainWindow = mainWindow;

        createMenu();

        //creates menu Items and adds them to a proper menu
        createFIleMenuItems();
        createEditMenuItems();
        createHelpMenuItems();

        //adds menus to the menu bar
        this.add(fileMenu);
        this.add(editMenu);
        this.add(helpMenu);

        this.setVisible(true);
    }

    private void createMenu(){
        fileMenu = new JMenu("Plik");
        editMenu = new JMenu("Edycja");
        helpMenu = new JMenu("Pomoc");
    }

    private void createFIleMenuItems(){
        exitItem = createMenuItem(
                "Wyjdz",
                "/close.jpg",
                e -> mainWindow.disposeAndExit(),
                KeyEvent.VK_E
        );

        saveItem = createMenuItem(
                "Zapisz",
                "/save.png",
                e -> {
                    if(mainWindow.getMainPanel().getFileManager().save(mainWindow.getMainPanel().topPanel.getTable().jtable)){
                        JOptionPane.showMessageDialog(mainWindow.getFrame(), "Zapis udany");
                        mainWindow.getMainPanel().bottomPanel.setInfoLabel("Tabela zapisana do " + mainWindow.getMainPanel().getFileManager().getCurrentFile().getName());
                    }
                    else{
                        JOptionPane.showMessageDialog(mainWindow.getFrame(), "Zapis nieudany");
                    }
                },
                null
        );

        saveAsItem = createMenuItem(
                "Zapisz Jako",
                "/save.png",
                e -> {
                    if(mainWindow.getMainPanel().getFileManager().saveAs(mainWindow.getMainPanel().topPanel.getTable().jtable)){
                        JOptionPane.showMessageDialog(mainWindow.getFrame(), "Zapis udany");
                        mainWindow.getMainPanel().bottomPanel.setInfoLabel("Tabela zapisana do " + mainWindow.getMainPanel().getFileManager().getCurrentFile().getName());
                    }
                    else{
                        JOptionPane.showMessageDialog(mainWindow.getFrame(), "Zapis nieudany");
                    }
                },
                null
        );

        loadItem = createMenuItem(
                "Wczytaj",
                "/download.png",
                e -> {
                    if(mainWindow.getMainPanel().getFileManager().load(mainWindow.getMainPanel().topPanel.getTable().jtable)){
                        JOptionPane.showMessageDialog(mainWindow.getFrame(), "Odczyt udany");
                        mainWindow.getMainPanel().bottomPanel.setInfoLabel("Tabela odczytana z " + mainWindow.getMainPanel().getFileManager().getLoadedFile().getName());
                    }
                    else {
                        JOptionPane.showMessageDialog(mainWindow.getFrame(), "Odczyt nieudany");
                    }
                },
                null
        );

        fileMenu.add(exitItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(loadItem);
    }

    private void createEditMenuItems(){
        undoItem = createMenuItem(
                "Cofnij",
                "/back-arrow.png",
                e -> {
                    TableEditInfo[] edit = mainWindow.getMainPanel().tableEditManager.undo();

                    if(edit != null){
                        for(TableEditInfo editInfo : edit){
                            mainWindow.getMainPanel().topPanel.getTable().jtable.setValueAt(editInfo.getPreviousData(), editInfo.getRow(), editInfo.getColumn());
                        }
                    }
                },
                null
        );

        redoItem = createMenuItem(
                "Przywroc",
                "/redo-arrow.png",
                e -> {
                    TableEditInfo[] edit = mainWindow.getMainPanel().tableEditManager.redo();

                    if(edit != null){
                        for(TableEditInfo editInfo : edit){
                            mainWindow.getMainPanel().topPanel.getTable().jtable.setValueAt(editInfo.getCurrentData(), editInfo.getRow(), editInfo.getColumn());
                        }
                    }
                },
                null
        );

        editMenu.add(undoItem);
        editMenu.add(redoItem);
    }

    private void createHelpMenuItems(){
        authorItem = createMenuItem(
                "Autor",
                "/author.png",
                e -> mainWindow.showAuthor(),
                null
        );

        helpItem = createMenuItem(
                "Pomoc",
                "/help.png",
                e -> mainWindow.showHelp(),
                null
        );

        helpMenu.add(authorItem);
        helpMenu.add(helpItem);
    }

    private JMenuItem createMenuItem(String name, String imagePath, ActionListener listener, Integer mnemonic){
        JMenuItem item = new JMenuItem(name);

        if(imagePath != null){
            ImageIcon icon = (ImageIcon) IconLoader.loadIcon(imagePath);
            item.setIcon(icon);
        }

        item.addActionListener(listener);

        if(mnemonic != null){
            item.setMnemonic(mnemonic);
        }

        return item;
    }



}
