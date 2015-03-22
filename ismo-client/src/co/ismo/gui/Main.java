package co.ismo.gui;

import co.ismo.util.Constants;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.stage.WindowEvent;

public class Main extends Application {

    private Group group;

    private void setupDisplay(Stage stage) {
        group = new Group();
        Scene scene = new Scene(group);
        scene.getStylesheets().add("/co/ismo/res/css/main.css");
        stage.setScene(scene);

        Rectangle2D primaryDisplay = Screen.getPrimary().getBounds();
        Rectangle r = new Rectangle(primaryDisplay.getWidth(), primaryDisplay.getHeight());
        r.getStyleClass().add("rect");
        group.getChildren().add(r);

        stage.setX(primaryDisplay.getMinX());
        stage.setY(primaryDisplay.getMinY());
        stage.setHeight(primaryDisplay.getHeight());
        stage.setWidth(primaryDisplay.getWidth());

        stage.setFullScreen(true);
        stage.setAlwaysOnTop(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreenExitHint("");

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (!Constants.DEVELOPER_MODE) { event.consume(); }
                System.out.println("Window Close event consumed");
            }
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        setupDisplay(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
