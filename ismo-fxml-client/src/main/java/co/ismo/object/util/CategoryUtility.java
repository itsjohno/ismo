package co.ismo.object.util;

import co.ismo.core.DatabaseConnector;
import co.ismo.object.type.Category;
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
public class CategoryUtility {

    private Category createCategory(ResultSet rs) throws SQLException {
        Category cat = new Category();
        cat.setName(rs.getString("name"));
        cat.setSupercatID(rs.getInt("supercatID"));
        return cat;
    }

    public HashMap<Integer, Category> getCategories() {
        HashMap<Integer, Category> categories = new HashMap<Integer, Category>();
        DatabaseConnector dbConnector = new DatabaseConnector();

        String query = "SELECT * FROM category";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            try (ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    categories.put(results.getInt("categoryID"), createCategory(results));
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return categories;
    }

    public int getCategoryIDFromName(String name) {
        int catID = 1;

        for (Category c : DynamicHashMap.getSubCategories().values()) {
            if (c.getName().equalsIgnoreCase(name)) {
                break;
            }
            catID++;
        }

        return catID;
    }
}