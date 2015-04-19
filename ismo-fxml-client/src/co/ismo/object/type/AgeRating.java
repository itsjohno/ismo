package co.ismo.object.type;

/**
 * Created by Johnathan
 * Package: co.ismo.object.type
 * Date: 19/04/2015
 * Project: ismo-fxml-client
 */
public class AgeRating {

    private int ageRequired;
    private boolean advisory;
    private String name;

    public AgeRating() {}

    public AgeRating(int ageRequired, boolean advisory, String name) {
        this.ageRequired = ageRequired;
        this.advisory = advisory;
        this.name = name;
    }

    public int getAgeRequired() {
        return ageRequired;
    }

    public void setAgeRequired(int ageRequired) {
        this.ageRequired = ageRequired;
    }

    public boolean isAdvisory() {
        return advisory;
    }

    public void setAdvisory(boolean advisory) {
        this.advisory = advisory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
