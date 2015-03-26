package co.ismo.util;

public abstract class Constants {
    /* Development Constants */
    public static boolean DEVELOPER_MODE = false;
    public static String VERSION_NUMBER = "V0.0.1";

    /* User Constants */
    public static boolean SINGLE_USER_TERMINAL = false;
    public static int USER_TIMEOUT = 120000;
    public static int TAN_LENGTH = 5;

    /* Currency Constants */
    public static String CURRENCY_CODE = "GBP";
    public static int DECIMAL_PLACES = 2;

    /* Transaction Constants */
    /* All monetary values stored as integers, as pennies */
    public static int MAX_TRANSACTION_VALUE = 250000;
    public static int MIN_TRANSACTION_VALUE = 0;
}
