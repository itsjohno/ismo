package co.ismo.gui.view;

import co.ismo.gui.controller.BasketController;
import co.ismo.gui.controller.LoyaltyController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.view
 * Date: 19/04/2015
 * Project: ismo-fxml-client
 */
public class LoyaltyView {

    public LoyaltyView() {

    }

    public Parent loadLoyaltyView(BasketController parentController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../res/fxml/basket_customerPane.fxml"));

            Parent content = loader.load();

            LoyaltyController loyaltyController = loader.getController();
            loyaltyController.setupParentController(parentController);

            return content;

        } catch (IOException ioe) {
            System.out.println("An IO Exception occured. Printing Stack Trace!");
            ioe.printStackTrace();
        }

        return null;
    }
}
