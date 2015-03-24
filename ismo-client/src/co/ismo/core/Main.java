package co.ismo.core;

import co.ismo.gui.views.LoginView;
import co.ismo.util.Constants;

import co.ismo.util.Enumerations;
import com.sun.javafx.css.StyleManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Rectangle2D primaryDisplay = Screen.getPrimary().getBounds();
        stage.setX(primaryDisplay.getMinX());
        stage.setY(primaryDisplay.getMinY());
        stage.setHeight(primaryDisplay.getHeight());
        stage.setWidth(primaryDisplay.getWidth());

        stage.setFullScreen(true);
        stage.setAlwaysOnTop(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH); // Disables exiting full screen mode.
        stage.setFullScreenExitHint(""); // Disables full screen exit hint

        Application.setUserAgentStylesheet(null);
        StyleManager.getInstance().addUserAgentStylesheet("/co/ismo/res/css/main.css"); // Attaches this stylesheet to all scenes

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (!Constants.DEVELOPER_MODE) {
                    event.consume();
                }
                System.out.println("Window Close event consumed");
            }
        });

        if (!Constants.SINGLE_USER_TERMINAL) {
            LoginView mainLogin = new LoginView(stage, Enumerations.UserLevel.Operator);
        }
    }

    @Override
    public void stop() throws Exception {
        System.out.println("STOP called");
        // This is where we'll need to ensure a flush of the cache to persistence.
    }

    public static void main(String[] args) {
        launch(args);
    }
}
