package co.ismo.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.controller
 * Date: 13/04/2015
 * Project: ismo-fxml-client
 */
public class ButtonController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
