//import the swing and AWT (Abstract Window Toolkit) library for the GUI
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import  java.awt.*;



public class MainWindow {

    public static void main(String[] args){
        SwingUtilities.invokeLater(
                ()->{
                    //create the window using JFrame
                    JFrame frame = new JFrame("NorthWind Database UI");
                    //stop execution upon closing the window
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    //set window size
                    Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
                    int height = windowSize.height;
                    int width = windowSize.width;
                    frame.setSize(width, height);
                    //center in window
                    frame.setLocationRelativeTo(null);
                    createTabs(frame);
                    //display the window
                    frame.setVisible(true);
                }
        );
    }

    private static void createTabs(JFrame frame){
        JTabbedPane pane = new JTabbedPane();

        JPanel employees = new Employees().setEmployeesTab();
        pane.addTab("Employees", employees);

        JPanel products  = new Products().setProductsTab();
        pane.addTab("Products", products);

        JPanel report = new Reports().setReportsTab();
        pane.addTab("Report", report);

        JPanel notifications = new Notifications().setNotificationsTab();
        pane.addTab("Notifications", notifications);

        //add the whole pane to the frame
        frame.add(pane, BorderLayout.CENTER);
    }
}
