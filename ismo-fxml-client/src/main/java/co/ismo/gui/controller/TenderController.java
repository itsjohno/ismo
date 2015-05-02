package co.ismo.gui.controller;

import co.ismo.object.type.Product;
import co.ismo.object.type.Tender;
import co.ismo.object.type.Transaction;
import co.ismo.object.util.TransactionUtility;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    private GridPane tenderGrid;

    @FXML
    private AnchorPane detailPane;

    @FXML
    private TextField tenderField;

    @FXML
    private Text actionText;

    @FXML
    private Text amountToPay;

    @FXML
    private ScrollPane basketContainer;

    @FXML
    private VBox basketContents;

    private int amountToTender = 0;
    private SimpleIntegerProperty basketNow = new SimpleIntegerProperty();

    private Transaction transaction;
    private ObservableMap<Product, Integer> productMap;

    private boolean tenderComplete = false;

    // Parent Controllers
    private TillController tillController;

    public void setupTillController(TillController tillController) {
        this.tillController = tillController;
    }

    public void setupTender(ObservableList<Node> productViews, ObservableMap<Product, Integer> products) {
        int cost = 0;
        for (Product i : products.keySet()) {
            cost += i.getPrice() * products.get(i);
        }

        transaction = tillController.getTransaction();
        productMap = products;

        transaction.setTotalCost(cost);
        transaction.setProducts(products);
        basketNow.set(cost);

        basketContents.getChildren().addAll(productViews);
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
        if (!tenderField.getText().isEmpty() && amountToTender > 0) {
            Tender tender = null;

            for (Tender t : transaction.getTenders()) {
                if (t.getType().equalsIgnoreCase("Cash")) {
                    tender = t;
                }
            }

            if (tender == null) {
                tender = new Tender("Cash", amountToTender);
            } else {
                tender.setAmount(tender.getAmount() + amountToTender);
            }

            transaction.getTenders().add(tender);

            basketNow.set(basketNow.getValue() - amountToTender);
            tenderField.textProperty().set("");
        }
        else {
            tenderField.setText("");
            tenderField.setPromptText("Cannot Zero Tender");
            tenderField.getStyleClass().add("error_textField");
        }
    }

    @FXML
    private void cardTender() {
        if (!tenderField.getText().isEmpty() && amountToTender > 0) {
            if (amountToTender > transaction.getTotalCost()) {
                tenderField.setText("");
                tenderField.setPromptText("Cannot Over Tender");
                tenderField.getStyleClass().add("error_textField");
            } else {
                Tender tender = null;

                for (Tender t : transaction.getTenders()) {
                    if (t.getType().equalsIgnoreCase("Card")) {
                        tender = t;
                    }
                }

                if (tender == null) {
                    tender = new Tender("Card", amountToTender);
                } else {
                    tender.setAmount(tender.getAmount() + amountToTender);
                }

                transaction.getTenders().add(tender);

                basketNow.set(transaction.getTotalCost() - amountToTender);
                tenderField.textProperty().set("");
            }
        }
        else {
            tenderField.setText("");
            tenderField.setPromptText("Cannot Zero Tender");
            tenderField.getStyleClass().add("error_textField");
        }
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
                case ESCAPE:
                    if (tenderComplete) {
                        tillController.logoutUser(keyEvent);
                    } else {
                        tillController.goBack();
                    }
                    break;

                case F1: addAmount(50); break;
                case F2: addAmount(100); break;
                case F3: addAmount(200); break;
                case F4: if (!keyEvent.isAltDown()) { addAmount(500); } break;
                case F5: addAmount(1000); break;
                case F6: addAmount(2000); break;
                case F7: addAmount(5000); break;
                case F8: addAmount(basketNow.getValue()); break;
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

            if (!valid) {
                tenderField.setPromptText("Invalid Tender");
                tenderField.getStyleClass().add("error_textField");
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

        basketNow.addListener((change) -> {
            if (basketNow.getValue() <= 0) {
                completeTransaction();
            } else {
                amountToPay.textProperty().set("£" + String.format("%.2f", (float) basketNow.getValue() / 100));
            }

            if (transaction.getTenders().size() > 0 && basketNow.getValue() > 0) {
                tillController.disableGoBackBtn();
            }
        });
    }

    private void completeTransaction() {
        actionText.setText("Change Due");

        int change = basketNow.getValue();
        if (basketNow.getValue() < 0) {
            change = -basketNow.getValue();
        }

        amountToPay.textProperty().set("£" + String.format("%.2f", (float) change / 100));
        tenderComplete = true;
        new TransactionUtility().saveTransaction(transaction, tillController.getCurrentOperator());

        disableAll();
        tillController.setLogoutBtn();
        tillController.logoutBtnFocus();
    }

    private void disableAll() {
        for (Node n : tenderGrid.getChildren()) {
            n.setDisable(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert tenderGrid != null :  "fx:id=\"tenderGrid\" was not injected: check your FXML file 'till_tender.fxml'.";
        assert detailPane != null :  "fx:id=\"detailPane\" was not injected: check your FXML file 'till_tender.fxml'.";
        assert tenderField != null :  "fx:id=\"tenderField\" was not injected: check your FXML file 'till_tender.fxml'.";
        assert actionText != null :  "fx:id=\"actionText\" was not injected: check your FXML file 'till_tender.fxml'.";
        assert amountToPay != null :  "fx:id=\"amountToPay\" was not injected: check your FXML file 'till_tender.fxml'.";
        assert basketContainer != null :  "fx:id=\"basketContainer\" was not injected: check your FXML file 'till_tender.fxml'.";
        assert basketContents != null :  "fx:id=\"basketContents\" was not injected: check your FXML file 'till_tender.fxml'.";

        setupEventListeners();
    }
}
