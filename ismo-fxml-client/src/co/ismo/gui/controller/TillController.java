package co.ismo.gui.controller;

import co.ismo.gui.view.TillView;
import co.ismo.objects.Operator;
import co.ismo.util.Enumeration;
import co.ismo.util.SharedViewUtils;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.controller
 * Date: 06/04/2015
 * Project: ismo-fxml-client
 */
public class TillController implements Initializable {

    private Operator currentOperator;

    @FXML
    private GridPane tillGrid;

    @FXML
    private Pane middlePane;

    @FXML
    private Text tillTitle;

    @FXML
    private Text dateTime;

    @FXML
    private Text userDetails;

    @FXML
    private Button logoutBtn;

    @FXML
    public void logoutUser(Event e) {
        currentOperator = null;

        Node s = (Node) e.getSource();
        Stage currentStage = (Stage) s.getScene().getWindow();
        currentStage.fireEvent(new WindowEvent(currentStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void setCurrentOperator(Operator operator) {
        this.currentOperator = operator;
        userDetails.setText(currentOperator.getForename() + " " + currentOperator.getSurname() + " (" + Enumeration.UserLevel.values()[currentOperator.getUserLevel()].toString().replace("_", " ") + ")");
    }

    private void bindDateTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), (ActionEvent actionEvent) -> {
                    Calendar time = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YY - HH:mm:ss");
                    dateTime.setText(simpleDateFormat.format(time.getTime()));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void loadBasketView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../res/fxml/till_basket.fxml"));
            middlePane.getChildren().add(loader.load());
            //TODO: Might need to persist this content, as if the new content (i.e. web browser) is lost into the entire middlePane, we'll lose the data to garbage collection.

            BasketController basketController = loader.getController();
            basketController.setupParentController(this);

        } catch (IOException ioe) {
            System.out.println("An IO Exception occured. Printing Stack Trace!");
            ioe.printStackTrace();
        }
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert tillGrid != null : "fx:id=\"tillGrid\" was not injected: check your FXML file 'till.fxml'.";
        assert middlePane != null : "fx:id=\"middlePane\" was not injected: check your FXML file 'till.fxml'.";
        assert tillTitle != null : "fx:id=\"tillTitle\" was not injected: check your FXML file 'till.fxml'.";
        assert logoutBtn != null : "fx:id=\"logoutBtn\" was not injected: check your FXML file 'till.fxml'.";
        assert dateTime != null : "fx:id=\"dateTime\" was not injected: check your FXML file 'till.fxml'.";
        assert userDetails != null : "fx:id=\"userDetails\" was not injected: check your FXML file 'till.fxml'.";

        loadBasketView();
        bindDateTime();
    }
}
