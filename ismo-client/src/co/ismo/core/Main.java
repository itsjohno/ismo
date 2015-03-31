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
        primaryStage.setTitle("ismo EPOS");
        primaryStage.setResizable(false);

        Scene scene = SharedViewUtils.getDefaultStyledScene(SharedViewUtils.getDefaultStyledGroup(primaryStage));
        primaryStage.setScene(scene);
    }

    public void setupTestUsers() {
        Constants.operators.add(new Operator("James", "Smith", "12345", 12345));
        Constants.operators.add(new Operator("John", "Smith", "11111", 11111));
        Constants.operators.add(new Operator("Steven", "Smith", "22222", 22222));
        Constants.operators.add(new Operator("Bob", "Smith", "33333", 333333));
        Constants.operators.add(new Operator("Jacob", "Smith", "44444", 444444));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Rectangle2D primaryDisplay = Screen.getPrimary().getBounds();
        // Rectangle2D primaryDisplay = Screen.getPrimary().getVisualBounds();

        primaryStage.setX(primaryDisplay.getMinX());
        primaryStage.setY(primaryDisplay.getMinY());
        primaryStage.setHeight(primaryDisplay.getHeight());
        primaryStage.setWidth(primaryDisplay.getWidth());
        primaryStage.setOnCloseRequest((WindowEvent event) -> SharedViewUtils.consumeEvent(event, "primaryStage"));

        System.out.println("X - " + primaryDisplay.getMinX());
        System.out.println("Y - " + primaryDisplay.getMinY());
        System.out.println("W - " + primaryDisplay.getHeight());
        System.out.println("H - " + primaryDisplay.getWidth());

        setStyling(primaryStage);
        primaryStage.show();
        setupTestUsers();

        if (!Constants.SINGLE_USER_TERMINAL) {
            new LoginView(primaryStage, Enumerations.UserLevel.Operator, false);
        } else {
            new TillView(primaryStage, new Operator());
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
