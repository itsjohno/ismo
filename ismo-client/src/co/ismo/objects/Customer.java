package co.ismo.objects;

import co.ismo.objects.interfaces.IAddress;
import java.util.Date;

public class Customer {

    long ID;
    long loyaltyNumber;
    String forename;
    String surname;
    IAddress address;
    Date signup;
    Date lastVisit;

    public Customer() {

    }

    public Customer(long id) {

    }


}
