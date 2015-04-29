package co.ismo.object.util;

import co.ismo.core.DatabaseConnector;
import co.ismo.object.type.AgeRating;
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
public class AgeRatingUtility {

    private AgeRating createAgeRating(ResultSet rs) throws SQLException {
        AgeRating ar = new AgeRating();
        ar.setAgeRequired(rs.getInt("ageRequired"));
        ar.setAdvisory(rs.getBoolean("advisory"));
        ar.setName(rs.getString("name"));
        return ar;
    }

    public HashMap<Integer, AgeRating> getAgeRatings() {
        HashMap<Integer, AgeRating> ageRatings = new HashMap<Integer, AgeRating>();
        DatabaseConnector dbConnector = new DatabaseConnector();

        String query = "SELECT * FROM age_ratings";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            try (ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    ageRatings.put(results.getInt("ageRatingID"), createAgeRating(results));
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return ageRatings;
    }

    public int getAgeRatingIDFromName(String name) {
        int ageRatingID = 0;

        for (AgeRating c : DynamicHashMap.getAgeRatings().values()) {
            if (c.getName().equalsIgnoreCase(name)) {
                break;
            }
            ageRatingID++;
        }

        return ageRatingID;
    }
}
