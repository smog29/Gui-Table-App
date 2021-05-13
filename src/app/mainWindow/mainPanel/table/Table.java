package app.mainWindow.mainPanel.table;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class Table extends JPanel{

    public final JTable jtable;

    public final TableModel model;

    public Table(){
        this.setLayout(new BorderLayout());

        final String[] headers = new String[]{"1", "2", "3", "4", "5"};

        model = new TableModel(5,5, headers);

        //creating table
        jtable = new JTable(model);

        jtable.setGridColor(Color.BLACK);
        jtable.setBackground(new Color(255,229,204));

        jtable.setRowHeight(40);

        jtable.getTableHeader().setReorderingAllowed(false);
        jtable.getTableHeader().setBackground(new Color(204,255,204));

        jtable.setFont(new Font(jtable.getFont().getName(), Font.BOLD, 15));

        jtable.setSelectionBackground(Color.BLUE);

        jtable.setColumnSelectionAllowed(false);
        jtable.setRowSelectionAllowed(false);
        jtable.setCellSelectionEnabled(true);

        //setting renderer for cells
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.lightGray : Color.WHITE);

                if(isSelected){
                    JLabel label = (JLabel) c;

                    label.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                    label.setBackground(new Color(154, 174, 213));
                }

                return c;
            }
        };
        renderer.setHorizontalAlignment(JLabel.RIGHT);

        jtable.setDefaultRenderer(Object.class, renderer);

        jtable.changeSelection(0,0,false,false);

        //adding table to the scroll pane
        JScrollPane tableScrollPane = new JScrollPane();
        tableScrollPane.setViewportView(jtable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        Dimension dimension = jtable.getPreferredSize();
        tableScrollPane.setPreferredSize(new Dimension(dimension.width, jtable.getRowHeight() * (model.getColumnCount() + 1)));

        jtable.setFillsViewportHeight(true);

        //zeroing table
        for(int i = 0; i < jtable.getRowCount() ; i++){
            for(int j = 0; j < jtable.getColumnCount() ; j++){
                jtable.setValueAt( "0", i, j);
            }
        }

        this.setBackground(new Color(255,229,204));
        this.add(tableScrollPane, BorderLayout.CENTER);
    }




}
