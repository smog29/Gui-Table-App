package app.mainWindow;

import app.mainWindow.mainPanel.MainPanel;
import app.mainWindow.menu.MainMenu;
import app.mainWindow.toolBar.MainToolBar;
import graphics.IconLoader;

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


        //main panel
        mainContent = (JPanel) frame.getContentPane();
        mainContent.setLayout(new BorderLayout());
        mainContent.setBackground(Color.GRAY);

        //creating panels and adding them to the layout
        createMainPanel();
        createToolBar();

        //menu
        MainMenu menu = new MainMenu(this);
        frame.setJMenuBar(menu);
    }

    private void createToolBar(){
        toolBar = new MainToolBar(this);

        mainContent.add(toolBar, BorderLayout.NORTH);
    }

    private void createMainPanel(){
        centrePanel = new MainPanel(this);

        mainContent.add(centrePanel, BorderLayout.CENTER);
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
                "Jakub Swistak \nPoczta: kuba175174@gmail.com",
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
