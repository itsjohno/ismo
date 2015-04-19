package co.ismo.gui.controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.controller
 * Date: 06/04/2015
 * Project: ismo-fxml-client
 */
public class ProductLookupController implements Initializable {

    private BasketController basketController;

    public void setupBasketController(BasketController basketController) {
        this.basketController = basketController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
