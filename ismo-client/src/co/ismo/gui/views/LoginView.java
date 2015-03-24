package co.ismo.gui.views;

import co.ismo.gui.controllers.LoginController;
import co.ismo.util.Constants;
import co.ismo.util.Enumerations;
import co.ismo.util.SharedViewUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Group;

public class LoginView {

    public static void getLoginDialog(Stage stage, Enumerations.UserLevel levelRequired) {
        Group loginViewGroup = SharedViewUtils.getDefaultStyledGroup(stage);
        Scene scene = new Scene(loginViewGroup);
        stage.setScene(scene);

        Group loginDialog = createDialog(levelRequired, stage.getWidth(), stage.getHeight());
        loginViewGroup.getChildren().add(loginDialog);

        SharedViewUtils.fadeNode(loginDialog, 1000, 0, 1);
        stage.show();
    }

    private static Group createDialog(Enumerations.UserLevel levelRequired, double width, double height) {
        Group loginDialogGroup = new Group();

        double dialogWidth = (width * 0.4);
        double dialogHeight = (height * 0.4);

        GridPane loginGrid = new GridPane();
        loginGrid.setMinWidth(dialogWidth);
        loginGrid.setMinHeight(dialogHeight);
        loginGrid.setLayoutX(width / 2 - dialogWidth / 2);
        loginGrid.setLayoutY(height / 2 - dialogHeight / 2);
        loginGrid.setPadding(new Insets(dialogHeight * 0.05, dialogWidth * 0.05, dialogHeight * 0.05, dialogWidth * 0.05));
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.getStyleClass().add("loginRect");

        PasswordField loginPasswordField = new PasswordField();

        Label tanLabel = new Label("Please enter TAN at " + levelRequired + " level or higher");
        tanLabel.setAlignment(Pos.CENTER);
        tanLabel.setLabelFor(loginPasswordField);
        loginGrid.add(tanLabel, 0, 0);

        loginPasswordField.setPromptText("TAN (Teller Authorisation Number)");
        loginPasswordField.setMinWidth(dialogWidth * 0.9);
        loginPasswordField.setAlignment(Pos.CENTER);
        loginPasswordField.getStyleClass().add("loginBox");
        loginPasswordField.setOnKeyPressed((KeyEvent kE) -> {
            if (loginPasswordField.getLength() + 1 > Constants.TAN_LENGTH) {
                loginPasswordField.setText(loginPasswordField.getText().substring(0, Constants.TAN_LENGTH));
                loginPasswordField.positionCaret(Constants.TAN_LENGTH);
            }
        });

        loginPasswordField.setOnAction((ActionEvent aE) -> { LoginController.attemptLogin(loginPasswordField.getText()); });

        loginGrid.add(loginPasswordField, 0, 1);

        loginDialogGroup.getChildren().add(loginGrid);

        return loginDialogGroup;
    }


}
