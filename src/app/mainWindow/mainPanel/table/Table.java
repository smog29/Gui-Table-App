package app.mainWindow.mainPanel.table;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class Table extends JPanel{

    public final JTable jtable;

    public Table(){
        this.setLayout(new BorderLayout());

        //creating model
        DefaultTableModel model = new DefaultTableModel() {
            final String[] headers = new String[]{"1", "2", "3", "4", "5"};

            @Override
            public String getColumnName(int index) {
                return headers[index];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.setColumnCount(5);
        model.setRowCount(5);

        //creating table
        jtable = new JTable(model);

        jtable.setGridColor(Color.BLACK);
        jtable.setBackground(new Color(255,229,204));

        jtable.setRowHeight(35);

        jtable.getTableHeader().setReorderingAllowed(false);
        jtable.getTableHeader().setBackground(new Color(204,255,204));

        jtable.setFont(new Font(jtable.getFont().getName(), Font.BOLD, 15));

        jtable.setSelectionBackground(Color.BLUE);

        jtable.setColumnSelectionAllowed(false);
        jtable.setRowSelectionAllowed(false);
        jtable.setCellSelectionEnabled(true);

        jtable.changeSelection(0,0,false,false);


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


    public double getTableElementsSum(){
        double sum = 0;

        for(int i = 0 ; i < jtable.getRowCount() ; i++){
            for(int j = 0 ; j < jtable.getColumnCount() ; j++){
                sum += (Double.parseDouble((String) jtable.getValueAt(i,j)));
            }
        }

        return sum;
    }

    public double getTableElementsAverage(){
        double numberOfElements = jtable.getColumnCount() * jtable.getColumnCount();

        return  getTableElementsSum() /  numberOfElements;
    }

    public double getTableMinElement(){
        double min = (Double.parseDouble((String) jtable.getValueAt(0,0)));

        for(int i = 0 ; i < jtable.getRowCount() ; i++){
            for(int j = 0 ; j < jtable.getColumnCount() ; j++){
                double value = (Double.parseDouble((String) jtable.getValueAt(i,j)));

                if(min > value){
                    min = value;
                }
            }
        }
        return min;
    }

    public double getTableMaxElement(){
        double max = (Double.parseDouble((String) jtable.getValueAt(0,0)));

        for(int i = 0 ; i < jtable.getRowCount() ; i++){
            for(int j = 0 ; j < jtable.getColumnCount() ; j++){
                double value = (Double.parseDouble((String) jtable.getValueAt(i,j)));

                if(max < value){
                    max = value;
                }
            }
        }
        return max;
    }

}
