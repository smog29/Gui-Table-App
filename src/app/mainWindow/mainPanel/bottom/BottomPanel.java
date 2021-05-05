package app.mainWindow.mainPanel.bottom;

import app.mainWindow.mainPanel.MainPanel;
import graphics.IconLoader;
import utils.Utils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BottomPanel extends JPanel {

    MainPanel mainPanel;

    //Center
    private JTextArea textArea;
    //East
    private JList<String> list;
    private JButton operationBtn;
    //South
    private JLabel infoLabel, statusLabel;


    public BottomPanel(MainPanel mainPanel){
        this.mainPanel = mainPanel;

        setPanels();
    }

    private void setPanels(){
        this.setLayout(new BorderLayout());


        JPanel centre = createCentrePanel();
        this.add(centre, BorderLayout.CENTER);


        JPanel east = createEastPanel();
        this.add(east, BorderLayout.EAST);


        JPanel south = createSouthPanel();
        this.add(south, BorderLayout.SOUTH);
    }




    private JPanel createCentrePanel(){
        JPanel panel = new JPanel(new BorderLayout());
        TitledBorder title = BorderFactory.createTitledBorder("Uzyskany rezultat");
        title.setTitleJustification(TitledBorder.CENTER);
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setBorder(title);
        textArea.setFont(new Font("TimesRoman", Font.ITALIC, 20));
        textArea.setEditable(false);

        panel.setBorder(BorderFactory.createEmptyBorder(0,10,10,0));
        panel.add(textArea,BorderLayout.CENTER);
        panel.setBackground(new Color(255,229,204));

        return panel;
    }

    private JPanel createEastPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));


        //List
        Map<String, Icon> map = new HashMap<>();
        map.put("Suma elementow", IconLoader.loadIcon("/sum.png"));
        map.put("Srednia elementow", IconLoader.loadIcon("/average.png"));
        map.put("Wartosc max i min", IconLoader.loadIcon("/maxmin.png"));

        String[] options = {"Suma elementow", "Srednia elementow", "Wartosc max i min"};

        list = new JList<String>(options);

        list.setCellRenderer(new DefaultListCellRenderer(){

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);

                Icon icon = map.get((String) value);

                label.setIcon(icon);
                return label;
            }
        });

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(options.length);

        list.setAlignmentX(Component.CENTER_ALIGNMENT);
        LineBorder border = (LineBorder) BorderFactory.createLineBorder(Color.GRAY, 2);

        list.setBorder(border);
        list.setBackground(new Color(192,192,192));


        //Button
        operationBtn = Utils.createJButton(
                "Wybierz operacje",
                e -> {
                    String chosenOperation = list.getSelectedValue();
                    if(chosenOperation == null){
                        return;
                    }

                    switch (chosenOperation) {
                        case "Suma elementow" -> {
                            double sum = mainPanel.topPanel.getTable().getTableElementsSum();
                            showDataInTextArea("Suma: " + sum);
                        }
                        case "Srednia elementow" -> {
                            double average = mainPanel.topPanel.getTable().getTableElementsAverage();
                            showDataInTextArea("Srednia: " + average);
                        }
                        case "Wartosc max i min" -> {
                            double min = mainPanel.topPanel.getTable().getTableMinElement();
                            double max = mainPanel.topPanel.getTable().getTableMaxElement();
                            showDataInTextArea("Min = " + min + " Max = " + max);
                        }
                    }
                },
                false,
                null,
                "/operation.png"
        );

        operationBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(operationBtn);
        panel.add(Box.createRigidArea(new Dimension(0,15)));
        panel.add(list);

        panel.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));
        panel.setBackground(new Color(255,229,204));

        return panel;
    }

    private JPanel createSouthPanel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(new Color(255,229,204));


        Font font = new Font("TimesRoman", Font.ITALIC, 15);


        JLabel infoText = new JLabel("Info");
        infoText.setBorder(BorderFactory.createEmptyBorder(0,30,0,10));
        infoText.setFont(new Font("TimesRoman", Font.BOLD, 15));
        panel.add(infoText);

        infoLabel = new JLabel("Status aplikacji");
        infoLabel.setOpaque(true);
        infoLabel.setBackground(Color.LIGHT_GRAY);
        infoLabel.setFont(font);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(0,5,0,10));
        panel.add(infoLabel);

        JLabel statusText = new JLabel("Status");
        statusText.setBorder(BorderFactory.createEmptyBorder(0,40,0,10));
        statusText.setFont(new Font("TimesRoman", Font.BOLD, 15));
        panel.add(statusText);

        statusLabel = new JLabel("ON");
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Color.LIGHT_GRAY);
        statusLabel.setFont(font);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(0,5,0,10));
        panel.add(statusLabel);


        return panel;
    }


    public void setInfoLabel(String info){
        infoLabel.setText(info);
    }

    public void showDataInTextArea(String data){
        textArea.setText(data);
    }
}
