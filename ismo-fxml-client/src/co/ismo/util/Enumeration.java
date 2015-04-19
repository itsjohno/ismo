package co.ismo.util;

/**
 * Created by Johnathan
 * Package: co.ismo.util
 * Date: 08/04/2015
 * Project: ismo-fxml-client
 */
public class Enumeration {

    public static enum UserLevel {
        Awaiting_Deletion(0),
        Operator(1),
        Supervisor(2),
        Deputy_Manager(3),
        Store_Manager(4),
        Operations_Manager(5);

        private final int value;
        private UserLevel(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
