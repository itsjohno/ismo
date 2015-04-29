package co.ismo.gui.view;

import co.ismo.gui.controller.TillController;
import co.ismo.object.type.Operator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.view
 * Date: 08/04/2015
 * Project: ismo-fxml-client
 */
public class TillView {

    public TillView(Stage parentStage, Operator currentOperator) {

        Stage tillStage = new Stage();
        tillStage.initOwner(parentStage);
        tillStage.setOnCloseRequest((WindowEvent event) -> {
            tillStage.close();
            new LoginView(parentStage, 1, false);
        });

        setStyling(tillStage, parentStage.getHeight(), parentStage.getWidth());
        setScene(tillStage, currentOperator);
        tillStage.show();

        // SharedViewUtils.fadeRoot(tillStage, 500, 0, 1);
    }

    private void setStyling(Stage tillStage, double parentHeight, double parentWidth) {
        tillStage.initStyle(StageStyle.TRANSPARENT);
        tillStage.initModality(Modality.APPLICATION_MODAL);

        tillStage.setWidth(parentWidth);
        tillStage.setHeight(parentHeight);
        tillStage.setX(0);
        tillStage.setY(0);

        tillStage.setResizable(false);
    }

    private void setScene(Stage tillStage, Operator currentOperator) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/till.fxml"));
            Scene scene = new Scene(loader.load(), Color.TRANSPARENT);

            TillController tillController = loader.getController();
            tillController.setCurrentOperator(currentOperator);

            scene.getStylesheets().add("/css/till.css");
            tillStage.setScene(scene);
        } catch (IOException ioe) {
            System.out.println("An IO Exception occured. Printing Stack Trace!");
            ioe.printStackTrace();
        }
    }
}
