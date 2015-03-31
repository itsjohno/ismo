package co.ismo.objects;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class Item {

    private String barcode;
    private String sku;
    private String name;
    private String description;
    private int cost;
    private short ageRating;

    public Item(String sku) {
        barcode = "000000000000";
        this.sku = sku;
        name = "Unidentified Product";
        description = "An unidentified product";
        cost = 999;
        ageRating = 15;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public short getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(short ageRating) {
        this.ageRating = ageRating;
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
