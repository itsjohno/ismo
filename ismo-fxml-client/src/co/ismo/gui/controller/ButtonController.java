package co.ismo.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.controller
 * Date: 13/04/2015
 * Project: ismo-fxml-client
 */
public class ButtonController implements Initializable {

    // Parent Controller
    private TillController tillController;

    public void setupParentController(TillController tillController) {
        this.tillController = tillController;
    }

    @FXML
    private void notImplemented() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Not Implemented");
        alert.setHeaderText(null);
        alert.setContentText("This button is not currently implemented!");

        alert.showAndWait();
    }

    @FXML
    private void productLookup(ActionEvent e) {
        tillController.loadProductLookupView();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
