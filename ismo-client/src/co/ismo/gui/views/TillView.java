package co.ismo.gui.views;

import co.ismo.objects.Operator;
import co.ismo.util.Constants;
import co.ismo.util.Enumerations;
import co.ismo.util.SharedViewUtils;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class TillView {

    public static void showTillView(Stage parentStage, Operator currentOperator) {

        Stage tillStage = new Stage();
        tillStage.initOwner(parentStage);
        tillStage.setOnCloseRequest((WindowEvent event) -> LoginView.showLoginView(parentStage, Enumerations.UserLevel.Operator, false));

        setStyling(tillStage, parentStage.getHeight(), parentStage.getWidth());
        setScene(parentStage, tillStage);
        tillStage.show();
    }

    private static void setStyling(Stage tillStage, double parentHeight, double parentWidth) {

        tillStage.initStyle(StageStyle.TRANSPARENT);
        tillStage.initModality(Modality.APPLICATION_MODAL);
        tillStage.setTitle("ismo EPOS - " + Constants.VERSION_NUMBER);

        tillStage.setWidth(parentWidth);
        tillStage.setHeight(parentHeight);
        tillStage.setX(0);
        tillStage.setY(0);

        tillStage.setResizable(false);
    }

    private static void setScene(Stage parentStage, Stage tillStage) {

        Group tillGroup = new Group();
        Scene tillScene = new Scene(tillGroup, Color.TRANSPARENT);

        GridPane tillGrid = new GridPane();
        tillGrid.setMinWidth(tillStage.getWidth() * 0.95);
        tillGrid.setMinHeight(tillStage.getHeight() * 0.95);
        tillGrid.setLayoutX(tillStage.getWidth() * 0.025);
        tillGrid.setLayoutY(tillStage.getHeight() * 0.025);
        tillGrid.setAlignment(Pos.CENTER);
        tillGrid.getStyleClass().add("backgroundRect");

        tillGroup.getChildren().add(tillGrid);
        tillStage.setScene(tillScene);

        SharedViewUtils.fadeNode(tillGrid, 500, 0, 1);
    }

}
