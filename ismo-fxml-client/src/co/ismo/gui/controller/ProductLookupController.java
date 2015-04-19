package co.ismo.gui.controller;

import co.ismo.object.type.AgeRating;
import co.ismo.object.type.Category;
import co.ismo.object.type.Product;
import co.ismo.object.util.AgeRatingUtility;
import co.ismo.object.util.CategoryUtility;
import co.ismo.object.util.OtherUtility;
import co.ismo.object.util.ProductUtility;
import co.ismo.util.DynamicHashMap;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.controller
 * Date: 06/04/2015
 * Project: ismo-fxml-client
 */
public class ProductLookupController implements Initializable {

    @FXML
    private TextField skuField;

    @FXML
    private TextField barcodeField;

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox supercatField;

    @FXML
    private ComboBox catField;

    @FXML
    private ComboBox ageRatingField;

    @FXML
    private TextField priceFromField;

    @FXML
    private TextField priceToField;

    @FXML
    private TableView resultsTable;

    @FXML
    private TableColumn skuColumn;

    @FXML
    private TableColumn barcodeColumn;

    @FXML
    private TableColumn nameColumn;

    @FXML
    private TableColumn stockColumn;

    @FXML
    private TableColumn priceColumn;

    @FXML
    private TableColumn supercatColumn;

    @FXML
    private TableColumn subcatColumn;

    @FXML
    private TableColumn ageRatingColumn;

    private TillController tillController;
    private BasketController basketController;

    public void setupControllers(TillController tillController, BasketController basketController) {
        this.tillController = tillController;
        this.basketController = basketController;
    }

    private void setupSuperCatCombo() {
        ObservableList<String> supercatList = FXCollections.observableArrayList(DynamicHashMap.getSuperCategories().values());
        supercatList.add(0, "All Categories");
        supercatField.setItems(supercatList);
        supercatField.setValue(supercatList.get(0));

        supercatField.valueProperty().addListener((InvalidationListener) (change) -> {
            int i = 0;
            for (String s : supercatList) {
                if (s.equalsIgnoreCase((String)supercatField.getValue())) {
                    setupCategoryCombo(i);
                    break;
                }
                i++;
            }
        });
    }

    private void setupCategoryCombo(int supercatID) {
        ObservableList<String> categoryList = FXCollections.observableArrayList();

        for (Category ar : DynamicHashMap.getSubCategories().values()) {
            if (supercatID < 0 || supercatID == ar.getSupercatID()) {
                categoryList.add(ar.getName());
            }
        }

        categoryList.add(0, "All Categories");
        catField.setItems(categoryList);
        catField.setValue(categoryList.get(0));
    }

    private void setupAgeRatingCombo() {
        ObservableList<String> ageRatingList = FXCollections.observableArrayList();

        for (AgeRating ar : DynamicHashMap.getAgeRatings().values()) {
            ageRatingList.add(ar.getName());
        }

        ageRatingList.add(0, "All Age Ratings");
        ageRatingField.setItems(ageRatingList);
        ageRatingField.setValue(ageRatingList.get(0));
    }

    @FXML
    private void searchProducts() {

        int fromPrice = 0, toPrice = 0, subcatID = 0, supercatID = 0, ageRatingID = 0;
        if (!((String)catField.getValue()).equalsIgnoreCase("All Categories")) { subcatID = new CategoryUtility().getCategoryIDFromName((String)catField.getValue()); }
        if (!((String)supercatField.getValue()).equalsIgnoreCase("All Categories")) { supercatID = new OtherUtility().getSuperCategoryIDFromName((String)supercatField.getValue()); }
        if (!((String)ageRatingField.getValue()).equalsIgnoreCase("All Age Ratings")) { ageRatingID = new AgeRatingUtility().getAgeRatingIDFromName((String)ageRatingField.getValue()); }
        if (!priceFromField.getText().equalsIgnoreCase("")) { fromPrice = Integer.parseInt(priceFromField.getText()); }
        if (!priceToField.getText().equalsIgnoreCase("")) { toPrice = Integer.parseInt(priceToField.getText()); }

        ObservableList<Product> returnedProducts = new ProductUtility().lookupProducts(skuField.getText(), barcodeField.getText(), nameField.getText(), supercatID, subcatID, ageRatingID, fromPrice, toPrice);
        resultsTable.setItems(returnedProducts);
    }

    private void keyListener(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ESCAPE: tillController.goBack(); break;
            case ENTER: searchProducts(); break;
            case F12: System.out.println("Reset to Defaults"); break;
        }
    }

    private EventHandler<KeyEvent> numberValidation() {
        return event -> {
            TextField txtField = (TextField) event.getSource();
            if(!event.getCharacter().matches("[0-9]")){
                event.consume();
            }
        };
    }

    private void setupEventListeners() {
        priceFromField.addEventFilter(KeyEvent.KEY_TYPED, numberValidation());
        priceToField.addEventFilter(KeyEvent.KEY_TYPED, numberValidation());

        skuField.setOnKeyPressed((KeyEvent e) -> keyListener(e));
        barcodeField.setOnKeyPressed((KeyEvent e) -> keyListener(e));
        nameField.setOnKeyPressed((KeyEvent e) -> keyListener(e));
        supercatField.setOnKeyPressed((KeyEvent e) -> keyListener(e));
        catField.setOnKeyPressed((KeyEvent e) -> keyListener(e));
        ageRatingField.setOnKeyPressed((KeyEvent e) -> keyListener(e));
        priceFromField.setOnKeyPressed((KeyEvent e) -> keyListener(e));
        priceToField.setOnKeyPressed((KeyEvent e) -> keyListener(e));
    }

    private void setupResultsTable() {
        skuColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("sku"));
        barcodeColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("barcode"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        //supercatColumn.setCellValueFactory(new PropertyValueFactory<Product, String>(""));
        subcatColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("categoryID"));
        ageRatingColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("ageRating"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert skuField != null : "fx:id=\"skuField\" was not injected: check your FXML file 'till_productLookup.fxml'.";
        assert barcodeField != null : "fx:id=\"barcodeField\" was not injected: check your FXML file 'till_productLookup.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'till_productLookup.fxml'.";
        assert supercatField != null : "fx:id=\"supercatField\" was not injected: check your FXML file 'till_productLookup.fxml'.";
        assert catField != null : "fx:id=\"catField\" was not injected: check your FXML file 'till_productLookup.fxml'.";
        assert ageRatingField != null : "fx:id=\"ageRatingField\" was not injected: check your FXML file 'till_productLookup.fxml'.";
        assert priceFromField != null : "fx:id=\"priceFromField\" was not injected: check your FXML file 'till_productLookup.fxml'.";
        assert priceToField != null : "fx:id=\"priceToField\" was not injected: check your FXML file 'till_productLookup.fxml'.";

        setupEventListeners();
        setupSuperCatCombo();
        setupCategoryCombo(-1);
        setupAgeRatingCombo();
        setupResultsTable();
    }
}
