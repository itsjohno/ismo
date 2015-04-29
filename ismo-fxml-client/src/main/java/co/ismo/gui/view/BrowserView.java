package co.ismo.gui.view;

import co.ismo.gui.controller.BrowserController;
import co.ismo.gui.controller.TillController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.view
 * Date: 21/04/2015
 * Project: ismo-fxml-client
 */
public class BrowserView {

    private BrowserController browserController;

    public BrowserView() {
        browserController = null;
    }

    public Parent getBrowserView(TillController tillController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/till_browser.fxml"));

            Parent content = loader.load();

            browserController = loader.getController();
            browserController.setupTillController(tillController);

            return content;

        } catch (IOException ioe) {
            System.out.println("An IO Exception occured. Printing Stack Trace!");
            ioe.printStackTrace();
        }

        return null;
    }

    public BrowserController getController() {
        return browserController;
    }

}
