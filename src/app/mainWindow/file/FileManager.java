package app.mainWindow.file;

import app.mainWindow.MainWindow;
import app.mainWindow.edit.TableEditInfo;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;


public class FileManager {

    private final MainWindow mainWindow;

    private File currentFile = null;

    private File loadedFile;

    public FileManager(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    //zapisywanie jako, jesli zaden plik zapisu nie byl wybrany
    public boolean saveAs(JTable table) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "txt", "TXT");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int response = fileChooser.showSaveDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();

            if (!currentFile.getName().toLowerCase().endsWith(".txt")) {
                String filePath = currentFile.getAbsolutePath();
                currentFile = new File(filePath + ".txt");
            }

            return save(table);
        }

        return false;
    }

    //zapisywanie do wybranego wczesniej pliku
    public boolean save(JTable table) {
        if (currentFile == null) {
            return saveAs(table);
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(
                    new FileWriter(currentFile, false)
            );

            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    writer.write(table.getValueAt(i, j) + " ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }


    public boolean load(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "txt", "TXT");
        fileChooser.setFileFilter(filter);

        int response = fileChooser.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            loadedFile = fileChooser.getSelectedFile();

            if (!loadedFile.getName().toLowerCase().endsWith(".txt")) {
                JOptionPane.showMessageDialog(mainWindow.getFrame(), "Odczyt mozliwy tylko z pliku .txt!");
                return load(table);
            }

            return loadFromFile(table, loadedFile);
        }
        return false;
    }

    private boolean loadFromFile(JTable table, File file){

        if(!good(table, file)){
            JOptionPane.showMessageDialog(mainWindow.getFrame(), "Bledne dane w pliku do odczytu");
            return load(table);
        }

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));

            String line;

            int row = 0;

            TableEditInfo[] editInfo = new TableEditInfo[table.getRowCount() * table.getColumnCount()];
            int n = 0;

            while ((line = reader.readLine()) != null) {
                String[] elements = line.trim().split(" ");

                for (int column = 0; column < table.getColumnCount(); column++) {
                    double doubleValue = Double.parseDouble(elements[column]);

                    String value = elements[column];

                    editInfo[n] = new TableEditInfo(table.getValueAt(row, column), value, row, column);
                    table.setValueAt(value, row, column);

                    n++;
                }
                row++;
            }

            mainWindow.getMainPanel().tableEditManager.push(editInfo);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private boolean good(JTable table, File file){
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();

        BufferedReader reader = null;

        try{
            reader = new BufferedReader(new FileReader(file));

            String line;

            int row = 0;

            while((line = reader.readLine()) != null){
                String[] elements = line.trim().split(" ");

                if(elements.length != columnCount){
                    return false;
                }

                for(String element : elements){
                    double doubleValue = Double.parseDouble(element);
                }

                row++;

                if(row > rowCount){
                    return false;
                }
            }

            if(row != rowCount){
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return true;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public File getLoadedFile() {
        return loadedFile;
    }
}
