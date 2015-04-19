package co.ismo.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.controller
 * Date: 06/04/2015
 * Project: ismo-fxml-client
 */
public class LoyaltyController implements Initializable {

    @FXML
    private TextField cardNumber;

    private BasketController basketController;

    public void setupParentController(BasketController basketController) {
        this.basketController = basketController;
    }

    private void setupEventListeners() {
        cardNumber.setOnKeyPressed((KeyEvent keyEvent) -> basketController.keyListener(keyEvent));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupEventListeners();
    }
}
