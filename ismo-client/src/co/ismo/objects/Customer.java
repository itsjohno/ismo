package co.ismo.objects;

import co.ismo.objects.interfaces.IAddress;
import java.util.Date;

public class Customer {

    long ID;
    String forename;
    String surname;
    IAddress address;
    Date signup;
    Date lastVisit;

}
