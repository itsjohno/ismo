package co.ismo.object.util;

import co.ismo.core.DatabaseConnector;
import co.ismo.object.type.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Johnathan
 * Package: co.ismo.object.util
 * Date: 16/04/2015
 * Project: ismo-fxml-client
 */
public class ProductUtility {

    private Product createItem(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setSku(rs.getString("sku"));
        product.setName(rs.getString("name"));
        product.setStock(rs.getInt("stock"));
        product.setCategoryID(rs.getInt("categoryID"));
        product.setAgeRating(rs.getInt("ageRating"));
        product.getBarcodes().add(rs.getString("barcode"));
        product.setPrice(rs.getInt("price"));
        return product;
    }

    public Product getItem(String lookupString) {
        Product foundProduct = null;
        DatabaseConnector dbConnector = new DatabaseConnector();

        String queryBarcode = "SELECT sku FROM product_barcode WHERE `barcode` = ?";
        String query = "SELECT * FROM productLookup WHERE `sku` = ?";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement barcodePreparedStatement = connection.prepareStatement(queryBarcode);
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            barcodePreparedStatement.setString(1, lookupString);

            try (ResultSet skuResults = barcodePreparedStatement.executeQuery()) {
                if (skuResults.next()) {
                    preparedStatement.setString(1, skuResults.getString("sku"));

                    try (ResultSet results = preparedStatement.executeQuery()) {
                        while (results.next()) {
                            if (foundProduct == null) {
                                foundProduct = createItem(results);
                            } else {
                                foundProduct.getBarcodes().add(results.getString("barcode"));
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return foundProduct;
    }

}
