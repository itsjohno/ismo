package co.ismo.gui.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.controller
 * Date: 06/04/2015
 * Project: ismo-fxml-client
 */
public class TenderController implements Initializable {

    @FXML
    private TextField tenderField;

    @FXML
    private Text amountToPay;

    @FXML
    private ScrollPane basketContainer;

    @FXML
    private VBox basketContents;

    private int amountToTender = 0;
    private int basketTotal = 0;
    private int basketNow = 0;

    // Parent Controllers
    private TillController tillController;

    public void setupTillController(TillController tillController) {
        this.tillController = tillController;
    }

    public void setupTender(ObservableList<Node> products, int cost) {
        basketTotal = cost;
        basketNow = cost;

        basketContents.getChildren().addAll(products);
        amountToPay.textProperty().set("£" + String.format("%.2f", (float) basketNow / 100));
    }

    @FXML
    private void addAmount(int amount) {
        int oldAmount = 0, newAmount = 0;
        if (!tenderField.getText().isEmpty()) { oldAmount = Integer.parseInt(tenderField.getText()); }
        newAmount =  oldAmount + amount;
        tenderField.textProperty().set(Integer.toString(newAmount));
        tenderField.positionCaret(tenderField.getLength());
    }

    @FXML
    private void cashTender() {
        System.out.println("CASH TENDERED - " + amountToTender);
    }

    @FXML
    private void cardTender() {
        System.out.println("CARD TENDERED - " + amountToTender);
    }

    @FXML
    private void voucherTender() {
        // Lets just treat this as Card Tender for now.
        this.cardTender();
    }

    @FXML
    private void loyaltyTender() {
        // Lets just treat this as Card Tender for now.
        this.cardTender();
    }

    private void setupEventListeners() {
        tenderField.setOnKeyPressed((KeyEvent keyEvent) -> {
            switch (keyEvent.getCode()) {
                case ESCAPE: tillController.goBack(); break;

                case F1: addAmount(50); ;break;
                case F2: addAmount(100); break;
                case F3: addAmount(200); break;
                case F4: if (!keyEvent.isAltDown()) { addAmount(500); } break;
                case F5: addAmount(1000); ;break;
                case F6: addAmount(2000); break;
                case F7: addAmount(5000); break;
                case F8: addAmount(basketNow); break;
                case F9: this.cashTender(); ;break;
                case F10: this.cardTender(); break;
                //case F11: this.voucherTender(); break;
                //case F12: this.loyaltyTender(); break;
            }
        });

        tenderField.textProperty().addListener((observable, oldval, newval) -> {
            boolean valid = false;

            if (!tenderField.getText().isEmpty() && tenderField.getText().matches("[0-9]*")) {
                int i = Integer.parseInt(tenderField.getText());
                if (i > 0) {
                    valid = true;
                }
                amountToTender = i;
            }
        });

        tenderField.textProperty().addListener((observable, oldvalue, newvalue) -> {
            int lastElement = tenderField.getStyleClass().size() - 1;
            if (tenderField.getStyleClass().get(lastElement).equalsIgnoreCase("error_TextField")) {
                tenderField.setPromptText("Specify Amount");
                tenderField.getStyleClass().remove(lastElement);
            }
        });

        basketContainer.setOnMouseClicked((e) -> {
            // Hack-y way of preventing the scrollPane from getting focus.
            tenderField.requestFocus();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert tenderField != null :  "fx:id=\"tenderField\" was not injected: check your FXML file 'till_tender.fxml'.";
        assert amountToPay != null :  "fx:id=\"amountToPay\" was not injected: check your FXML file 'till_tender.fxml'.";
        assert basketContainer != null :  "fx:id=\"basketContainer\" was not injected: check your FXML file 'till_tender.fxml'.";
        assert basketContents != null :  "fx:id=\"basketContents\" was not injected: check your FXML file 'till_tender.fxml'.";

        setupEventListeners();
    }
}
