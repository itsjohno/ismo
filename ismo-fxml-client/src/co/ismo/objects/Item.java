package co.ismo.objects;

import co.ismo.util.Enumeration;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Johnathan
 * Package: co.ismo.objects
 * Date: 08/04/2015
 * Project: ismo-fxml-client
 */
public class Item {

    private String barcode;
    private String sku;
    private String name;
    private String description;
    private String supercat;
    private String subcat;
    private int cost;
    private Enumeration.AgeRating ageRating;

    public Item(String sku) {
        //barcode = "000000000000";
        this.sku = sku;
        name = "Unidentified Product";
        description = "An unidentified product";
        supercat = "Female Clothing";
        subcat = "Shoes";
        cost = 999;
        ageRating = Enumeration.AgeRating.BBFC_Fifteen;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupercat() {
        return supercat;
    }

    public void setSupercat(String supercat) {
        this.supercat = supercat;
    }

    public String getSubcat() {
        return subcat;
    }

    public void setSubcat(String subcat) {
        this.subcat = subcat;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Enumeration.AgeRating getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(Enumeration.AgeRating ageRating) {
        this.ageRating = ageRating;
    }

    public boolean ageCheck(Date customerDOB) {

        Calendar cal = Calendar.getInstance();
        Date today = new Date();

        cal.setTime(today);
        cal.add(Calendar.YEAR, -ageRating.getValue());

        if (customerDOB.before(cal.getTime())) {
            return true;
        } else {
            return false;
        }
    }
}
