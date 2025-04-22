import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Products {
    public JPanel setProductsTab(){
        JPanel products = new JPanel();
        products.setLayout(new BorderLayout());

        String[] columns = {
                "ProductCode", "ProductName", "Description", "StandardCost", "ListPrice",
                "ReorderLevel", "TargetLevel", "QuantityPerUnit", "Discontinued",
                "MinimumReorderQuantity", "Category"
        };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable productsTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(productsTable);

        JPanel input = new JPanel();
        JButton addBtn = new JButton("Add record");
        Connection connection = Database.getInstance().getConnection();
        addBtn.addActionListener(e -> {
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(products), "Add New Product", true);
            dialog.setLayout(new GridLayout(0, 2, 5, 5));
            dialog.setSize(400, 400);

            JTextField codeField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField descField = new JTextField();
            JTextField costField = new JTextField();
            JTextField priceField = new JTextField();
            JComboBox<String> supplierBox = new JComboBox<>();
            JComboBox<String> categoryBox = new JComboBox<>();

            // Fetch suppliers and categories from DB
            try {
                Statement stmt = connection.createStatement();
                ResultSet rsSuppliers = stmt.executeQuery("SELECT DISTINCT supplier_ids FROM products");
                while (rsSuppliers.next()) {
                    supplierBox.addItem(rsSuppliers.getString("supplier_ids"));
                }

                ResultSet rsCategories = stmt.executeQuery("SELECT DISTINCT category FROM products");
                while (rsCategories.next()) {
                    categoryBox.addItem(rsCategories.getString("category"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            dialog.add(new JLabel("Product Code:")); dialog.add(codeField);
            dialog.add(new JLabel("Product Name:")); dialog.add(nameField);
            dialog.add(new JLabel("Description:")); dialog.add(descField);
            dialog.add(new JLabel("Standard Cost:")); dialog.add(costField);
            dialog.add(new JLabel("List Price:")); dialog.add(priceField);
            dialog.add(new JLabel("Supplier:")); dialog.add(supplierBox);
            dialog.add(new JLabel("Category:")); dialog.add(categoryBox);

            JButton submit = new JButton("Add Product");
            dialog.add(submit);

            submit.addActionListener(ev -> {
                try {
                    Statement stmt = connection.createStatement();
                    String sql = String.format(
                            "INSERT INTO products (product_code, product_name, description, standard_cost, list_price, supplier_ids, category) " +
                                    "VALUES ('%s', '%s', '%s', %s, %s, '%s', '%s')",
                            codeField.getText(),
                            nameField.getText(),
                            descField.getText(),
                            costField.getText(),
                            priceField.getText(),
                            supplierBox.getSelectedItem(),
                            categoryBox.getSelectedItem()
                    );
                    stmt.executeUpdate(sql);
                    dialog.dispose();

                    // Refresh the table
                    model.setRowCount(0);
                    ResultSet result = stmt.executeQuery("SELECT * FROM products");
                    while (result.next()) {
                        model.addRow(new Object[]{
                                result.getString("product_code"),
                                result.getString("product_name"),
                                result.getString("description"),
                                result.getDouble("standard_cost"),
                                result.getDouble("list_price"),
                                result.getInt("reorder_level"),
                                result.getInt("target_level"),
                                result.getString("quantity_per_unit"),
                                result.getBoolean("discontinued"),
                                result.getInt("minimum_reorder_quantity"),
                                result.getString("category")
                        });
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            dialog.setLocationRelativeTo(products);
            dialog.setVisible(true);
        });

        input.add(addBtn);
        products.add(input, BorderLayout.NORTH);
        products.add(scrollPane, BorderLayout.CENTER);

        //load data


        try{
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM products";
            ResultSet result = stmt.executeQuery(query);
            while (result.next()){
                String productCode = result.getString("product_code");
                String productName = result.getString("product_name");
                String description = result.getString("description");
                double standardCost = result.getDouble("standard_cost");
                double listPrice = result.getDouble("list_price");
                int reorderLevel = result.getInt("reorder_level");
                int targetLevel = result.getInt("target_level");
                String quantityPerUnit = result.getString("quantity_per_unit");
                boolean discontinued = result.getBoolean("discontinued");
                int minReorderQty = result.getInt("minimum_reorder_quantity");
                String category = result.getString("category");
                model.addRow(new Object[]{
                        productCode, productName, description, standardCost, listPrice,
                        reorderLevel, targetLevel, quantityPerUnit, discontinued,
                        minReorderQty, category
                });
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return products;
    }
}
