package co.ismo.object.type;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Johnathan
 * Package: co.ismo.object
 * Date: 08/04/2015
 * Project: ismo-fxml-client
 */
public class Product {
    private String sku;
    private String name;
    private int stock;
    private int categoryID;
    private int ageRating;
    private List<String> barcodes;
    private int price;

    public Product() {
        barcodes = new ArrayList<String>();
    }

    public Product(String sku, String name, int stock, int categoryID, int ageRating, List<String> barcodes, int price) {
        this.sku = sku;
        this.name = name;
        this.stock = stock;
        this.categoryID = categoryID;
        this.ageRating = ageRating;
        this.barcodes = barcodes;
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(int ageRating) {
        this.ageRating = ageRating;
    }

    public List<String> getBarcodes() {
        return barcodes;
    }

    public void setBarcodes(List<String> barcodes) {
        this.barcodes = barcodes;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean ageCheck(Date customerDOB) {

        Calendar cal = Calendar.getInstance();
        Date today = new Date();

        cal.setTime(today);
        cal.add(Calendar.YEAR, -ageRating);

        if (customerDOB.before(cal.getTime())) {
            return true;
        } else {
            return false;
        }
    }
}
