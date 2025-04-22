import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Employees {
    public JPanel setEmployeesTab(){
        JPanel employees = new JPanel();
        employees.setLayout(new BorderLayout());

        String[] columns = {"FirstName", "LastName", "Address", "AddressLine2", "City", "Region", "PostalCode", "Phone", "Office", "Active"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable employeesTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(employeesTable);

        JPanel input = new JPanel();
        JLabel label = new JLabel("Filter");
        JTextField filter = new JTextField(15);

        JButton filterBtn = new JButton("Apply Filter");

        Connection connection = Database.getInstance().getConnection();
        filterBtn.addActionListener(e -> {
            String keyword = filter.getText().trim();
            try{
                model.setRowCount(0);
                Statement stmt = connection.createStatement();
                String sql = String.format(
                        "SELECT * FROM employees WHERE first_name LIKE '%%%s%%' OR last_name LIKE '%%%s%%' OR email_address LIKE '%%%s%%' OR job_title LIKE '%%%s%%' OR city LIKE '%%%s%%'",
                        keyword, keyword, keyword, keyword, keyword
                );
                ResultSet result = stmt.executeQuery(sql);
                while (result.next()){
                    String firstName = result.getString("first_name");
                    String lastName = result.getString("last_name");
                    String address = result.getString("address");
                    String addressLine2 = result.getString("state_province");
                    String city = result.getString("city");
                    String region = result.getString("country_region");
                    String postalCode = result.getString("zip_postal_code");
                    String phone = result.getString("home_phone");
//                String office = result.getString("office");
//                boolean active = result.getBoolean("active");
                    model.addRow(new Object[]{
                            firstName,
                            lastName,
                            address,
                            addressLine2,
                            city,
                            region,
                            postalCode,
                            phone,
//                        office,
//                        active
                    });
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }
        });

//        Map<String, String> columnMapping = new HashMap<>();
//        columnMapping.put("Firstname", "first_name");
//        columnMapping.put("Lastname", "last_name");
//        columnMapping.put("Address", "address");
//        columnMapping.put("Addressline2", "state_province");
//        columnMapping.put("City", "city");
//        columnMapping.put("Region", "country_region");
//        columnMapping.put("Postalcode", "zip_postal_code");
//        columnMapping.put("Phone", "mobile_phone");
//        columnMapping.put("Office", "office");
//        columnMapping.put("Active", "active");
//
//        filterBtn.addActionListener(e->{
//            String filterText = filter.getText();
//            filterText = TextFormatter.toSentenceCase(filterText);
//            System.out.println("Filter text: " + filterText);
//            String columnName = columnMapping.get(filterText);
//            System.out.println("Mapped column: " + columnName);
//
//        });

        input.add(label);
        input.add(filter);
        input.add(filterBtn);


        employees.add(input, BorderLayout.NORTH);
        employees.add(scrollPane, BorderLayout.CENTER);

        //load data


        try{
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM employees";
            ResultSet result = stmt.executeQuery(query);
            while (result.next()){
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String address = result.getString("address");
                String addressLine2 = result.getString("state_province");
                String city = result.getString("city");
                String region = result.getString("country_region");
                String postalCode = result.getString("zip_postal_code");
                String phone = result.getString("home_phone");
//                String office = result.getString("office");
//                boolean active = result.getBoolean("active");
                model.addRow(new Object[]{
                        firstName,
                        lastName,
                        address,
                        addressLine2,
                        city,
                        region,
                        postalCode,
                        phone,
//                        office,
//                        active
                });
            }
        }catch(Exception e){
            e.printStackTrace();
        }
//
        return employees;
    }
};
