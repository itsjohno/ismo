package co.ismo.gui.view;

import co.ismo.objects.Item;
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
public class ItemView extends VBox implements Initializable {

    @FXML
    private VBox rootBox;

    @FXML
    private Text qty;

    @FXML
    private Text value;

    @FXML
    private Text qtyValue;

    @FXML
    private Text barcode;

    @FXML
    private Text productName;

    @FXML
    private Text supercat;

    @FXML
    private Text subcat;

    public ItemView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../res/fxml/basket_itemTemplate.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setupItemDisplay(Item item, int quantity) {
        qty.setText(Integer.toString(quantity) + "x");
        value.setText("£" + String.format("%.2f", (float)item.getCost()/100));
        qtyValue.setText("£" + String.format("%.2f", (float)(item.getCost() * quantity)/100));
        barcode.setText(item.getBarcode() != null ? item.getBarcode() : item.getSku());
        productName.setText(item.getName());
        supercat.setText(item.getSupercat());
        subcat.setText(item.getSubcat());
    }

    public void toggleHighlight() {
        if (rootBox.getStyleClass().contains("highlightItem")) {
            rootBox.getStyleClass().clear();
        } else {
            rootBox.getStyleClass().add("highlightItem");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert rootBox != null : "fx:id=\"qty\" was not injected: check your FXML file 'basket_itemTemplate.fxml'.";
        assert qty != null : "fx:id=\"qty\" was not injected: check your FXML file 'basket_itemTemplate.fxml'.";
        assert value != null : "fx:id=\"value\" was not injected: check your FXML file 'basket_itemTemplate.fxml'.";
        assert qtyValue != null : "fx:id=\"qtyValue\" was not injected: check your FXML file 'basket_itemTemplate.fxml'.";
        assert barcode != null : "fx:id=\"barcode\" was not injected: check your FXML file 'basket_itemTemplate.fxml'.";
        assert productName != null : "fx:id=\"productTitle\" was not injected: check your FXML file 'basket_itemTemplate.fxml'.";
        assert supercat != null : "fx:id=\"supercat\" was not injected: check your FXML file 'basket_itemTemplate.fxml'.";
        assert subcat != null : "fx:id=\"subcat\" was not injected: check your FXML file 'basket_itemTemplate.fxml'.";
    }
}
