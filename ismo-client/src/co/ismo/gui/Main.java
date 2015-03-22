package co.ismo.gui;

import co.ismo.util.Constants;
import co.ismo.util.ObjectUtils;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {

    private void setupDisplay(Stage stage) throws IOException {

        Group group = new Group();
        Scene scene = new Scene(group);
        scene.getStylesheets().add("/co/ismo/res/css/main.css");
        stage.setScene(scene);

        Rectangle2D primaryDisplay = Screen.getPrimary().getBounds();
        Rectangle backgroundRect = new Rectangle(primaryDisplay.getWidth(), primaryDisplay.getHeight());
        backgroundRect.getStyleClass().add("backgroundRect");
        group.getChildren().add(backgroundRect);

        VBox loginBox = new VBox(15);
        loginBox.setPadding(new Insets(25));
        loginBox.setSpacing(50);
        loginBox.setMinWidth((primaryDisplay.getWidth() * 0.4));
        loginBox.setMinHeight((primaryDisplay.getHeight() * 0.4));
        loginBox.setLayoutX(primaryDisplay.getWidth() / 2 - loginBox.getMinWidth() / 2);
        loginBox.setLayoutY(primaryDisplay.getHeight() / 2 - loginBox.getMinHeight() / 2);
        loginBox.getStyleClass().add("loginRect");

        StackPane img = new StackPane();
        Image logo = new Image("/co/ismo/res/img/game-logo.png");
        ImageView iv = new ImageView();
        iv.setImage(logo);
        iv.setFitWidth(loginBox.getMinWidth() * 0.5);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);

        img.setPadding(new Insets(15, 0, 0, 0));
        img.setPrefSize(loginBox.getMinWidth(), iv.getFitHeight());
        img.setAlignment(Pos.CENTER);
        img.getChildren().add(iv);
        loginBox.getChildren().add(img);

        PasswordField loginPasswordField = new PasswordField();
        loginPasswordField.setPromptText("TAN (Teller Authorisation Number)");
        loginPasswordField.setLayoutX(primaryDisplay.getWidth() / 2 - loginBox.getMinWidth() / 2 + loginBox.getMinWidth() * 0.05);
        loginPasswordField.setLayoutY(primaryDisplay.getHeight() / 2 - loginBox.getMinHeight() / 2);
        loginPasswordField.setMinWidth(loginBox.getMinWidth() * 0.9);
        loginPasswordField.getStyleClass().add("loginBox");
        loginPasswordField.setOnKeyPressed((KeyEvent kE) -> {
            if (loginPasswordField.getLength() + 1 > Constants.TAN_LENGTH) {
                loginPasswordField.setText(loginPasswordField.getText().substring(0, Constants.TAN_LENGTH - 1));
                loginPasswordField.positionCaret(Constants.TAN_LENGTH - 1);
            }
        });

        loginBox.getChildren().add(loginPasswordField);

        ObjectUtils.fade(loginBox, 1000, 0, 1);

        group.getChildren().addAll(loginBox);

        stage.setX(primaryDisplay.getMinX());
        stage.setY(primaryDisplay.getMinY());
        stage.setHeight(primaryDisplay.getHeight());
        stage.setWidth(primaryDisplay.getWidth());

        stage.setFullScreen(true);
        stage.setAlwaysOnTop(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH); // Disables exiting full screen mode.
        stage.setFullScreenExitHint(""); // Disables full screen exit hint

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (!Constants.DEVELOPER_MODE) {
                    event.consume();
                }
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
