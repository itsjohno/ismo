package co.ismo.object.util;

import co.ismo.core.DatabaseConnector;
import co.ismo.object.type.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Johnathan
 * Package: co.ismo.object.util
 * Date: 20/04/2015
 * Project: ismo-fxml-client
 */
public class TransactionUtility {

    private Transaction createEmptyTransaction(ResultSet rs) throws SQLException {
        Transaction t = new Transaction(rs.getString("storeID"), rs.getString("tillID"), Integer.toString((Integer.parseInt(rs.getString("transactionID")) + 1)));
        return t;
    }

    public Transaction getNextTransaction() {
        Transaction transaction = null;
        DatabaseConnector dbConnector = new DatabaseConnector();

        String query = "SELECT * FROM transaction WHERE storeID = ? AND tillID = ? ORDER BY transactionID LIMIT 1";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1, "DD");
            preparedStatement.setString(2, "1");

            try (ResultSet results = preparedStatement.executeQuery()) {
                if (results.next()) {
                    transaction = createEmptyTransaction(results);
                } else {
                    transaction = new Transaction("DD", "1", "1");
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return transaction;
    }

}
