package co.ismo.util;

public abstract class Constants {
    /* Development Constants */
    public static final boolean DEVELOPER_MODE = true;

    /* User Constants */
    public static final boolean SINGLE_USER_TERMINAL = false;
    public static final int USER_TIMEOUT = 120000;
    public static final int TAN_LENGTH = 5;

    /* Currency Constants */
    public static final String CURRENCY_CODE = "GBP";
    public static final int DECIMAL_PLACES = 2;

    /* Transaction Constants */
    /* All monetary values stored as integers, as pennies */
    public static final int MAX_TRANSACTION_VALUE = 250000;
    public static final int MIN_TRANSACTION_VALUE = 0;
}
