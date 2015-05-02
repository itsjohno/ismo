package co.ismo.object.util;

import co.ismo.core.DatabaseConnector;
import co.ismo.object.type.Operator;
import co.ismo.object.type.Product;
import co.ismo.object.type.Tender;
import co.ismo.object.type.Transaction;

import java.sql.*;
import java.util.Calendar;

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

        String query = "SELECT * FROM transaction WHERE storeID = ? AND tillID = ? ORDER BY transactionID DESC LIMIT 1";

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

    public boolean saveTransaction(Transaction transaction, Operator operator) {
        DatabaseConnector dbConnector = new DatabaseConnector();

        String transactionQuery = "INSERT INTO transaction (storeID, tillID, transactionID, operatorID, customerID, totalCost, datetime, suspended, cancelled) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String productQuery = "INSERT INTO transaction_product (storeID, tillID, transactionID, sku, quantity, unitPrice) VALUES (?, ?, ?, ?, ?, ?)";
        String tenderQuery = "INSERT INTO transaction_tender (storeID, tillID, transactionID, type, amount) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement transactionStatement = connection.prepareStatement(transactionQuery);
             PreparedStatement productStatement = connection.prepareStatement(productQuery);
             PreparedStatement tenderStatement = connection.prepareStatement(tenderQuery);) {

            connection.setAutoCommit(false);

            transactionStatement.setString(1, transaction.getStoreID());
            transactionStatement.setString(2, transaction.getTillID());
            transactionStatement.setString(3, transaction.getTransactionID());
            transactionStatement.setInt(4, Integer.parseInt(operator.getOperatorID()));
            transactionStatement.setNull(5, Types.NULL);
            transactionStatement.setInt(6, transaction.getTotalCost());
            transactionStatement.setDate(7, new Date(Calendar.getInstance().getTimeInMillis()));
            transactionStatement.setBoolean(8, transaction.isSuspended());
            transactionStatement.setBoolean(9, transaction.isCancelled());

            transactionStatement.executeUpdate();

            for (Product p : transaction.getProducts().keySet()) {
                productStatement.setString(1, transaction.getStoreID());
                productStatement.setString(2, transaction.getTillID());
                productStatement.setString(3, transaction.getTransactionID());
                productStatement.setString(4, p.getSku());
                productStatement.setInt(5, transaction.getProducts().get(p));
                productStatement.setInt(6, p.getPrice());
                productStatement.addBatch();
            }

            productStatement.executeBatch();

            for (Tender t : transaction.getTenders()) {
                tenderStatement.setString(1, transaction.getStoreID());
                tenderStatement.setString(2, transaction.getTillID());
                tenderStatement.setString(3, transaction.getTransactionID());
                tenderStatement.setString(4, t.getType());
                tenderStatement.setInt(5, t.getAmount());
                tenderStatement.addBatch();
            }

            tenderStatement.executeBatch();

            connection.commit();

            return true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return false;
    }

}
