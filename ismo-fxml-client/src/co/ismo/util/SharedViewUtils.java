package co.ismo.util;

import javafx.event.Event;

/**
 * Created by Johnathan
 * Package: co.ismo.util
 * Date: 06/04/2015
 * Project: ismo-fxml-client
 */
public class SharedViewUtils {
    public static void consumeEvent(Event e, String caller) {
        if (!Constant.DEVELOPER_MODE) {
            System.out.println("Consumed event of type " + e.getEventType() + " from " + caller);
            e.consume();
        }
        else {
            System.out.println("Event of type " + e.getEventType() + " from " + caller + " occurred");
        }
    }
}
