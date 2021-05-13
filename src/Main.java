import app.mainWindow.MainWindow;



public class Main {



    public static void main(String[] args){
        MainWindow app = new MainWindow(1300,800,"App");

        app.getFrame().setVisible(true);
    }
}
