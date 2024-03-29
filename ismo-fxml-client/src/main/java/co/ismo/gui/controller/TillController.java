package co.ismo.gui.controller;

import co.ismo.gui.view.*;
import co.ismo.object.type.Operator;
import co.ismo.object.type.Transaction;
import co.ismo.object.util.TransactionUtility;
import co.ismo.util.DynamicHashMap;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

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
    private Text transactionNumber;

    @FXML
    private Button goBackBtn;

    private SimpleStringProperty transactionIDProperty;
    private boolean logoutDisabled = false;

    private Stage tillStage;
    private Transaction transaction;
    private BasketView basketView;

//    @FXML
//    public void logoutUser(Event e) {
//        Node s = (Node) e.getSource();
//        Stage currentStage = (Stage) s.getScene().getWindow();
//        currentStage.fireEvent(new WindowEvent(currentStage, WindowEvent.WINDOW_CLOSE_REQUEST));
//    }

    @FXML
    public void logoutUser(boolean force) {
        if (!logoutDisabled || force) {
            tillStage.close();
            new LoginView((Stage) tillStage.getOwner(), 1, false);
        }
    }

    public void goBack() {
        middlePane.getChildren().clear();
        middlePane.getChildren().add(basketView.getBasketView());

        goBackBtn.setText("Log Out (Esc)");
        goBackBtn.setOnMouseClicked((event) -> logoutUser(false));
    }

    public void setLogoutBtn() {
        goBackBtn.setText("Log Out (Esc)");
        goBackBtn.setOnMouseClicked((event) -> logoutUser(false));
    }

    public void disableGoBackBtn() {
        logoutDisabled = true;
        goBackBtn.setDisable(true);
    }

    public void enableGoBackBtn() {
        logoutDisabled = false;
        goBackBtn.setDisable(false);
    }

    public void logoutBtnFocus() {
        goBackBtn.setDisable(false);
        goBackBtn.requestFocus();

        goBackBtn.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                logoutUser(false);
            }
        });
    }

    public void setCurrentOperator(Operator operator) {
        this.currentOperator = operator;
        userDetails.setText(currentOperator.getForename() + " " + currentOperator.getSurname() + " (" + DynamicHashMap.getUserLevels().get(currentOperator.getUserLevel()) + ")");
    }

    public void provideStage(Stage s) {
        this.tillStage = s;
    }

    public Operator getCurrentOperator() {
        return currentOperator;
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
        basketView = new BasketView();
        basketView.loadBasketView(this);

        if (basketView != null) {
            middlePane.getChildren().add(basketView.getBasketView());
        } else {
            System.out.println("An error occurred while trying to load the BasketView");
        }
    }

    public void loadProductLookupView() {
        if (middlePane.getChildren().contains(basketView.getBasketView())) {
            middlePane.getChildren().clear();
            middlePane.getChildren().add(new ProductLookupView().getProductLookupView(this, basketView.getBasketController()));

            goBackBtn.setText("Go Back (Esc)");
            goBackBtn.setOnMouseClicked((event) -> goBack());
        }
    }

    public void loadWebBrowserView() {
        if (middlePane.getChildren().contains(basketView.getBasketView())) {
            BrowserView browserView = new BrowserView();

            middlePane.getChildren().clear();
            middlePane.getChildren().add(browserView.getBrowserView(this));

            goBackBtn.setText("Go Back (Esc)");
            goBackBtn.setOnMouseClicked((event) -> {
                browserView.getController().closeBrowserEngine();
                goBack();
            });
        }
    }

    public void loadTenderView() {
        if (middlePane.getChildren().contains(basketView.getBasketView())) {
            middlePane.getChildren().clear();

            middlePane.getChildren().add(new TenderView().getTenderView(this, this.basketView.getBasketController()));

            goBackBtn.setText("Go Back (Esc)");
            goBackBtn.setOnMouseClicked((event) -> goBack());
        }
    }

    private void setTransaction() {
        transaction = new TransactionUtility().getNextTransaction();

        String storeID = transaction.getStoreID();
        String tillID = transaction.getTillID();
        String transactionID = Integer.toString(transaction.getTransactionID());

        if (transactionID.length() < 5) {
            transactionID = String.format("%5s", transactionID).replace(' ', '0');
        }
        transactionIDProperty = new SimpleStringProperty(storeID + tillID + "-" + transactionID);
        transactionNumber.textProperty().bind(transactionIDProperty);
    }

    public Transaction getTransaction() {
        return this.transaction;
    }

    public void suspendTransaction() {
        transaction.setSuspended(true);
        new TransactionUtility().saveTransaction(transaction, getCurrentOperator(), true);
        logoutUser(true);
    }

    public void resumeTransaction() {

    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert tillGrid != null : "fx:id=\"tillGrid\" was not injected: check your FXML file 'till.fxml'.";
        assert middlePane != null : "fx:id=\"middlePane\" was not injected: check your FXML file 'till.fxml'.";
        assert tillTitle != null : "fx:id=\"tillTitle\" was not injected: check your FXML file 'till.fxml'.";
        assert goBackBtn != null : "fx:id=\"goBackBtn\" was not injected: check your FXML file 'till.fxml'.";
        assert dateTime != null : "fx:id=\"dateTime\" was not injected: check your FXML file 'till.fxml'.";
        assert userDetails != null : "fx:id=\"userDetails\" was not injected: check your FXML file 'till.fxml'.";
        assert transactionNumber != null : "fx:id=\"transactionNumber\" was not injected: check your FXML file 'till.fxml'.";

        loadBasketView();
        bindDateTime();
        setTransaction();
        goBackBtn.setOnMouseClicked((event) -> logoutUser(false));
    }
}
