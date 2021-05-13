package app.mainWindow.navigationBar;

import app.mainWindow.MainWindow;
import app.mainWindow.edit.TableEditInfo;
import app.mainWindow.listModel.ListModel;
import app.mainWindow.mainPanel.MainPanel;
import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;
import com.l2fprod.common.swing.plaf.TaskPaneGroupUI;
import graphics.IconLoader;

import javax.swing.*;
import javax.swing.plaf.PanelUI;
import javax.swing.plaf.basic.BasicMenuBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class NavigationBar extends JPanel {

    private JTaskPane taskPane;

    private JTaskPaneGroup operationsBar;
    private JList<String> operationsList;

    private JTaskPaneGroup aboutBar;
    private JList<String> aboutList;

    private JTaskPaneGroup editBar;
    private JList<String> editList;

    private final MainWindow mainWindow;

    private final MainPanel mainPanel;


    public NavigationBar(MainWindow mainWindow){
        this.mainWindow = mainWindow;

        mainPanel = mainWindow.getMainPanel();

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        taskPane = new JTaskPane();

        taskPane.setBackground(new Color(255,229,204));
        taskPane.setBorder(BorderFactory.createLineBorder(Color.GRAY,3));

        setOperationsBar();
        taskPane.add(operationsBar);

        setAboutBar();
        taskPane.add(aboutBar);

        setEditBar();
        taskPane.add(editBar);

        this.add(new JScrollPane(taskPane));
    }


    private void setOperationsBar(){
        operationsBar = new JTaskPaneGroup();
        operationsBar.setFocusable(false);
        operationsBar.setIcon(IconLoader.loadIcon("/operation.png"));

        Map<String, Icon> map = new HashMap<>();
        map.put("Suma", IconLoader.loadIcon("/sum2.png"));
        map.put("Srednia", IconLoader.loadIcon("/average.png"));
        map.put("Min Max", IconLoader.loadIcon("/maxmin.png"));

        String[] options = {"Suma", "Srednia", "Min Max"};

        ListModel<String> listModel = new ListModel<>(options);

        operationsList = new JList<>(listModel);
        operationsList.setBackground(operationsBar.getBackground());

        operationsList.setCellRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                label.setIcon(map.get((String) value));

                return label;
            }
        });

        operationsList.addListSelectionListener(e -> {
            String option = operationsList.getSelectedValue();

            if(option == null){
                return;
            }

            switch(option){
                case "Suma" -> {
                    double sum = mainPanel.topPanel.getTable().model.getTableElementsSum();
                    mainWindow.getMainPanel().bottomPanel.showDataInTextArea("Suma: " + sum);
                }
                case "Srednia" -> {
                    double average = mainPanel.topPanel.getTable().model.getTableElementsAverage();
                    mainWindow.getMainPanel().bottomPanel.showDataInTextArea("Srednia: " + average);
                }
                case "Min Max" -> {
                    double min = mainPanel.topPanel.getTable().model.getTableMinElement();
                    double max = mainPanel.topPanel.getTable().model.getTableMaxElement();
                    mainWindow.getMainPanel().bottomPanel.showDataInTextArea("Min: " + min + " Max: " + max);
                }
            }

            operationsList.clearSelection();
        });

        operationsBar.add(operationsList);
    }

    private void setAboutBar(){
        aboutBar = new JTaskPaneGroup();
        aboutBar.setFocusable(false);
        aboutBar.setIcon(IconLoader.loadIcon("/help.png"));

        Map<String, Icon> map = new HashMap<>();

        map.put("Autor", IconLoader.loadIcon("/author.png"));
        map.put("Pomoc", IconLoader.loadIcon("/help.png"));

        String[] options = {"Autor", "Pomoc"};

        ListModel<String> aboutListMode = new ListModel<>(options);

        aboutList = new JList<>(aboutListMode);

        aboutList.setBackground(aboutBar.getBackground());

        aboutList.setCellRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                label.setIcon(map.get((String) value));

                return label;
            }
        });

        aboutList.addListSelectionListener(e -> {
            String option = aboutList.getSelectedValue();

            if(option == null){
                return;
            }

            switch (option){
                case "Autor" -> mainWindow.showAuthor();

                case "Pomoc" -> mainWindow.showHelp();
            }

            aboutList.clearSelection();
        });

        aboutBar.add(aboutList);
    }

    private void setEditBar(){
        editBar = new JTaskPaneGroup();
        editBar.setFocusable(false);
        editBar.setIcon(IconLoader.loadIcon("/edit.png"));

        Map<String, Icon> map = new HashMap<>();

        map.put("Cofnij", IconLoader.loadIcon("/back-arrow.png"));
        map.put("Przywroc", IconLoader.loadIcon("/redo-arrow.png"));
        map.put("Policz", IconLoader.loadIcon("/counting.png"));

        String[] options = {"Cofnij", "Przywroc", "Policz"};

        ListModel<String> listModel = new ListModel<>(options);

        editList = new JList<>(listModel);

        editList.setBackground(editBar.getBackground());

        editList.setCellRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                label.setIcon(map.get((String) value));

                return label;
            }
        });

        editList.addListSelectionListener(e -> {
            String option = editList.getSelectedValue();

            if(option == null){
                return;
            }

            switch (option){
                case "Cofnij" -> {
                    TableEditInfo[] edit = mainPanel.tableEditManager.undo();

                    if(edit != null){
                        for(TableEditInfo editInfo : edit){
                            mainPanel.topPanel.getTable().jtable.setValueAt(editInfo.getPreviousData(), editInfo.getRow(), editInfo.getColumn());
                        }
                    }
                }

                case "Przywroc" -> {
                    TableEditInfo[] edit = mainPanel.tableEditManager.redo();

                    if(edit != null){
                        for(TableEditInfo editInfo : edit){
                            mainPanel.topPanel.getTable().jtable.setValueAt(editInfo.getCurrentData(), editInfo.getRow(), editInfo.getColumn());
                        }
                    }
                }

                case "Policz" -> {
                    StringBuilder output = new StringBuilder();
                    Map<Object, Integer> occurrencesMap = mainPanel.topPanel.getTable().model.getOccurrences();

                    for(Map.Entry<Object, Integer> entry : occurrencesMap.entrySet()){
                        output.append(String.format("%s wystepuje %d %s", entry.getKey(), entry.getValue(), entry.getValue().equals(1) ? " raz.   " : " razy.   "));
                    }

                    mainPanel.bottomPanel.showDataInTextArea(output.toString());
                }
            }

            editList.clearSelection();
        });

        editBar.add(editList);
    }
}
