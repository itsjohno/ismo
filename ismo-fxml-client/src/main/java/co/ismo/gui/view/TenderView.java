package co.ismo.gui.view;

import co.ismo.gui.controller.TenderController;
import co.ismo.gui.controller.TillController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.view
 * Date: 01/05/2015
 * Project: ismo-fxml-client
 */
public class TenderView {

    private TenderController tenderController;

    public TenderView() {
        tenderController = null;
    }

    public Parent getTenderView(TillController tillController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/till_tender.fxml"));

            Parent content = loader.load();

            tenderController = loader.getController();
            tenderController.setupTillController(tillController);

            return content;

        } catch (IOException ioe) {
            System.out.println("An IO Exception occured. Printing Stack Trace!");
            ioe.printStackTrace();
        }

        return null;
    }

    public TenderController getController() {
        return tenderController;
    }

}
