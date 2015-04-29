package co.ismo.object.type;

/**
 * Created by Johnathan
 * Package: co.ismo.object.type
 * Date: 19/04/2015
 * Project: ismo-fxml-client
 */
public class Category {

    private String name;
    private int supercatID;

    public Category() {
    }

    public Category(String name, int supercatID) {
        this.name = name;
        this.supercatID = supercatID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSupercatID() {
        return supercatID;
    }

    public void setSupercatID(int supercatID) {
        this.supercatID = supercatID;
    }
}
