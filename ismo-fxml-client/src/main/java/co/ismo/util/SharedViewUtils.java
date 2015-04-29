package co.ismo.util;

import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Johnathan
 * Package: co.ismo.util
 * Date: 06/04/2015
 * Project: ismo-fxml-client
 */
public class SharedViewUtils {
    public static void consumeEvent(Event e, String caller) {
        if (!Constant.DEVELOPER_MODE) {
            e.consume();
        }
    }

    public static void fadeRoot(Stage stage, int timeInMillis, double start, double end) {
        FadeTransition ft = new FadeTransition(Duration.millis(timeInMillis), stage.getScene().getRoot());
        ft.setFromValue(start);
        ft.setToValue(end);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    public static Parent loadContent(URL fxmlURL) {
        try {
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            return loader.load();
        } catch (IOException ioe) {
            System.out.println("An IO Exception occured. Printing Stack Trace!");
            ioe.printStackTrace();
        }

        return null;
    }
}
