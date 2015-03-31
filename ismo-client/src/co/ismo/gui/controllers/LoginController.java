package co.ismo.gui.controllers;

import co.ismo.objects.Operator;
import co.ismo.util.Constants;

public class LoginController {

    public Operator attemptLogin(String input) {
        for (Operator o : Constants.operators) {
            if (input.equalsIgnoreCase(o.getEncodedTAN())) {
                System.out.println("Logged in as " + o.getForename() + " " + o.getSurname());
                return o;
            }
        }
        // TODO: Log attempted login with timestamp and entered TAN?
        return null;
    }
}
