package co.ismo.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Johnathan
 * Package: co.ismo.core
 * Date: 16/04/2015
 * Project: ismo-fxml-client
 */
public class DatabaseConnector {

    public DatabaseConnector() {
        loadDriver();
    }

    public void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println("An error occurred! " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        Connection connection = null;

        String dbURL = "jdbc:mysql://" + Configuration.getStringField("database.host") + "/" + Configuration.getStringField("database.schema");

        try {
            connection = DriverManager.getConnection(dbURL, Configuration.getStringField("database.username"), Configuration.getStringField("database.password"));
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return connection;
    }
}
