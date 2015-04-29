package co.ismo.core;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * Created by johnathanlaw on 29/04/15.
 */
public class Configuration {

    private static XMLConfiguration configuration;

    static {
        try {
            configuration = new XMLConfiguration("configuration.xml");
        }
        catch (ConfigurationException ce) {
            System.out.println("An error occurred when loading configuration");
            ce.printStackTrace();
        }
    }

    public static XMLConfiguration getConfiguration() {
        return configuration;
    }

    public static String getStringField(String field) {
        return configuration.getString(field);
    }

    public static boolean getBooleanField(String field) {
        return configuration.getBoolean(field);
    }
}
