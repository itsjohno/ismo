package co.ismo.util;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextField;
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

    public static void consumeEvent(Event e, String caller) {
        if (!Constants.DEVELOPER_MODE) {
            System.out.println("Consumed event of type " + e.getEventType() + " from " + caller);
            e.consume();
        }
        else {
            System.out.println("Event of type " + e.getEventType() + " from " + caller + " occurred");
        }
    }

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }
}

