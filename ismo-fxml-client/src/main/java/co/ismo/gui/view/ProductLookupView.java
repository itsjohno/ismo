package co.ismo.gui.view;

import co.ismo.gui.controller.BasketController;
import co.ismo.gui.controller.ProductLookupController;
import co.ismo.gui.controller.TillController;
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

    public Parent getProductLookupView(TillController tillController, BasketController basketController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/till_productLookup.fxml"));

            Parent content = loader.load();

            ProductLookupController productLookupController = loader.getController();
            productLookupController.setupControllers(tillController, basketController);

            return content;

        } catch (IOException ioe) {
            System.out.println("An IO Exception occured. Printing Stack Trace!");
            ioe.printStackTrace();
        }

        return null;
    }

}
