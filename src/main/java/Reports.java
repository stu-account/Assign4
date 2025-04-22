import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Reports {
    public JPanel setReportsTab(){
        JPanel report = new JPanel();
        report.setLayout(new BorderLayout());

        String[] columns = {"Category", "NumberOfItems"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable reportTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(reportTable);
        report.add(scrollPane);

        Connection connection = Database.getInstance().getConnection();
        try{
            Statement stmt = connection.createStatement();
            String query = "SELECT category, COUNT(*) AS NumItems FROM products GROUP BY category";
            ResultSet result = stmt.executeQuery(query);
            while (result.next()){
                String category = result.getString("category");
                String numItems = result.getString("NumItems");

                model.addRow(new Object[]{category, numItems});
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return report;
    }
}
