package co.ismo.core;

import co.ismo.gui.controller.RootController;
import co.ismo.gui.view.LoginView;
import co.ismo.gui.view.TillView;
import co.ismo.object.type.Operator;
import co.ismo.object.util.AgeRatingUtility;
import co.ismo.object.util.CategoryUtility;
import co.ismo.object.util.OtherUtility;
import co.ismo.util.Constant;
import co.ismo.util.DynamicHashMap;
import co.ismo.util.SharedViewUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setHashMaps();
        setStyling(primaryStage);
        setScene(primaryStage);
        primaryStage.show();

        if (Constant.SINGLE_USER_TERMINAL) {
            new TillView(primaryStage, new Operator());
        } else {
            new LoginView(primaryStage, 1, false);
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


        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/main.fxml"));
        Scene scene = new Scene(loader.load(), Color.TRANSPARENT);

        RootController rootController = loader.getController();
        rootController.setBackgroundSize(primaryStage.getWidth(), primaryStage.getHeight());

        scene.getStylesheets().add("/css/main.css");
        primaryStage.setScene(scene);
    }

    private void setHashMaps() {
        DynamicHashMap.setAgeRatings(new AgeRatingUtility().getAgeRatings());
        DynamicHashMap.setSubCategories(new CategoryUtility().getCategories());

        OtherUtility ou = new OtherUtility();
        DynamicHashMap.setSuperCategories(ou.getSuperCategories());
        DynamicHashMap.setUserLevels(ou.getUserLevels());
        ou = null;
    }

    @Override
    public void stop() throws Exception {
        System.out.println("STOP called"); // This is where we'll need to ensure a flush of the cache to persistence.
    }
}
