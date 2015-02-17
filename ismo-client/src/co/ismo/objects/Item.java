package co.ismo.objects;

import java.math.BigDecimal;
import java.util.Date;

public class Item {

    private String barcode;
    private String sku;
    private String name;
    private String description;
    private BigDecimal cost;
    private short ageRating;

    private boolean ageCheck(Date customerDOB) {
        return true;
    }
}
