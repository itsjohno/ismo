package co.ismo.gui.views;

import co.ismo.gui.controllers.LoginController;
import co.ismo.gui.controllers.TillController;
import co.ismo.objects.Operator;
import co.ismo.util.Constants;
import co.ismo.util.Enumerations;
import co.ismo.util.SharedViewUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class TillView {

    private final TillController tillController;

    public TillView(Stage parentStage, Operator currentOperator) {

        Stage tillStage = new Stage();
        tillController = new TillController();

        tillStage.initOwner(parentStage);
        tillStage.setOnCloseRequest((WindowEvent event) -> {
            System.out.println("Logged out user " + currentOperator.getForename() + " " + currentOperator.getSurname());
            new LoginView(parentStage, Enumerations.UserLevel.Operator, false);
        });

        setStyling(tillStage, parentStage.getHeight(), parentStage.getWidth());
        setScene(parentStage, tillStage);
        tillStage.show();
    }

    private void setStyling(Stage tillStage, double parentHeight, double parentWidth) {
        tillStage.initStyle(StageStyle.TRANSPARENT);
        tillStage.initModality(Modality.APPLICATION_MODAL);
        tillStage.setTitle("ismo EPOS - " + Constants.VERSION_NUMBER);

        tillStage.setWidth(parentWidth);
        tillStage.setHeight(parentHeight);
        tillStage.setX(0);
        tillStage.setY(0);

        tillStage.setResizable(false);
    }

    private void setScene(Stage parentStage, Stage tillStage) {

        Group tillGroup = new Group();
        Scene tillScene = SharedViewUtils.getDefaultStyledScene(tillGroup);

        GridPane tillGrid = new GridPane();
        tillGrid.setMinWidth(tillStage.getWidth() * 0.95);
        tillGrid.setMinHeight(tillStage.getHeight() * 0.95);
        tillGrid.setLayoutX(tillStage.getWidth() * 0.025);
        tillGrid.setLayoutY(tillStage.getHeight() * 0.025);
        tillGrid.setAlignment(Pos.CENTER);
        tillGrid.getStyleClass().add("backgroundRect");

        TextField skuField = new TextField();

        Label tanLabel = new Label("Enter SKU/Product ID");
        tanLabel.getStyleClass().add("skuFieldLabel");
        tanLabel.setLabelFor(skuField);
        tillGrid.add(tanLabel, 0, 0);

        skuField.setPromptText("SKU/Product ID");
        skuField.setMinWidth(tillStage.getWidth() * 0.9);
        skuField.setAlignment(Pos.CENTER);
        skuField.getStyleClass().add("skuField");

        skuField.setOnAction((ActionEvent aE) -> {
            tillController.addItem(skuField.getText());
            skuField.setText("");
        });

        tillGrid.add(skuField, 0, 1);

        tillGroup.getChildren().add(tillGrid);
        tillStage.setScene(tillScene);

        SharedViewUtils.fadeNode(tillGrid, 500, 0, 1);
    }

}
