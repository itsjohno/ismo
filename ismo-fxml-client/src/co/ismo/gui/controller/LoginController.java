package co.ismo.gui.controller;

import co.ismo.gui.view.TillView;
import co.ismo.objects.Operator;
import co.ismo.util.Constant;
import co.ismo.util.Enumeration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.controller
 * Date: 06/04/2015
 * Project: ismo-fxml-client
 */
public class LoginController implements Initializable {

    @FXML
    private VBox loginVBox;

    @FXML
    private PasswordField tanBox;

    @FXML
    private Label tanBoxLabel;

    private Enumeration.UserLevel requiredUserLevel;

    @FXML
    private void tanBoxOnAction(ActionEvent e) {
        Operator loggedInOperator = attemptLogin(tanBox.getText());

        if (loggedInOperator != null) {
            if (loggedInOperator.getUserLevel() >= requiredUserLevel.getValue()) {
                Node s = (Node) e.getSource();
                Stage currentStage = (Stage) s.getScene().getWindow();
                Stage parentStage = (Stage) currentStage.getOwner();

                currentStage.close();
                new TillView(parentStage, loggedInOperator);
            } else {
                tanBox.setText("");
                tanBox.setPromptText("Insufficient User Privileges");
                tanBox.getStyleClass().add("error_textField");
            }
        } else {
            tanBox.setText("");
            tanBox.setPromptText("Invalid TAN");
            tanBox.getStyleClass().add("error_textField");
        }
    }

    private Operator attemptLogin(String input) {
        for (Operator o : Constant.operators) {
            if (input.equalsIgnoreCase(o.getEncodedTAN())) {
                return o;
            }
        }
        // TODO: Log attempted login with timestamp and entered TAN?
        return null;
    }

    public void setRequiredUserLevel(Enumeration.UserLevel requiredUserLevel) {
        this.requiredUserLevel = requiredUserLevel;
        this.tanBoxLabel.setText("Enter TAN at " + requiredUserLevel.toString().replace("_", " ") + " level or higher");
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert loginVBox != null : "fx:id=\"loginVBox\" was not injected: check your FXML file 'login.fxml'.";
        assert tanBox != null : "fx:id=\"tanBox\" was not injected: check your FXML file 'login.fxml'.";

        tanBox.textProperty().addListener((observable, oldValue, newValue) -> {
            int lastElement = tanBox.getStyleClass().size() - 1;

            if (newValue.length() > Constant.TAN_LENGTH) {
                tanBox.setText(oldValue);
            }

            if (tanBox.getStyleClass().get(lastElement).equalsIgnoreCase("error_TextField")) {
                tanBox.setPromptText("Scan/Type TAN");
                tanBox.getStyleClass().remove(lastElement);
            }
        });
    }
}