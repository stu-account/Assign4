import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//create a singleton
public class Database {
    private static Database instance;
    private Connection connection;

    private String url = "jdbc:mariadb://localhost:3306/u24616592_u24579484_northwind";
    private String user = "root";
    private String password = "boop";

    //private constructor to prevent instantiation from outside
    private Database(){
        try{
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("CONNECTED.");
        }catch (SQLException e){
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }

    public static Database getInstance(){
        if (instance == null) instance = new Database();
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close connection:");
            e.printStackTrace();
        }
    }
}
