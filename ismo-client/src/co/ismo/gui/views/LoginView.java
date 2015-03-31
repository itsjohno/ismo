package co.ismo.gui.views;

import co.ismo.gui.controllers.LoginController;
import co.ismo.objects.Operator;
import co.ismo.util.Constants;
import co.ismo.util.Enumerations;
import co.ismo.util.SharedViewUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class LoginView {

    private final double SCALE_FACTOR = 0.4;

    public LoginView(Stage parentStage, Enumerations.UserLevel requiredUserLevel, boolean closeable) {
        Stage loginDialogStage = new Stage();
        loginDialogStage.initOwner(parentStage);
        loginDialogStage.setTitle(requiredUserLevel + " Login Required");

        if (!closeable) {
            loginDialogStage.setOnCloseRequest((WindowEvent event) -> SharedViewUtils.consumeEvent(event, "loginDialogStage"));
        }

        setStyling(loginDialogStage, parentStage.getHeight(), parentStage.getWidth());
        setScene(parentStage, loginDialogStage, requiredUserLevel);
        loginDialogStage.show();
    }

    private void setStyling(Stage dialogStage, double parentHeight, double parentWidth) {

        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        double dialogWidth = parentWidth * SCALE_FACTOR;
        double dialogHeight = parentHeight * SCALE_FACTOR;

        dialogStage.setWidth(dialogWidth);
        dialogStage.setHeight(dialogHeight);
        dialogStage.setX(parentWidth / 2 - dialogWidth / 2);
        dialogStage.setY(parentHeight / 2 - dialogHeight / 2);

        dialogStage.setResizable(false);
    }

    private void setScene(Stage parentStage, Stage dialogStage, Enumerations.UserLevel requiredUserLevel) {

        Group loginGroup = new Group();
        Scene loginScene = SharedViewUtils.getDefaultStyledScene(loginGroup);

        GridPane loginGrid = new GridPane();
        loginGrid.setMinWidth(dialogStage.getWidth());
        loginGrid.setMinHeight(dialogStage.getHeight());
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.getStyleClass().add("backgroundRect");

        PasswordField loginPasswordField = new PasswordField();

        Label tanLabel = new Label("Please enter TAN at " + requiredUserLevel + " level or higher");
        tanLabel.getStyleClass().add("loginLabel");
        tanLabel.setLabelFor(loginPasswordField);
        loginGrid.add(tanLabel, 0, 0);

        loginPasswordField.setPromptText("Scan/Type TAN");
        loginPasswordField.setMinWidth(dialogStage.getWidth() * 0.9);
        loginPasswordField.setAlignment(Pos.CENTER);
        loginPasswordField.getStyleClass().add("loginBox");
        SharedViewUtils.addTextLimiter(loginPasswordField, Constants.TAN_LENGTH);

        loginPasswordField.setOnAction((ActionEvent aE) -> {
            Operator loggedInOperator = new LoginController().attemptLogin(loginPasswordField.getText());

            if (loggedInOperator != null) {
                dialogStage.close();
                new TillView(parentStage, loggedInOperator);
            } else {
                loginPasswordField.setText("");
                loginPasswordField.setPromptText("Invalid TAN");
                loginPasswordField.getStyleClass().add("error_textField");
            }
        });

        loginPasswordField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            int lastElement = loginPasswordField.getStyleClass().size() - 1;
            if (loginPasswordField.getStyleClass().get(lastElement).equalsIgnoreCase("error_TextField")) {
                loginPasswordField.setPromptText("Scan/Type TAN");
                loginPasswordField.getStyleClass().remove(lastElement);
            }
        });

        loginGrid.add(loginPasswordField, 0, 1);

        loginGroup.getChildren().add(loginGrid);
        dialogStage.setScene(loginScene);

        SharedViewUtils.fadeNode(loginGrid, 1000, 0, 1);
    }
}
