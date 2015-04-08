package co.ismo.objects;

/**
 * Created by Johnathan
 * Package: co.ismo.objects
 * Date: 06/04/2015
 * Project: ismo-fxml-client
 */

public class Operator {
    String forename;
    String surname;
    String encodedTAN;
    int employeeID;
    int userLevel;

    public Operator() {
    }

    public Operator(String forename, String surname, String encodedTAN, int employeeID, int userLevel) {
        this.forename = forename;
        this.surname = surname;
        this.encodedTAN = encodedTAN;
        this.employeeID = employeeID;
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

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getUserLevel() { return userLevel; }

    public void setUserLevel(int userLevel) { this.userLevel = userLevel; }
}
