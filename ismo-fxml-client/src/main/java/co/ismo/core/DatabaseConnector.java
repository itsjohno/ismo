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

    private String dbURL = "jdbc:mysql://ec2-52-17-144-142.eu-west-1.compute.amazonaws.com/ismo_new";
    private String dbUser = "ismo";
    private String dbPassword = "QyIOw0Bq5OclCWzp";

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

        try {
            connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return connection;
    }
}
