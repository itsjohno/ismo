package co.ismo.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Johnathan
 * Package: co.ismo.core
 * Date: 16/04/2015
 * Project: ismo-fxml-client
 */
public class CoreUtility {

    public static String encodeString(String unhashed) {
        String hashed = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            hashed = byteArrayToHexString(md.digest(unhashed.getBytes()));
        } catch (NoSuchAlgorithmException nsae) {
            System.out.println("An exception occurred! Specified algorithm does not exist");
        }

        return hashed;
    }

    // http://rgagnon.com/javadetails/java-0596.html
    public static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result +=
                    Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
}
