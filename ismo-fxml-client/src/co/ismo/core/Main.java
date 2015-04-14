package co.ismo.core;

import co.ismo.gui.controller.RootController;
import co.ismo.gui.view.LoginView;
import co.ismo.gui.view.TillView;
import co.ismo.objects.Operator;
import co.ismo.util.Constant;
import co.ismo.util.Enumeration;
import co.ismo.util.SharedViewUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Johnathan
 * Package: co.ismo.core
 * Date: 06/04/2015
 * Project: ismo-fxml-client
 */

public class Main extends Application {

    static {
        Font.loadFont(Main.class.getResource("../gui/res/font/fontawesome-webfont.ttf").toExternalForm(), 10);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setStyling(primaryStage);
        setScene(primaryStage);
        primaryStage.show();

        setupTestUsers();

        if (Constant.SINGLE_USER_TERMINAL) {
            new TillView(primaryStage, new Operator());
        } else {
            new LoginView(primaryStage, Enumeration.UserLevel.Operator, false);
        }
    }


    private void setStyling(Stage primaryStage) {
        Rectangle2D primaryDisplay = Screen.getPrimary().getBounds();

        primaryStage.setX(primaryDisplay.getMinX());
        primaryStage.setY(primaryDisplay.getMinY());
        primaryStage.setHeight(primaryDisplay.getHeight());
        primaryStage.setWidth(primaryDisplay.getWidth());
        primaryStage.setOnCloseRequest((WindowEvent event) -> SharedViewUtils.consumeEvent(event, "Main"));
    }

    private void setScene(Stage primaryStage) throws IOException {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("ismo EPOS");
        primaryStage.setResizable(false);

        FXMLLoader loader =  new FXMLLoader(getClass().getResource("../gui/res/fxml/main.fxml"));
        Scene scene = new Scene(loader.load(), Color.TRANSPARENT);

        RootController rootController = loader.getController();
        rootController.setBackgroundSize(primaryStage.getWidth(), primaryStage.getHeight());

        scene.getStylesheets().add("/co/ismo/gui/res/css/main.css");
        primaryStage.setScene(scene);
    }

    public void setupTestUsers() {
        Constant.operators.add(new Operator("James", "Smith", "12345", 12345, 0));
        Constant.operators.add(new Operator("John", "Smith", "11111", 11111, 1));
        Constant.operators.add(new Operator("Steven", "Smith", "22222", 22222, 2));
        Constant.operators.add(new Operator("Bob", "Smith", "33333", 333333, 3));
        Constant.operators.add(new Operator("Jacob", "Smith", "44444", 444444, 4));
    }


    @Override
    public void stop() throws Exception {
        System.out.println("STOP called"); // This is where we'll need to ensure a flush of the cache to persistence.
    }

    public static void main(String[] args) {
        launch(args);
    }
}
