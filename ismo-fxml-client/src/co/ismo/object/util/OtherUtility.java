package co.ismo.object.util;

import co.ismo.core.DatabaseConnector;
import co.ismo.util.DynamicHashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Johnathan
 * Package: co.ismo.object.util
 * Date: 19/04/2015
 * Project: ismo-fxml-client
 */
public class OtherUtility {

    public HashMap<Integer, String> getSuperCategories() {
        HashMap<Integer, String> supercats = new HashMap<Integer, String>();
        DatabaseConnector dbConnector = new DatabaseConnector();

        String query =  "SELECT * FROM super_category";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            try (ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    supercats.put(results.getInt("supercatID"), results.getString("name"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return supercats;
    }

    public HashMap<Integer, String> getUserLevels() {
        HashMap<Integer, String> userLevels = new HashMap<Integer, String>();
        DatabaseConnector dbConnector = new DatabaseConnector();

        String query =  "SELECT * FROM user_levels";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            try (ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    userLevels.put(results.getInt("userLevelID"), results.getString("name"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return userLevels;
    }

    public int getSuperCategoryIDFromName(String name) {
        int catID = 0;

        for (String s : DynamicHashMap.getSuperCategories().values()) {
            if (s.equalsIgnoreCase(name)) {
                break;
            }
            catID++;
        }

        return catID;
    }
}
