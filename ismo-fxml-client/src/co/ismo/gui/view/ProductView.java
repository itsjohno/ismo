package co.ismo.gui.view;

import co.ismo.object.type.Product;
import co.ismo.util.DynamicHashMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.view
 * Date: 14/04/2015
 * Project: ismo-fxml-client
 */
public class ProductView extends VBox implements Initializable {

    @FXML
    private VBox rootBox;

    @FXML
    private Text qty;

    @FXML
    private Text value;

    @FXML
    private Text qtyValue;

    @FXML
    private Text sku;

    @FXML
    private Text productName;

    @FXML
    private Text supercat;

    @FXML
    private Text subcat;

    @FXML
    private Text ageRating;

    public ProductView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../res/fxml/basket_productTemplate.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setupItemDisplay(Product product, int quantity) {
        qty.setText(Integer.toString(quantity) + "x");
        value.setText("£" + String.format("%.2f", (float) product.getPrice() / 100));
        qtyValue.setText("£" + String.format("%.2f", (float) (product.getPrice() * quantity) / 100));
        sku.setText(product.getSku());
        productName.setText(product.getName());
        subcat.setText(DynamicHashMap.getSubCategories().get(product.getCategoryID()).getName());
        supercat.setText(DynamicHashMap.getSuperCategories().get(DynamicHashMap.getSubCategories().get(product.getCategoryID()).getSupercatID()));
        ageRating.setText(DynamicHashMap.getAgeRatings().get(product.getAgeRating()).getName());
    }

    public void updateQty(int newQty, int unitCost) {
        qty.setText(Integer.toString(newQty) + "x");
        qtyValue.setText("£" + String.format("%.2f", (float) (unitCost * newQty) / 100));
    }

    public void toggleHighlight() {
        if (rootBox.getStyleClass().contains("highlightItem")) {
            rootBox.getStyleClass().clear();
        } else {
            rootBox.getStyleClass().add("highlightItem");
        }
    }

    public String getSku() {
        return sku.getText();
    }

    public String getName() {
        return productName.getText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert rootBox != null : "fx:id=\"qty\" was not injected: check your FXML file 'basket_productTemplate.fxml'.";
        assert qty != null : "fx:id=\"qty\" was not injected: check your FXML file 'basket_productTemplate.fxml'.";
        assert value != null : "fx:id=\"value\" was not injected: check your FXML file 'basket_productTemplate.fxml'.";
        assert qtyValue != null : "fx:id=\"qtyValue\" was not injected: check your FXML file 'basket_productTemplate.fxml'.";
        assert sku != null : "fx:id=\"sku\" was not injected: check your FXML file 'basket_productTemplate.fxml'.";
        assert productName != null : "fx:id=\"productTitle\" was not injected: check your FXML file 'basket_productTemplate.fxml'.";
        assert supercat != null : "fx:id=\"supercat\" was not injected: check your FXML file 'basket_productTemplate.fxml'.";
        assert subcat != null : "fx:id=\"subcat\" was not injected: check your FXML file 'basket_productTemplate.fxml'.";
        assert ageRating != null : "fx:id=\"ageRating\" was not injected: check your FXML file 'basket_productTemplate.fxml'.";
    }
}
