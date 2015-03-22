package co.ismo.util;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ObjectUtils {

    public static void fade(Node node, int timeInMillis, double start, double end) {
        FadeTransition ft = new FadeTransition(Duration.millis(timeInMillis), node);
        ft.setFromValue(start);
        ft.setToValue(end);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
}
