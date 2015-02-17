package co.ismo.objects;

import co.ismo.objects.interfaces.IAddress;

public class Address implements IAddress {

    private String lineOne; // Door Number/House Name + Street
    private String lineTwo; // Flat Number etc
    private String town;
    private String county;
    private String country;

    public Address(String lineOne, String lineTwo, String town, String county, String country) {
        this.lineOne = lineOne;
        this.lineTwo = lineTwo;
        this.town = town;
        this.county = county;
        this.country = country;
    }
}
