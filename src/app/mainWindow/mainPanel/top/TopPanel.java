package app.mainWindow.mainPanel.top;

import app.mainWindow.MainWindow;
import app.mainWindow.edit.TableEditInfo;
import app.mainWindow.mainPanel.MainPanel;
import app.mainWindow.mainPanel.table.Table;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TopPanel extends JPanel {

    private final MainPanel mainPanel;

    private final MainWindow mainWindow;

    //Center
    private Table table;
    //North
    private JTextField numberField;
    private JSlider rowSlider, columnSlider;
    private int chosenRow = 0, chosenColumn = 0;
    //East
    private JButton addBtn, zeroingBtn, saveBtn, loadBtn;


    public TopPanel(MainPanel mainPanel, MainWindow mainWindow) {
        this.mainPanel = mainPanel;
        this.mainWindow = mainWindow;

        this.setLayout(new BorderLayout());

        setPanels();

        //mouse listener to track selected table cells
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.jtable.getSelectedRow() + 1;
                int column = table.jtable.getSelectedColumn() + 1;

                rowSlider.setValue(row);
                columnSlider.setValue(column);
            }
        };

        table.jtable.addMouseListener(mouseAdapter);
    }

    private void setPanels() {
        table = new Table();
        table.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(table, BorderLayout.CENTER);

        JPanel north = createNorthPanel();

        this.add(north, BorderLayout.NORTH);

        JPanel east = createEastPanel();

        this.add(east, BorderLayout.EAST);
    }

    private JPanel createNorthPanel() {
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        northPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        //------
        northPanel.add(new JLabel("Wprowadz liczbe"));
        numberField = new JTextField();
        numberField.setPreferredSize(new Dimension(100, 25));
        numberField.setText("" + 0);
        numberField.setHorizontalAlignment(JTextField.RIGHT);
        numberField.setFont(new Font("arial", Font.BOLD, 15));
        northPanel.add(numberField);

        //------
        JLabel label = new JLabel("Wybrany rzad");
        label.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 15));

        rowSlider = new JSlider(1, table.jtable.getRowCount(), 1);
        rowSlider.setPaintTicks(true);
        rowSlider.setMajorTickSpacing(1);
        rowSlider.setPaintTrack(false);
        rowSlider.setPaintLabels(true);
        rowSlider.addChangeListener(e -> {
            chosenRow = rowSlider.getValue() - 1;
            table.jtable.changeSelection(chosenRow, chosenColumn, false, false);
        });

        northPanel.add(label);
        northPanel.add(rowSlider);

        //-----
        JLabel label2 = new JLabel("Wybrana kolumna");
        label2.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 15));

        columnSlider = new JSlider(1, table.jtable.getColumnCount(), 1);
        columnSlider.setPaintTicks(true);
        columnSlider.setMajorTickSpacing(1);
        columnSlider.setPaintTrack(false);
        columnSlider.setPaintLabels(true);
        columnSlider.addChangeListener(e -> {
            chosenColumn = columnSlider.getValue() - 1;
            table.jtable.changeSelection(chosenRow, chosenColumn, false, false);
        });


        northPanel.add(label2);
        northPanel.add(columnSlider);

        northPanel.setBackground(new Color(224, 224, 224));

        return northPanel;
    }


    private JPanel createEastPanel() {
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.PAGE_AXIS));
        eastPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 40));

        addBtn = Utils.createJButton(
                "Dodaj",
                e -> {
                    try {
                        double doubleValue = Double.parseDouble(numberField.getText());

                        String value = numberField.getText();

                        int[] selectedRows = table.jtable.getSelectedRows();
                        int[] selectedColumns = table.jtable.getSelectedColumns();

                        TableEditInfo[] editInfo = new TableEditInfo[selectedRows.length * selectedColumns.length];


                        int n = 0;
                        for (int selectedRow : selectedRows) {
                            for (int selectedColumn : selectedColumns) {
                                editInfo[n] = new TableEditInfo(table.jtable.getValueAt(selectedRow, selectedColumn),
                                        value, selectedRow, selectedColumn);

                                table.jtable.setValueAt(value, selectedRow, selectedColumn);

                                n++;
                            }
                        }

                        mainPanel.tableEditManager.push(editInfo);
                    } catch (Exception exception) {
                        exception.printStackTrace();

                        JOptionPane.showMessageDialog(
                                this,
                                "Podaj poprawna liczbe.",
                                "Zly format liczby",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                },
                false,
                null,
                "/table-add.png"
        );

        zeroingBtn = Utils.createJButton(
                "Wyzeruj",
                e -> {
                    TableEditInfo[] editInfo = new TableEditInfo[table.jtable.getRowCount() * table.jtable.getColumnCount()];

                    int n = 0;
                    for (int i = 0; i < table.jtable.getRowCount(); i++) {
                        for (int j = 0; j < table.jtable.getColumnCount(); j++) {
                            editInfo[n] = new TableEditInfo(table.jtable.getValueAt(i, j), 0, i, j);
                            n++;

                            table.jtable.setValueAt(0, i, j);
                        }
                    }
                    mainPanel.tableEditManager.push(editInfo);
                },
                false,
                null,
                "/zero.png"
        );

        saveBtn = Utils.createJButton(
                "Zapisz",
                e -> {
                    if (mainPanel.getFileManager().save(table.jtable)) {
                        JOptionPane.showMessageDialog(mainWindow.getFrame(), "Zapis udany");
                        mainPanel.bottomPanel.setInfoLabel("Tabela zapisana do " + mainPanel.getFileManager().getCurrentFile().getName());
                    } else {
                        JOptionPane.showMessageDialog(mainWindow.getFrame(), "Zapis nieudany");
                    }
                },
                false,
                null,
                "/save.png"
        );

        loadBtn = Utils.createJButton(
                "Wczytaj",
                e -> {
                    if (mainWindow.getMainPanel().getFileManager().load(mainWindow.getMainPanel().topPanel.getTable().jtable)) {
                        JOptionPane.showMessageDialog(mainWindow.getFrame(), "Odczyt udany");
                        mainPanel.bottomPanel.setInfoLabel("Tabela wczytana z " + mainPanel.getFileManager().getLoadedFile().getName());
                    } else {
                        JOptionPane.showMessageDialog(mainWindow.getFrame(), "Odczyt nieudany");
                    }
                },
                false,
                null,
                "/download.png"
        );


        Dimension buttonsSize = new Dimension(120, 30);

        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addBtn.setPreferredSize(buttonsSize);
        addBtn.setMaximumSize(buttonsSize);

        zeroingBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        zeroingBtn.setPreferredSize(buttonsSize);
        zeroingBtn.setMaximumSize(buttonsSize);

        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveBtn.setPreferredSize(buttonsSize);
        saveBtn.setMaximumSize(buttonsSize);

        loadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadBtn.setPreferredSize(buttonsSize);
        loadBtn.setMaximumSize(buttonsSize);

        eastPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        eastPanel.add(addBtn);
        eastPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        eastPanel.add(zeroingBtn);
        eastPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        eastPanel.add(saveBtn);
        eastPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        eastPanel.add(loadBtn);

        eastPanel.setBackground(new Color(255, 229, 204));

        return eastPanel;
    }


    public Table getTable() {
        return table;
    }


}
