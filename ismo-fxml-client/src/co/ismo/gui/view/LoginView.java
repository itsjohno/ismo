package co.ismo.gui.view;

import co.ismo.gui.controller.LoginController;
import co.ismo.util.SharedViewUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.view
 * Date: 06/04/2015
 * Project: ismo-fxml-client
 */
public class LoginView {

    public LoginView(Stage stage, int requiredUserLevel, boolean closable) {

        Stage loginStage = new Stage();
        loginStage.initOwner(stage);
        loginStage.setTitle("Login Required");

        setScene(loginStage, requiredUserLevel);
        setStyling(loginStage, stage.getWidth(), closable);
        loginStage.show();

        // SharedViewUtils.fadeRoot(loginStage, 500, 0, 1);
    }

    private void setStyling(Stage loginStage, double parentWidth, boolean closable) {
        loginStage.initStyle(StageStyle.TRANSPARENT);
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.setOnCloseRequest((WindowEvent event) -> {
            if (!closable) {
                SharedViewUtils.consumeEvent(event, "LoginView");
            }
        });

        loginStage.setWidth(parentWidth * 0.4);
        loginStage.setResizable(false);
    }

    private void setScene(Stage loginStage, int requiredUserLevel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../res/fxml/login.fxml"));
            Scene scene = new Scene(loader.load(), Color.TRANSPARENT);

            LoginController loginController = loader.getController();
            loginController.setRequiredUserLevel(requiredUserLevel);

            scene.getStylesheets().add("/co/ismo/gui/res/css/login.css");
            loginStage.setScene(scene);
        } catch (IOException ioe) {
            System.out.println("An IO Exception occured. Printing Stack Trace!");
            ioe.printStackTrace();
        }
    }
}
