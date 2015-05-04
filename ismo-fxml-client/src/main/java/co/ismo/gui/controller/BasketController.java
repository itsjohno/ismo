package co.ismo.gui.controller;

import co.ismo.gui.view.ButtonView;
import co.ismo.gui.view.LoyaltyView;
import co.ismo.gui.view.ProductView;
import co.ismo.object.type.Product;
import co.ismo.object.util.ProductUtility;
import javafx.animation.AnimationTimer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Map;
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

    @FXML
    private Text itemQty;

    @FXML
    private Button tenderBtn;

    // Hotswappable Button Controllers
    private ButtonView btnView;

    // Parent Controller
    private TillController tillController;

    private SimpleIntegerProperty basketCountProperty;
    private SimpleStringProperty basketCostProperty;
    private SimpleStringProperty currentQtyProperty;

    private ObservableMap<Product, Integer> basket;
    private int selectedItem;
    private int integerQty;

    public void setupParentController(TillController tillController) {
        this.tillController = tillController;
        setupButtons();
    }

    public void keyPressedListener(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ESCAPE:
                tillController.logoutUser(false);
                break;
            case UP:
                moveSelection(true);
                break;
            case DOWN:
                moveSelection(false);
                break;

            case F1:
                tillController.loadProductLookupView();
                break;
            case F2:
                notImplemented();
                break;
            case F3:
                notImplemented();
                break;
            case F4:
                if (!keyEvent.isAltDown()) {
                    notImplemented();
                }
                break;
            case F5:
                if (basketCountProperty.getValue() > 0) {
                    tillController.suspendTransaction();
                }
                break;
            case F6:
                if (basketCountProperty.getValue() <= 0) {
                    tillController.resumeTransaction();
                }
                break;
            case F7:
                voidItem();
                break;
            case F8:
                notImplemented();
                break;
            case F9:
                tillController.loadWebBrowserView();
                break;
            case F10:
                notImplemented();
                break;
            case F11:
                System.out.println("Reserved for Loyalty Lookup");
                break;
            case F12:
                if (basketCountProperty.getValue() > 0) {
                    tillController.loadTenderView();
                }
                break;
        }
    }

    public void keyTypedListener(KeyEvent keyEvent) {
        if (keyEvent.getCharacter().equalsIgnoreCase("*")) {
            modifyQty();
            keyEvent.consume();
        }
    }

    public void notImplemented() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Not Implemented");
        alert.setHeaderText(null);
        alert.setContentText("This button is not currently implemented!");

        alert.showAndWait();
    }

    @FXML
    private void tenderBasket() {
        tillController.loadTenderView();
    }

    private void modifyQty() {
        boolean valid = false;
        if (skuField.getText().matches("[0-9]")) {
            int i = Integer.parseInt(skuField.getText());
            if (i > 0) {
                valid = true;
            }
            integerQty = i;
            currentQtyProperty.set(integerQty + "x");
        }

        skuField.setText("");

        if (!valid) {
            skuField.setPromptText("Invalid Quantity");
            skuField.getStyleClass().add("error_textField");
        }
    }

    private void setupEventListeners() {
        skuField.setOnKeyPressed((KeyEvent keyEvent) -> keyPressedListener(keyEvent));
        skuField.setOnKeyTyped((KeyEvent keyEvent) -> keyTypedListener(keyEvent));

        skuField.setOnAction((ActionEvent ae) -> {
            if (addItem(skuField.getText(), integerQty)) {
                skuField.setText("");
                integerQty = 1;
                currentQtyProperty.set(integerQty + "x");
            } else {
                skuField.setText("");
                skuField.setPromptText("Unrecognised SKU/Barcode");
                skuField.getStyleClass().add("error_textField");
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

            basketCostProperty.set("£" + String.format("%.2f", (float) cost / 100));
            basketCountProperty.set(count);

            if (count > 0) {
                tenderBtn.setDisable(false);
                tillController.disableGoBackBtn();
                btnView.getBtnController().toggleResumeButton(true);
                btnView.getBtnController().toggleSuspendButton(false);

            } else {
                tenderBtn.setDisable(true);
                tillController.disableGoBackBtn();
                btnView.getBtnController().toggleResumeButton(false);
                btnView.getBtnController().toggleSuspendButton(true);
            }
        });
    }

    public void voidItem() {
        if (basket.size() > 0 && selectedItem >= 0) {

            String sku = ((ProductView) basketContents.getChildren().get(selectedItem)).getSku();

            for (Map.Entry<Product, Integer> entry : basket.entrySet()) {
                if (entry.getKey().getSku().equalsIgnoreCase(sku) && entry.getValue() > 0) {
                    entry.setValue(0);
                    basketContents.getChildren().remove(selectedItem);

                    if (basketContents.getChildren().size() > 0) {
                        ((ProductView) basketContents.getChildren().get(basketContents.getChildren().size() - 1)).toggleHighlight();
                    }

                    selectedItem = basketContents.getChildren().size() - 1;
                    break;
                }
            }
        }
    }

    public ObservableList<Node> getBasketViews() {
        return basketContents.getChildrenUnmodifiable();
    }

    public ObservableMap<Product, Integer> getBasket() {
        return basket;
    }

    private void moveSelection(boolean moveUp) {
        if (basketContents.getChildren().size() > 0) {
            if (moveUp) {
                if (selectedItem - 1 >= 0) {
                    ((ProductView) basketContents.getChildren().get(selectedItem)).toggleHighlight();
                    ((ProductView) basketContents.getChildren().get(selectedItem - 1)).toggleHighlight();
                    ensureSelectionVisible(basketContents.getChildren().get(selectedItem - 1));
                    selectedItem--;
                }
            } else {
                if (selectedItem + 1 <= basketContents.getChildren().size() - 1) {
                    ((ProductView) basketContents.getChildren().get(selectedItem)).toggleHighlight();
                    ((ProductView) basketContents.getChildren().get(selectedItem + 1)).toggleHighlight();
                    ensureSelectionVisible(basketContents.getChildren().get(selectedItem + 1));
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

    private boolean addItem(String skuFieldText, int qty) {
        Product originalProduct = isProductInBasket(skuFieldText);
        Product product;

        if (originalProduct != null) {
            ProductView originalProductView = null;
            for (Node iv : basketContents.getChildren()) {
                if (originalProduct.getSku().equalsIgnoreCase(((ProductView) iv).getSku())) {
                    originalProductView = (ProductView) iv;
                }
            }

            if (originalProductView != null) {
                originalProductView.updateQty(basket.get(originalProduct) + qty, originalProduct.getPrice());
                basket.put(originalProduct, basket.get(originalProduct) + qty);
                return true;
            } else {
                product = originalProduct;
            }
        } else {
            product = new ProductUtility().getItem(skuFieldText);

            if (product == null) {
                return false;
            }
        }

        ProductView productView = new ProductView();
        productView.setupItemDisplay(product, qty);

        if (basket.size() > 0 && selectedItem >= 0) {
            ((ProductView) basketContents.getChildren().get(selectedItem)).toggleHighlight();
            productView.toggleHighlight();
            selectedItem = basketContents.getChildrenUnmodifiable().size();
        } else {
            productView.toggleHighlight();
            selectedItem = 0;
        }

        basket.put(product, qty);
        basketContents.getChildren().add(productView);

        return true;
    }

    public boolean addItem(Product product) {
        Product originalProduct = isProductInBasket(product.getSku());

        if (originalProduct != null) {
            ProductView originalProductView = null;
            for (Node iv : basketContents.getChildren()) {
                if (originalProduct.getSku().equalsIgnoreCase(((ProductView) iv).getSku())) {
                    originalProductView = (ProductView) iv;
                }
            }

            if (originalProductView != null) {
                originalProductView.updateQty(basket.get(originalProduct) + 1, originalProduct.getPrice());
                basket.put(originalProduct, basket.get(originalProduct) + 1);
            } else {
                System.out.println("An error occurred!");
            }
        } else {
            ProductView productView = new ProductView();
            productView.setupItemDisplay(product, 1);

            if (basketContents.getChildren().size() > 0) {
                ((ProductView) basketContents.getChildren().get(selectedItem)).toggleHighlight();
                productView.toggleHighlight();
                selectedItem = basketContents.getChildrenUnmodifiable().size();
            } else {
                productView.toggleHighlight();
                selectedItem = 0;
            }

            basket.put(product, 1);
            basketContents.getChildren().add(productView);
        }

        return true;
    }

    private Product isProductInBasket(String skuFieldText) {
        Product originalProduct = null;

        for (Product i : basket.keySet()) {
            boolean checkBarcodes = true;

            if (skuFieldText.equalsIgnoreCase(i.getSku())) {
                checkBarcodes = false;
                originalProduct = i;
            }

            if (i.getBarcodes() != null && checkBarcodes) {
                for (String barcode : i.getBarcodes()) {
                    if (skuFieldText.equalsIgnoreCase(barcode)) {
                        originalProduct = i;
                        break;
                    }
                }
            }

            if (originalProduct != null) {
                break;
            }
        }

        return originalProduct;
    }

    private void setupButtons() {
        btnView = new ButtonView();
        btnPane.getChildren().add(btnView.loadButtonView(tillController, this));
        btnView.getBtnController().toggleSuspendButton(true);
    }

    private void setupBasket() {
        basket = FXCollections.observableHashMap();
        basketCountProperty = new SimpleIntegerProperty(0);
        basketCount.textProperty().bind(basketCountProperty.asString());

        basketCostProperty = new SimpleStringProperty("£0.00");
        basketCost.textProperty().bind(basketCostProperty);

        currentQtyProperty = new SimpleStringProperty("1x");
        itemQty.textProperty().bind(currentQtyProperty);
        integerQty = 1;
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
        assert tenderBtn != null : "fx:id=\"tenderBtn\" was not injected: check your FXML file 'till_basket.fxml'.";

        setupBasket();
        setupEventListeners();
        loadCustomerPane();
    }
}
