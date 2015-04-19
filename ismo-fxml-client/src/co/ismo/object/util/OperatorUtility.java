package co.ismo.object.util;

import co.ismo.core.CoreUtility;
import co.ismo.core.DatabaseConnector;
import co.ismo.object.type.Operator;

import java.sql.*;

/**
 * Created by Johnathan
 * Package: co.ismo.core
 * Date: 16/04/2015
 * Project: ismo-fxml-client
 */
public class OperatorUtility {

    private Operator createOperator(ResultSet op) throws SQLException {
        Operator operator = new Operator();
        operator.setOperatorID(op.getString("operatorID"));
        operator.setForename(op.getString("forename"));
        operator.setSurname(op.getString("surname"));
        operator.setUserLevel(op.getInt("userLevel"));
        return operator;
    }

    public Operator getOperatorByTAN(String tan) {

        Operator foundOperator = null;
        DatabaseConnector dbConnector = new DatabaseConnector();

        String query = "SELECT forename, surname, operatorID, userLevel FROM operator WHERE encodedTAN = ?";
        String encodedTan = CoreUtility.encodeString(tan);

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1, encodedTan);

            try (ResultSet results = preparedStatement.executeQuery()) {
                if (results.next()) {
                    foundOperator = createOperator(results);
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return foundOperator;
    }
}
