package co.ismo.gui.view;

import co.ismo.gui.controller.BasketController;
import co.ismo.gui.controller.ButtonController;
import co.ismo.gui.controller.TillController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.view
 * Date: 19/04/2015
 * Project: ismo-fxml-client
 */
public class ButtonView {

    private ButtonController btnController;

    public ButtonView() {

    }

    public Parent loadButtonView(TillController tillController, BasketController basketController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/basket_btnPane_default.fxml"));

            Parent content = loader.load();

            btnController = loader.getController();
            btnController.setupParentControllers(tillController, basketController);

            return content;

        } catch (IOException ioe) {
            System.out.println("An IO Exception occured. Printing Stack Trace!");
            ioe.printStackTrace();
        }

        return null;
    }

    public ButtonController getBtnController() {
        return btnController;
    }
}
