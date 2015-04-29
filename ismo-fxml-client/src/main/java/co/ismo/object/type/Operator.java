package co.ismo.object.type;

/**
 * Created by Johnathan
 * Package: co.ismo.object
 * Date: 06/04/2015
 * Project: ismo-fxml-client
 */

public class Operator {
    private String operatorID;
    private String forename;
    private String surname;
    private String encodedTAN;
    private int userLevel;

    public Operator() {
    }

    public Operator(String forename, String surname, String encodedTAN, String operatorID, int userLevel) {
        this.forename = forename;
        this.surname = surname;
        this.encodedTAN = encodedTAN;
        this.operatorID = operatorID;
        this.userLevel = userLevel;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEncodedTAN() {
        return encodedTAN;
    }

    public void setEncodedTAN(String encodedTAN) {
        this.encodedTAN = encodedTAN;
    }

    public String getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(String operatorID) {
        this.operatorID = operatorID;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }
}
