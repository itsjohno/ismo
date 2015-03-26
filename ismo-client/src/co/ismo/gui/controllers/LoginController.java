package co.ismo.gui.controllers;

import co.ismo.objects.Operator;

public class LoginController {

    public static boolean attemptLogin(String input) {
        if (input.equalsIgnoreCase("12345")) {
            System.out.println("Login Success!");
            return true;
        } else {
            System.out.println("Login Failure");
            return false;
        }
    }
}
