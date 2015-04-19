package co.ismo.gui.view;

import co.ismo.gui.controller.BasketController;
import co.ismo.gui.controller.ProductLookupController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.view
 * Date: 19/04/2015
 * Project: ismo-fxml-client
 */
public class ProductLookupView {

    public ProductLookupView() {

    }

    public Parent getProductLookupView(BasketController basketController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../res/fxml/till_productLookup.fxml"));

            Parent content = loader.load();

            ProductLookupController productLookupController = loader.getController();
            productLookupController.setupBasketController(basketController);

            return content;

        } catch (IOException ioe) {
            System.out.println("An IO Exception occured. Printing Stack Trace!");
            ioe.printStackTrace();
        }

        return null;
    }

}
