package co.ismo.gui.views;

import co.ismo.gui.controllers.TillController;
import co.ismo.objects.Operator;
import co.ismo.util.Constants;
import co.ismo.util.Enumerations;
import co.ismo.util.SharedViewUtils;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
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
        tillGrid.getStyleClass().add("borderRect");

        tillGrid.setHgap(10);
        tillGrid.setVgap(10);

        ColumnConstraints leftColumnConstraint = new ColumnConstraints();
        leftColumnConstraint.setPercentWidth(70);
        ColumnConstraints rightColumnConstraint = new ColumnConstraints();
        rightColumnConstraint.setPercentWidth(30);
        tillGrid.getColumnConstraints().addAll(leftColumnConstraint, rightColumnConstraint);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(10);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(80);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(10);
        tillGrid.getRowConstraints().addAll(row1,row2,row3);

        Pane p = new Pane();
        p.getChildren().addAll(new Text("0,0"));
        //hbox00.getStyleClass().add("backgroundWhite");
        tillGrid.add(p, 0, 0);

        HBox hbox10 = new HBox();
        hbox10.getChildren().addAll(new Text("1,0"));
        hbox10.getStyleClass().add("backgroundWhite");
        tillGrid.add(hbox10, 1, 0);

        HBox hbox01 = new HBox();
        hbox01.getChildren().addAll(new Text("0,1"));
        hbox01.getStyleClass().add("backgroundWhite");
        tillGrid.add(hbox01, 0, 1);

        HBox hbox11 = new HBox();
        hbox11.getChildren().addAll(new Text("1,1"));
        hbox11.getStyleClass().add("backgroundWhite");
        tillGrid.add(hbox11, 1, 1);

        HBox hbox02 = new HBox();
        hbox02.getChildren().addAll(new Text("0,2"));
        hbox02.getStyleClass().add("backgroundWhite");
        tillGrid.add(hbox02, 0, 2);

        HBox hbox12 = new HBox();
        hbox12.getChildren().addAll(new Text("1,2"));
        hbox12.getStyleClass().add("backgroundWhite");
        tillGrid.add(hbox12, 1, 2);

        tillGrid.setGridLinesVisible(true);
        tillGroup.getChildren().add(tillGrid);
        tillStage.setScene(tillScene);

        //SharedViewUtils.fadeNode(tillGrid, 500, 0, 1);
    }

}
