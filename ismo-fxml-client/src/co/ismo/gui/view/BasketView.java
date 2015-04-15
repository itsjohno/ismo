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

    public BasketView() {

    }

    public Parent loadBasketView(TillController parentController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../res/fxml/till_basket.fxml"));

            Parent content = loader.load();

            BasketController basketController = loader.getController();
            basketController.setupParentController(parentController);

            return content;

        } catch (IOException ioe) {
            System.out.println("An IO Exception occured. Printing Stack Trace!");
            ioe.printStackTrace();
        }

        return null;
    }
}
