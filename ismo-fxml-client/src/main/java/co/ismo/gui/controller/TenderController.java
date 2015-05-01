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

    private int tenderedAmount = 0;

    // Parent Controllers
    private TillController tillController;

    public void setupTillController(TillController tillController) {
        this.tillController = tillController;
    }

    public void setupTender(ObservableList<Node> products, int cost) {
        basketContents.getChildren().addAll(products);
        amountToPay.textProperty().set("£" + String.format("%.2f", (float) cost / 100));
    }

    @FXML
    private void cashTender() {
        System.out.println("CASH TENDERED");
    }

    @FXML
    private void cardTender() {
        System.out.println("CARD TENDERED");
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

                case F1: tenderedAmount += 50; ;break;
                case F2: tenderedAmount += 100; break;
                case F3: tenderedAmount += 200; break;
                case F4: if (!keyEvent.isAltDown()) { tenderedAmount += 500; } break;
                case F5: tenderedAmount += 1000; ;break;
                case F6: tenderedAmount += 2000; break;
                case F7: tenderedAmount += 5000; break;
                case F8: tenderedAmount += 0; break;
                case F9: this.cashTender(); ;break;
                case F10: this.cardTender(); break;
                case F11: this.voucherTender(); break;
                case F12: this.loyaltyTender(); break;
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
