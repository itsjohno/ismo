package co.ismo.gui.controller;

import co.ismo.gui.view.ButtonView;
import co.ismo.gui.view.LoyaltyView;
import co.ismo.gui.view.ProductView;
import co.ismo.object.type.Product;
import co.ismo.object.util.ProductUtility;
import co.ismo.util.SharedViewUtils;
import javafx.animation.AnimationTimer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.controller
 * Date: 11/04/2015
 * Project: ismo-fxml-client
 */
public class BasketController implements Initializable {

    @FXML
    private GridPane basketGrid;

    @FXML
    private Pane btnPane;

    @FXML
    private Pane customerPane;

    @FXML
    private TextField skuField;

    @FXML
    private ScrollPane basketContainer;

    @FXML
    private VBox basketContents;

    @FXML
    private Text basketCount;

    @FXML
    private Text basketCost;

    // Hotswappable Button Controllers
    private Parent defaultBtn;
    private Parent alternateBtn;

    // Parent Controller
    private TillController tillController;

    // Item Template
    private Parent itemTemplate;

    private SimpleIntegerProperty basketCountProperty;
    private SimpleStringProperty basketCostProperty;
    private ObservableMap<Product, Integer> basket;
    private int selectedItem;

    public void setupParentController(TillController tillController) {
        this.tillController = tillController;
        setupButtons();
    }

    public void keyListener(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ESCAPE: tillController.logoutUser(keyEvent); break;
            case UP: moveSelection(true); break;
            case DOWN: moveSelection(false); break;

            case F1: tillController.loadProductLookupView(); break;
            case F2: System.out.println("F2 Pressed"); break;
            case F3: System.out.println("F3 Pressed"); break;
            case F4: System.out.println("F4 Pressed"); break;
            case F5: System.out.println("F5 Pressed"); break;
            case F6: System.out.println("F6 Pressed"); break;
            case F7: System.out.println("F7 Pressed"); break;
            case F8: System.out.println("F8 Pressed"); break;
            case F9: System.out.println("F9 Pressed"); break;
            case F10: System.out.println("F2 Pressed"); break;
            case F11: System.out.println("F11 Pressed"); break;
            case F12: System.out.println("F12 Pressed"); break;
        }
    }

    private void setupEventListeners() {
        skuField.setOnKeyPressed((KeyEvent keyEvent) -> keyListener(keyEvent));

        skuField.setOnAction((ActionEvent ae) -> {
            if (!addItem(skuField.getText(), 1)) {
                skuField.setText("");
                skuField.setPromptText("Unrecognised SKU/Barcode");
                skuField.getStyleClass().add("error_textField");
            }
            else {
                skuField.setText("");
            }

            skuField.requestFocus();
        });

        skuField.textProperty().addListener((observable, oldvalue, newvalue) -> {
            int lastElement = skuField.getStyleClass().size() - 1;
            if (skuField.getStyleClass().get(lastElement).equalsIgnoreCase("error_TextField")) {
                skuField.setPromptText("Enter SKU/Barcode");
                skuField.getStyleClass().remove(lastElement);
            }
        });

        basketContainer.setOnMouseClicked((e) -> {
            // Hack-y way of preventing the scrollPane from getting focus.
            skuField.requestFocus();
        });

        basketContents.getChildrenUnmodifiable().addListener((Observable observable) -> {
            // http://stackoverflow.com/questions/13156896/javafx-auto-scroll-down-scrollpane
            AnimationTimer timer = new AnimationTimer() {
                long lng = 0;

                @Override
                public void handle(long l) {
                    if (lng == 0) {
                        lng = l;
                    }
                    if (l > lng + 100000) {
                        basketContainer.setVvalue(1.0);
                        this.stop();
                    }
                }
            };
            timer.start();
        });

        basket.addListener((InvalidationListener) (change) -> {
            int count = 0;
            int cost = 0;

            for (Product i : basket.keySet()) {
                cost += i.getPrice() * basket.get(i);
                count += basket.get(i);
            }

            basketCostProperty.set("£" + String.format("%.2f", (float)cost/100));
            basketCountProperty.set(count);
        });
    }

    private void moveSelection(boolean moveUp) {
        if (basket.size() > 0) {
            if (moveUp) {
                if (selectedItem - 1 >= 0) {
                    ((ProductView)basketContents.getChildren().get(selectedItem)).toggleHighlight();
                    ((ProductView)basketContents.getChildren().get(selectedItem-1)).toggleHighlight();
                    ensureSelectionVisible(basketContents.getChildren().get(selectedItem-1));
                    selectedItem--;
                }
            } else {
                if (selectedItem + 1 <= basket.size() - 1) {
                    ((ProductView)basketContents.getChildren().get(selectedItem)).toggleHighlight();
                    ((ProductView)basketContents.getChildren().get(selectedItem+1)).toggleHighlight();
                    ensureSelectionVisible(basketContents.getChildren().get(selectedItem+1));
                    selectedItem++;
                }
            }
        }
    }

    private void ensureSelectionVisible(Node node) {
        // http://stackoverflow.com/questions/12837592/how-to-scroll-to-make-a-node-within-the-content-of-a-scrollpane-visible
        double height = basketContainer.getContent().getBoundsInLocal().getHeight();
        double y = node.getBoundsInParent().getMaxY();
        basketContainer.setVvalue(y / height);
    }

    private void loadCustomerPane() {
        customerPane.getChildren().add(new LoyaltyView().loadLoyaltyView(this));
    }

    private boolean addItem(String skuFieldText, int qty)
    {
        Product originalProduct = null;

        for (Product i : basket.keySet())
        {
            boolean checkBarcodes = true;

            if (skuFieldText.equalsIgnoreCase(i.getSku()))
            {
                checkBarcodes = false;
                originalProduct = i;
            }

            if (i.getBarcodes() != null && checkBarcodes)
            {
                for (String barcode : i.getBarcodes())
                {
                    if (skuFieldText.equalsIgnoreCase(barcode))
                    {
                        originalProduct = i;
                        break;
                    }
                }
            }

            if (originalProduct != null) { break; }
        }

        if (originalProduct != null)
        {
            ProductView originalProductView = null;
            for (Node iv : basketContents.getChildren()) {
                if (originalProduct.getSku().equalsIgnoreCase(((ProductView)iv).getSku())) {
                    originalProductView = (ProductView) iv;
                }
            }

            if (originalProductView != null) {
                originalProductView.updateQty(basket.get(originalProduct) + qty, originalProduct.getPrice());
                basket.put(originalProduct, basket.get(originalProduct) + qty);
            }
            else {
                System.out.println("An error occurred!");
            }
        }
        else
        {
            Product product = new ProductUtility().getItem(skuFieldText);

            if (product != null) {
                ProductView productView = new ProductView();
                productView.setupItemDisplay(product, qty);

                if (basket.size() > 0) {
                    ((ProductView) basketContents.getChildren().get(selectedItem)).toggleHighlight();
                    productView.toggleHighlight();
                    selectedItem = basketContents.getChildrenUnmodifiable().size();
                } else {
                    productView.toggleHighlight();
                    selectedItem = 0;
                }

                basket.put(product, qty);
                basketContents.getChildren().add(productView);
            }
            else
            {
                return false;
            }
        }

        return true;
    }

    private void setupButtons() {
        defaultBtn = new ButtonView().loadButtonView(tillController);
        btnPane.getChildren().add(defaultBtn);
    }

    private void setupBasket() {
        basket = FXCollections.observableHashMap();
        basketCountProperty = new SimpleIntegerProperty(0);
        basketCount.textProperty().bind(basketCountProperty.asString());

        basketCostProperty = new SimpleStringProperty("£0.00");
        basketCost.textProperty().bind(basketCostProperty);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert basketGrid != null : "fx:id=\"basketGrid\" was not injected: check your FXML file 'till_basket.fxml'.";
        assert btnPane != null : "fx:id=\"btnPane\" was not injected: check your FXML file 'till_basket.fxml'.";
        assert customerPane != null : "fx:id=\"customerPane\" was not injected: check your FXML file 'till_basket.fxml'.";
        assert skuField != null : "fx:id=\"skuField\" was not injected: check your FXML file 'till_basket.fxml'.";
        assert basketContainer != null : "fx:id=\"basketContainer\" was not injected: check your FXML file 'till_basket.fxml'.";
        assert basketContents != null : "fx:id=\"basketContents\" was not injected: check your FXML file 'till_basket.fxml'.";
        assert basketCount != null : "fx:id=\"basketCount\" was not injected: check your FXML file 'till_basket.fxml'.";
        assert basketCost != null : "fx:id=\"basketCost\" was not injected: check your FXML file 'till_basket.fxml'.";

        setupBasket();
        setupEventListeners();
        loadCustomerPane();
    }
}
