package co.ismo.util;

import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SharedViewUtils {

    public static Group getDefaultStyledGroup(Stage stage) {
        Group group = new Group();

        Rectangle backgroundRect = new Rectangle(stage.getWidth(), stage.getHeight());
        backgroundRect.getStyleClass().add("displayBackground");

        group.getChildren().add(backgroundRect);

        return group;
    }

    public static void fadeNode(Node node, int timeInMillis, double start, double end) {
        FadeTransition ft = new FadeTransition(Duration.millis(timeInMillis), node);
        ft.setFromValue(start);
        ft.setToValue(end);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
}

