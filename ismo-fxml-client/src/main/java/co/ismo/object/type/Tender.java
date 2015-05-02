package co.ismo.object.type;

/**
 * Created by Johnathan
 * Package: co.ismo.object.type
 * Date: 02/05/2015
 * Project: ismo-fxml-client
 */
public class Tender {

    private String type;
    private int amount;

    public Tender(String type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
