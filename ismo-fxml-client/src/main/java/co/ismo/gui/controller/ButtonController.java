package co.ismo.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.controller
 * Date: 13/04/2015
 * Project: ismo-fxml-client
 */
public class ButtonController implements Initializable {

    @FXML
    private Button suspendButton;

    @FXML
    private Button resumeButton;

    // Parent Controllers
    private TillController tillController;
    private BasketController basketController;

    public void setupParentControllers(TillController tillController, BasketController basketController) {
        this.tillController = tillController;
        this.basketController = basketController;
    }

    @FXML
    private void notImplemented() {
        basketController.notImplemented();
    }

    @FXML
    private void productLookup(ActionEvent e) {
        tillController.loadProductLookupView();
    }

    @FXML
    private void webBrowser(ActionEvent e) {
        tillController.loadWebBrowserView();
    }

    @FXML
    private void voidItem(ActionEvent e) {
        basketController.voidItem();
    }

    @FXML
    private void suspendTransaction(ActionEvent e) {
        tillController.suspendTransaction();
    }

    @FXML
    private void resumeTransaction(ActionEvent e) {
        //basketController.voidItem();
    }

    @FXML
    private void suspendTransaction() {
        if (!suspendButton.isDisable()) {
            tillController.suspendTransaction();
        }
    }

    public void toggleSuspendButton(boolean disable) {
        suspendButton.setDisable(disable);
    }

    public void toggleResumeButton(boolean disable) {
        resumeButton.setDisable(disable);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert suspendButton != null : "fx:id=\"suspendButton\" was not injected: check your FXML file 'basket_btnPane_default.fxml'.";
        assert resumeButton != null : "fx:id=\"resumeButton\" was not injected: check your FXML file 'basket_btnPane_default.fxml'.";
    }
}
