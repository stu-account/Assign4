import javafx.beans.property.ReadOnlySetProperty;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Notifications {
    public JPanel setNotificationsTab(){
        JPanel notifications = new JPanel();
        notifications.setLayout(new BorderLayout());

        String[] columns = {
                "ID", "Company", "LastName", "FirstName", "EmailAddress", "JobTitle",
                "BusinessPhone", "HomePhone", "MobilePhone", "FaxNumber", "Address",
                "City", "StateProvince", "ZipPostalCode", "CountryRegion",
                "WebPage", "Notes", "Attachments"
        };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable notificationsTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(notificationsTable);
        notifications.add(scrollPane, BorderLayout.CENTER);

        Connection connection = Database.getInstance().getConnection();

        JPanel input = new JPanel();
        JButton listBtn = new JButton("List customers");
        JButton createBtn = new JButton("Add customer");
        JButton updateBtn = new JButton("Update customer information");
        JButton deleteBtn = new JButton("Remove customer");

        //4.6
        JButton inactiveBtn = new JButton("Show Inactive Customers");
        JTextField search = new JTextField(20);
        JButton searchBtn = new JButton("Search");


        listBtn.addActionListener(e -> {
            model.setRowCount(0);
            try{
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM customers";
                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    int id = result.getInt("id");
                    String company = result.getString("company");
                    String lastName = result.getString("last_name");
                    String firstName = result.getString("first_name");
                    String email = result.getString("email_address");
                    String jobTitle = result.getString("job_title");
                    String businessPhone = result.getString("business_phone");
                    String homePhone = result.getString("home_phone");
                    String mobilePhone = result.getString("mobile_phone");
                    String faxNumber = result.getString("fax_number");
                    String address = result.getString("address");
                    String city = result.getString("city");
                    String state = result.getString("state_province");
                    String zip = result.getString("zip_postal_code");
                    String country = result.getString("country_region");
                    String webPage = result.getString("web_page");
                    String notes = result.getString("notes");
                    byte[] attachments = result.getBytes("attachments"); // This might be null

                    model.addRow(new Object[] {
                            id, company, lastName, firstName, email, jobTitle,
                            businessPhone, homePhone, mobilePhone, faxNumber, address,
                            city, state, zip, country, webPage, notes, attachments
                    });
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });

        createBtn.addActionListener(e -> {
            JDialog dialog = new JDialog((Frame) null, "Add New Customer", true);
            dialog.setLayout(new GridLayout(0, 2, 5, 5));

            // Input fields
            JTextField companyField = new JTextField();
            JTextField lastNameField = new JTextField();
            JTextField firstNameField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField jobTitleField = new JTextField();
            JTextField businessPhoneField = new JTextField();
            JTextField homePhoneField = new JTextField();
            JTextField mobilePhoneField = new JTextField();
            JTextField faxField = new JTextField();
            JTextField addressField = new JTextField();
            JTextField cityField = new JTextField();
            JTextField stateField = new JTextField();
            JTextField zipField = new JTextField();
            JTextField countryField = new JTextField();
            JTextField webPageField = new JTextField();
            JTextArea notesArea = new JTextArea(3, 15);

            // Add components to dialog
            dialog.add(new JLabel("Company:")); dialog.add(companyField);
            dialog.add(new JLabel("Last Name:")); dialog.add(lastNameField);
            dialog.add(new JLabel("First Name:")); dialog.add(firstNameField);
            dialog.add(new JLabel("Email:")); dialog.add(emailField);
            dialog.add(new JLabel("Job Title:")); dialog.add(jobTitleField);
            dialog.add(new JLabel("Business Phone:")); dialog.add(businessPhoneField);
            dialog.add(new JLabel("Home Phone:")); dialog.add(homePhoneField);
            dialog.add(new JLabel("Mobile Phone:")); dialog.add(mobilePhoneField);
            dialog.add(new JLabel("Fax Number:")); dialog.add(faxField);
            dialog.add(new JLabel("Address:")); dialog.add(addressField);
            dialog.add(new JLabel("City:")); dialog.add(cityField);
            dialog.add(new JLabel("State/Province:")); dialog.add(stateField);
            dialog.add(new JLabel("Zip/Postal Code:")); dialog.add(zipField);
            dialog.add(new JLabel("Country/Region:")); dialog.add(countryField);
            dialog.add(new JLabel("Web Page:")); dialog.add(webPageField);
            dialog.add(new JLabel("Notes:")); dialog.add(notesArea);

            JButton submitBtn = new JButton("Submit");
            dialog.add(submitBtn);

            submitBtn.addActionListener(submitEvent -> {
                try {
                    Statement stmt = connection.createStatement();
                    String sql = String.format(
                            "INSERT INTO customers (company, last_name, first_name, email_address, job_title, business_phone, home_phone, mobile_phone, fax_number, address, city, state_province, zip_postal_code, country_region, web_page, notes) " +
                                    "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                            companyField.getText(),
                            lastNameField.getText(),
                            firstNameField.getText(),
                            emailField.getText(),
                            jobTitleField.getText(),
                            businessPhoneField.getText(),
                            homePhoneField.getText(),
                            mobilePhoneField.getText(),
                            faxField.getText(),
                            addressField.getText(),
                            cityField.getText(),
                            stateField.getText(),
                            zipField.getText(),
                            countryField.getText(),
                            webPageField.getText(),
                            notesArea.getText()
                    );
                    stmt.executeUpdate(sql);
                    dialog.dispose();
                    JOptionPane.showMessageDialog(null, "Customer added successfully.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Failed to add customer.");
                }
            });

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        updateBtn.addActionListener(e -> {
            // Mapping of user-friendly field names to database column names
            Map<String, String> fieldMapping = new HashMap<>();
            fieldMapping.put("Company", "company");
            fieldMapping.put("LastName", "last_name");
            fieldMapping.put("FirstName", "first_name");
            fieldMapping.put("EmailAddress", "email_address");
            fieldMapping.put("JobTitle", "job_title");
            fieldMapping.put("BusinessPhone", "business_phone");
            fieldMapping.put("HomePhone", "home_phone");
            fieldMapping.put("MobilePhone", "mobile_phone");
            fieldMapping.put("FaxNumber", "fax_number");
            fieldMapping.put("Address", "address");
            fieldMapping.put("City", "city");
            fieldMapping.put("State/Province", "state_province");
            fieldMapping.put("PostalCode", "zip_postal_code");
            fieldMapping.put("Country/Region", "country_region");
            fieldMapping.put("WebPage", "web_page");
            fieldMapping.put("Notes", "notes");

            JDialog dialog = new JDialog((Frame) null, "Update Customer Field", true);
            dialog.setLayout(new GridLayout(0, 2, 5, 5));

            JTextField idField = new JTextField();
            JTextField fieldField = new JTextField();
            JTextField valueField = new JTextField();

            dialog.add(new JLabel("Customer ID:")); dialog.add(idField);
            dialog.add(new JLabel("Field to update (e.g., Company):")); dialog.add(fieldField);
            dialog.add(new JLabel("New value:")); dialog.add(valueField);

            JButton submitBtn = new JButton("Update");
            dialog.add(submitBtn);

            submitBtn.addActionListener(ev -> {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    String field = fieldField.getText().trim();
                    String newValue = valueField.getText().trim();

                    // Translate the user input to the actual database field
                    String dbField = fieldMapping.get(field);
                    if (dbField == null) {
                        JOptionPane.showMessageDialog(dialog, "Invalid field name. Please use a valid field.");
                        return;
                    }

                    Statement stmt = connection.createStatement();
                    String sql = String.format(
                            "UPDATE customers SET %s = '%s' WHERE id = %d",
                            dbField, newValue, id
                    );

                    int rows = stmt.executeUpdate(sql);
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(dialog, "Customer updated successfully.");
                    } else {
                        JOptionPane.showMessageDialog(dialog, "No customer found with that ID.");
                    }

                    dialog.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Update failed. Please check input.");
                }
            });

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        deleteBtn.addActionListener(e -> {
            JDialog dialog = new JDialog((Frame) null, "Remove Customer", true);
            dialog.setLayout(new GridLayout(2, 2, 5, 5));

            JTextField idField = new JTextField();

            dialog.add(new JLabel("Customer ID:"));
            dialog.add(idField);

            JButton submitBtn = new JButton("Delete");
            dialog.add(submitBtn);

            submitBtn.addActionListener(ev -> {
                try {
                    int id = Integer.parseInt(idField.getText().trim());

                    // Confirm before deletion
                    int response = JOptionPane.showConfirmDialog(dialog, "Are you sure you want to delete customer ID " + id + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        Statement stmt = connection.createStatement();
                        String sql = String.format("DELETE FROM customers WHERE id = %d", id);

                        int rows = stmt.executeUpdate(sql);
                        if (rows > 0) {
                            JOptionPane.showMessageDialog(dialog, "Customer removed successfully.");
                        } else {
                            JOptionPane.showMessageDialog(dialog, "No customer found with that ID.");
                        }
                    }

                    dialog.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Error occurred while deleting the customer.");
                }
            });

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        searchBtn.addActionListener(e -> {
            String keyword = search.getText().trim();
            model.setRowCount(0);


            try{
                Statement stmt = connection.createStatement();
                String sql = String.format(
                        "SELECT * FROM customers WHERE first_name LIKE '%%%s%%' OR last_name LIKE '%%%s%%' OR email_address LIKE '%%%s%%'",
                        keyword, keyword, keyword);
                ResultSet result = stmt.executeQuery(sql);
                while (result.next()){
                    model.addRow(new Object[]{
                            result.getInt("id"), result.getString("company"),
                            result.getString("last_name"), result.getString("first_name"),
                            result.getString("email_address"), result.getString("job_title"),
                            result.getString("business_phone"), result.getString("home_phone"),
                            result.getString("mobile_phone"), result.getString("fax_number"),
                            result.getString("address"), result.getString("city"),
                            result.getString("state_province"), result.getString("zip_postal_code"),
                            result.getString("country_region"), result.getString("web_page"),
                            result.getString("notes"), result.getBytes("attachments")
                    });
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });

        inactiveBtn.addActionListener(e -> {
            model.setRowCount(0);
            try{
                Statement stmt = connection.createStatement();
                String sql = "SELECT * FROM customers WHERE id NOT IN (SELECT customer_id FROM orders)";
                ResultSet result = stmt.executeQuery(sql);
                while (result.next()){
                    model.addRow(new Object[]{
                            result.getInt("id"), result.getString("company"),
                            result.getString("last_name"), result.getString("first_name"),
                            result.getString("email_address"), result.getString("job_title"),
                            result.getString("business_phone"), result.getString("home_phone"),
                            result.getString("mobile_phone"), result.getString("fax_number"),
                            result.getString("address"), result.getString("city"),
                            result.getString("state_province"), result.getString("zip_postal_code"),
                            result.getString("country_region"), result.getString("web_page"),
                            result.getString("notes"), result.getBytes("attachments")
                    });
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });


        input.add(listBtn);
        input.add(createBtn);
        input.add(updateBtn);
        input.add(deleteBtn);
        //4.6
        input.add(inactiveBtn);
        input.add(new JLabel("Search:"));
        input.add(search);
        input.add(searchBtn);


        notifications.add(input, BorderLayout.NORTH);

        return notifications;
    }


}
