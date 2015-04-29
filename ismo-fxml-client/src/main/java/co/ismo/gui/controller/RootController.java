package co.ismo.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.controller
 * Date: 06/04/2015
 * Project: ismo-fxml-client
 */
public class RootController implements Initializable {

    @FXML
    private Pane mainPane;

    @FXML
    private Rectangle backgroundRectangle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'simple.fxml'.";
        assert backgroundRectangle != null : "fx:id=\"backgroundRectangle\" was not injected: check your FXML file 'simple.fxml'.";
    }

    public void setBackgroundSize(double width, double height) {
        backgroundRectangle.setWidth(width);
        backgroundRectangle.setHeight(height);
    }
}
