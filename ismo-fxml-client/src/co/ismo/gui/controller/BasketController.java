package co.ismo.gui.controller;

import co.ismo.gui.view.ItemView;
import co.ismo.objects.Item;
import co.ismo.util.SharedViewUtils;
import javafx.animation.AnimationTimer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import java.util.ArrayList;
import java.util.List;
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
    private Text itemCount;

    @FXML
    private Text basketCost;

    // Hotswappable Button Controllers
    private Parent defaultBtn;
    private Parent alternateBtn;

    // Parent Controller
    private TillController tillController;

    // Item Template
    private Parent itemTemplate;

    private List<Item> basket;
    private int selectedItem;

    public void setupParentController(TillController tillController) {
        this.tillController = tillController;
    }

    private void setupEventListeners() {
        skuField.setOnKeyPressed((KeyEvent keyEvent) -> {
            KeyCode kC = keyEvent.getCode();

            switch (kC) {
                case ESCAPE:
                    tillController.logoutUser(keyEvent);
                    break;
                case UP:
                    selectedItem--;
                    System.out.println("UP - SEL: " + selectedItem);
                    break;
                case DOWN:
                    selectedItem++;
                    System.out.println("DOWN - SEL: " + selectedItem);
                    break;
                case F11:
                    System.out.println("F11 Pressed");
                    break;
                case F12:
                    System.out.println("F12 Pressed");
                    break;
            }

            if (keyEvent.isControlDown()) {
                btnPane.getChildren().clear();
                btnPane.getChildren().add(alternateBtn);

                switch (kC) {
                    case F1:
                        System.out.println("Control + F1 Pressed");
                        break;
                    case F2:
                        System.out.println("Control + F2 Pressed");
                        break;
                    case F3:
                        System.out.println("Control + F3 Pressed");
                        break;
                    case F4:
                        System.out.println("Control + F4 Pressed");
                        break;
                    case F5:
                        System.out.println("Control + F5 Pressed");
                        break;
                    case F6:
                        System.out.println("Control + F6 Pressed");
                        break;
                    case F7:
                        System.out.println("Control + F7 Pressed");
                        break;
                    case F8:
                        System.out.println("Control + F8 Pressed");
                        break;
                    case F9:
                        System.out.println("Control + F9 Pressed");
                        break;
                    case F10:
                        System.out.println("Control + F10 Pressed");
                        break;

                }
            } else {
                switch (kC) {
                    case F1:
                        System.out.println("F1 Pressed");
                        break;
                    case F2:
                        System.out.println("F2 Pressed");
                        break;
                    case F3:
                        System.out.println("F3 Pressed");
                        break;
                    case F4:
                        System.out.println("F4 Pressed");
                        break;
                    case F5:
                        System.out.println("F5 Pressed");
                        break;
                    case F6:
                        System.out.println("F6 Pressed");
                        break;
                    case F7:
                        System.out.println("F7 Pressed");
                        break;
                    case F8:
                        System.out.println("F8 Pressed");
                        break;
                    case F9:
                        System.out.println("F9 Pressed");
                        break;
                    case F10:
                        System.out.println("F10 Pressed");
                        break;
                }
            }
        });

        skuField.setOnKeyReleased((KeyEvent keyEvent) -> {
            if (!keyEvent.isControlDown()) {
                btnPane.getChildren().clear();
                btnPane.getChildren().add(defaultBtn);
            }
        });

        skuField.setOnAction((ActionEvent ae) -> {
            addItem(skuField.getText());
            skuField.setText("");
            skuField.requestFocus();
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
    }

    private void moveSelection() {

    }

    private void ensureSelectionVisible(Node node) {
        // http://stackoverflow.com/questions/12837592/how-to-scroll-to-make-a-node-within-the-content-of-a-scrollpane-visible
        double height = basketContainer.getContent().getBoundsInLocal().getHeight();
        double y = node.getBoundsInParent().getMaxY();
        basketContainer.setVvalue(y/height);
    }

    private void loadCustomerPane() {
        customerPane.getChildren().add(SharedViewUtils.loadContent(getClass().getResource("../res/fxml/basket_customerPane.fxml")));
    }

    private void addItem(String skuFieldText) {
        ItemView itemView = new ItemView();
        Item item = new Item(skuFieldText);
        itemView.setupItemDisplay(item, 1);

        if (basket.size() > 0) {
            if (selectedItem == basketContents.getChildrenUnmodifiable().size() - 1) {
                ((ItemView) basketContents.getChildren().get(selectedItem)).toggleHighlight();
                itemView.toggleHighlight();
                selectedItem++;
            }
        } else {
            itemView.toggleHighlight();
            selectedItem = 0;
        }

        basket.add(item);
        basketContents.getChildren().add(itemView);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert basketGrid != null : "fx:id=\"basketGrid\" was not injected: check your FXML file 'till_basket.fxml'.";
        assert btnPane != null : "fx:id=\"btnPane\" was not injected: check your FXML file 'till_basket.fxml'.";
        assert customerPane != null : "fx:id=\"customerPane\" was not injected: check your FXML file 'till_basket.fxml'.";
        assert skuField != null : "fx:id=\"skuField\" was not injected: check your FXML file 'till_basket.fxml'.";
        assert basketContainer != null : "fx:id=\"basketContainer\" was not injected: check your FXML file 'till_basket.fxml'.";
        assert basketContents != null : "fx:id=\"basketContents\" was not injected: check your FXML file 'till_basket.fxml'.";
        assert itemCount != null : "fx:id=\"itemCount\" was not injected: check your FXML file 'till_basket.fxml'.";
        assert basketCost != null : "fx:id=\"basketCost\" was not injected: check your FXML file 'till_basket.fxml'.";

        setupEventListeners();
        loadCustomerPane();

        basket = new ArrayList<Item>();
        defaultBtn = SharedViewUtils.loadContent(getClass().getResource("../res/fxml/basket_btnPane_default.fxml"));
        alternateBtn = SharedViewUtils.loadContent(getClass().getResource("../res/fxml/basket_btnPane_alternate.fxml"));
        btnPane.getChildren().add(defaultBtn);
    }
}
