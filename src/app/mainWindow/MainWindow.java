package app.mainWindow;

import app.mainWindow.mainPanel.MainPanel;
import app.mainWindow.menu.MainMenu;
import app.mainWindow.navigationBar.NavigationBar;
import app.mainWindow.toolBar.MainToolBar;
import com.l2fprod.common.swing.JTipOfTheDay;

import com.l2fprod.common.swing.tips.DefaultTip;
import com.l2fprod.common.swing.tips.DefaultTipModel;
import graph.Histogram;
import graphics.IconLoader;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class MainWindow {

    private final JFrame frame;

    private final JPanel mainContent;

    private MainToolBar toolBar;

    private MainPanel centrePanel;

    private Histogram histogram;
    private JDialog chartDialog;

    private JTipOfTheDay tipOfTheDay;


    public MainWindow(int width, int height, String title){

        //creating frame
        frame = new JFrame();

        frame.setTitle(title);

        //setting window size
        Dimension frameSize = new Dimension(width, height);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        if(frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        else if(frameSize.height < screenSize.height/2)
            frameSize.height = screenSize.height/2;
        if(frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        else if(frameSize.width < screenSize.width/2)
            frameSize.width = screenSize.width/2;

        frame.setSize(frameSize);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                disposeAndExit();
            }
        });


        //main content
        mainContent = (JPanel) frame.getContentPane();
        mainContent.setLayout(new BorderLayout());
        mainContent.setBackground(Color.GRAY);

        //creating panels and adding them to the layout
        createMainPanel();
        createToolBar();

        //creating navigation panel
        createNavigationBar();

        //menu
        MainMenu menu = new MainMenu(this);
        frame.setJMenuBar(menu);

        //tip of the day
        setTipOfTheDay();
    }

    private void setTipOfTheDay(){
        DefaultTipModel tipModel = new DefaultTipModel();

        tipModel.add(new DefaultTip("Tip 1", " Learn By Doing, Practicing and Not Just Reading"));
        tipModel.add(new DefaultTip("Tip 2", "“The code works” isn't where you stop; it's where you start"));

        tipOfTheDay = new JTipOfTheDay(tipModel);

        tipOfTheDay.showDialog(frame);
    }

    private void createToolBar(){
        toolBar = new MainToolBar(this);

        mainContent.add(toolBar, BorderLayout.NORTH);
    }

    private void createMainPanel(){
        centrePanel = new MainPanel(this);

        mainContent.add(centrePanel, BorderLayout.CENTER);
    }

    private void createNavigationBar(){
        NavigationBar navigationBar = new NavigationBar(this);

        mainContent.add(navigationBar, BorderLayout.WEST);
    }

    public void createHistogram(){
        histogram = new Histogram(centrePanel.topPanel.getTable().model.toDoubleArray());

        ChartPanel chartPanel = new ChartPanel(histogram.getHistogramChart());

        chartDialog = new JDialog();
        chartDialog.add(chartPanel);
        chartDialog.pack();

        chartDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        chartDialog.setModal(true);

        chartDialog.setVisible(true);
    }

    public void disposeAndExit(){
        int value = JOptionPane.showOptionDialog(
                frame,
                "Czy chcesz zamknac aplikacje?",
                "Uwaga",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                new String[] {"Tak", "Nie"},
                "Tak"
        );


        if(value == JOptionPane.YES_OPTION){
            frame.dispose();
            System.exit(0);
        }
    }

    public void showAuthor(){
        JOptionPane.showMessageDialog(frame,
                "Wersja GUI: 1.1\nAutor:\nJakub Swistak\nkuba175174@gmail.com\nCopyright \u00a9 by JS 2021",
                "Autor",
                JOptionPane.INFORMATION_MESSAGE,
                IconLoader.loadIcon("/logo.png")
        );
    }

    public void showHelp(){
        String url = "res/help.html";
        File file = new File(url);

        try{
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public JFrame getFrame(){
        return frame;
    }

    public MainPanel getMainPanel() {
        return centrePanel;
    }

}
