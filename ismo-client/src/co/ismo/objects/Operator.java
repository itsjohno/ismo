package co.ismo.objects;

public class Operator {
    String forename;
    String surname;
    String encodedTAN;
    int employeeID;

    public Operator() {}

    public Operator(String forename, String surname, String encodedTAN, int employeeID) {
        this.forename = forename;
        this.surname = surname;
        this.encodedTAN = encodedTAN;
        this.employeeID = employeeID;
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
}
