package co.ismo.gui.view;

import co.ismo.gui.controller.ButtonController;
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
public class ButtonView {

    public ButtonView() {

    }

    public Parent loadButtonView(TillController parentController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../res/fxml/basket_btnPane_default.fxml"));

            Parent content = loader.load();

            ButtonController buttonController = loader.getController();
            buttonController.setupParentController(parentController);

            return content;

        } catch (IOException ioe) {
            System.out.println("An IO Exception occured. Printing Stack Trace!");
            ioe.printStackTrace();
        }

        return null;
    }

}
