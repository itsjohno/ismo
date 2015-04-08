package co.ismo.gui.view;

import co.ismo.objects.Operator;
import co.ismo.util.Enumeration;
import co.ismo.util.SharedViewUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

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
        tillStage.setOnCloseRequest((WindowEvent event) -> new LoginView(parentStage, Enumeration.UserLevel.Operator, false));

        setStyling(tillStage, parentStage.getHeight(), parentStage.getWidth());
        setScene(parentStage, tillStage);
        tillStage.show();
    }

    private void setStyling(Stage tillStage, double parentHeight, double parentWidth) {
        tillStage.initStyle(StageStyle.TRANSPARENT);
        tillStage.initModality(Modality.APPLICATION_MODAL);
        //tillStage.setTitle("ismo EPOS - " + Constants.VERSION_NUMBER);

        tillStage.setWidth(parentWidth);
        tillStage.setHeight(parentHeight);
        tillStage.setX(0);
        tillStage.setY(0);

        tillStage.setResizable(false);
    }

    private void setScene(Stage parentStage, Stage tillStage) {

    }

}
