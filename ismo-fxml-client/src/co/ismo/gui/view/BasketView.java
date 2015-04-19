package co.ismo.gui.view;

import co.ismo.gui.controller.BasketController;
import co.ismo.gui.controller.TillController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.view
 * Date: 14/04/2015
 * Project: ismo-fxml-client
 */
public class BasketView {

    private Parent basketView;
    private BasketController basketController;

    public BasketView() {
        basketView = null;
        basketController = null;
    }

    public void loadBasketView(TillController parentController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../res/fxml/till_basket.fxml"));

            basketView = loader.load();
            basketController = loader.getController();
            basketController.setupParentController(parentController);

        } catch (IOException ioe) {
            System.out.println("An IO Exception occured. Printing Stack Trace!");
            ioe.printStackTrace();
        }
    }

    public Parent getBasketView() {
        return basketView;
    }

    public BasketController getBasketController() {
        return basketController;
    }
}
