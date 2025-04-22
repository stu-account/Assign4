import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//create a singleton
public class Database {
    private static Database instance;
    private Connection connection;

    // Fetching the environment variables
    String url = System.getenv("dvdrental_DB_PROTO") + "://" + System.getenv("dvdrental_DB_HOST") + ":" + System.getenv("dvdrental_DB_PORT") + "/" + System.getenv("dvdrental_DB_NAME");
    String user = System.getenv("dvdrental_DB_USERNAME");
    String password = System.getenv("dvdrental_DB_PASSWORD");


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
