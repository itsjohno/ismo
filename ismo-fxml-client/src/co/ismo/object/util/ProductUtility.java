package co.ismo.object.util;

import co.ismo.core.DatabaseConnector;
import co.ismo.object.type.Category;
import co.ismo.object.type.Product;
import co.ismo.util.DynamicHashMap;
import com.mysql.jdbc.StringUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        String queryBarcode = "SELECT * FROM product_barcode WHERE `barcode` = ? OR `sku` = ?";
        String query = "SELECT * FROM productLookup WHERE `sku` = ?";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement barcodePreparedStatement = connection.prepareStatement(queryBarcode);
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            barcodePreparedStatement.setString(1, lookupString);
            barcodePreparedStatement.setString(2, lookupString);

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

    public ObservableList<Product> lookupProducts(String sku, String barcode, String name, int supercat, int category, int ageRating, int fromPrice, int toPrice) {
        int paramsSet = 0;
        List<Product> productList = new ArrayList<Product>();
        DatabaseConnector dbConnector = new DatabaseConnector();

        if (!StringUtils.isNullOrEmpty(barcode)) {
            String queryBarcode = "SELECT * FROM product_barcode WHERE `barcode` = ?";

            try (Connection connection = dbConnector.getConnection();
                 PreparedStatement barcodePreparedStatement = connection.prepareStatement(queryBarcode);) {

                barcodePreparedStatement.setString(1, barcode);

                try (ResultSet skuResults = barcodePreparedStatement.executeQuery()) {
                    if (skuResults.next()) {
                        sku = skuResults.getString("sku");
                    }
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }

        String query = "SELECT * FROM productLookup WHERE 1=1 ";
        if (!StringUtils.isNullOrEmpty(sku)) {
            query += "AND `sku` = ? ";
            paramsSet++;
        }
        if (!StringUtils.isNullOrEmpty(name)) {
            query += "AND `name` LIKE ? ";
            paramsSet++;
        }
        if (category != 0) {
            query += "AND `categoryID` = ? ";
            paramsSet++;
        }
        if (supercat != 0 && category == 0) {
            query += "AND `categoryID` IN (";

            String subcatString = "";
            CategoryUtility cu = new CategoryUtility();

            for (Category c : DynamicHashMap.getSubCategories().values()) {
                if (c.getSupercatID() == supercat) {
                    subcatString += Integer.toString(cu.getCategoryIDFromName(c.getName())) + ",";
                }
            }

            if (!StringUtils.isNullOrEmpty(subcatString)) {
                query += subcatString.substring(0, subcatString.length() - 1) + ")";
            } else {
                query += "0)";
            }
        }
        if (ageRating != 0) {
            query += "AND `ageRating` = ? ";
            paramsSet++;
        }
        if (fromPrice != 0) {
            query += "AND `price` >= ? ";
            paramsSet++;
        }
        if (toPrice != 0) {
            query += "AND `price` <= ?";
            paramsSet++;
        }

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            int currentParam = 1;
            while (currentParam <= paramsSet) {
                if (!StringUtils.isNullOrEmpty(sku)) {
                    preparedStatement.setString(currentParam, sku);
                    currentParam++;
                }
                if (!StringUtils.isNullOrEmpty(name)) {
                    String nameLike = "%";
                    for (String s : name.split(" ")) {
                        nameLike += s + "%";
                    }
                    preparedStatement.setString(currentParam, nameLike);
                    currentParam++;
                }
                if (category != 0) {
                    preparedStatement.setInt(currentParam, category);
                    currentParam++;
                }

                if (ageRating != 0) {
                    preparedStatement.setInt(currentParam, ageRating);
                    currentParam++;
                }
                if (fromPrice != 0) {
                    preparedStatement.setInt(currentParam, fromPrice);
                    currentParam++;
                }
                if (toPrice != 0) {
                    preparedStatement.setInt(currentParam, toPrice);
                    currentParam++;
                }
            }

            try (ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    boolean productInList = false;

                    for (Product p : productList) {
                        if (p.getSku().equalsIgnoreCase(results.getString("sku"))) {
                            p.getBarcodes().add(results.getString("barcode"));
                            productInList = true;
                            break;
                        }
                    }

                    if (!productInList) {
                        productList.add(createItem(results));
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return FXCollections.observableArrayList(productList);
    }
}
