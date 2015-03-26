package co.ismo.core;

import co.ismo.gui.views.LoginView;
import co.ismo.gui.views.TillView;
import co.ismo.objects.Operator;
import co.ismo.util.Constants;

import co.ismo.util.Enumerations;
import co.ismo.util.SharedViewUtils;
import com.sun.javafx.css.StyleManager;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application {

    public void setStyling(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);

        Application.setUserAgentStylesheet(null);
        StyleManager.getInstance().addUserAgentStylesheet("/co/ismo/res/css/main.css"); // Attaches this stylesheet to all scenes

        Scene scene = new Scene(SharedViewUtils.getDefaultStyledGroup(primaryStage));
        primaryStage.setScene(scene);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Rectangle2D primaryDisplay = Screen.getPrimary().getBounds();
        primaryStage.setX(primaryDisplay.getMinX());
        primaryStage.setY(primaryDisplay.getMinY());
        primaryStage.setHeight(primaryDisplay.getHeight());
        primaryStage.setWidth(primaryDisplay.getWidth());
        primaryStage.setOnCloseRequest((WindowEvent event) -> SharedViewUtils.consumeEvent(event, "primaryStage"));

        setStyling(primaryStage);
        primaryStage.show();

        boolean loginCompleted = true;

        if (!Constants.SINGLE_USER_TERMINAL) {
            LoginView.showLoginView(primaryStage, Enumerations.UserLevel.Operator, false);
        } else {
            TillView.showTillView(primaryStage, new Operator());
        }
    }

    @Override
    public void stop() throws Exception {
        System.out.println("STOP called"); // This is where we'll need to ensure a flush of the cache to persistence.
    }

    public static void main(String[] args) {
        launch(args);
    }
}
