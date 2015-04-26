package co.ismo.gui.controller;

import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.controller
 * Date: 21/04/2015
 * Project: ismo-fxml-client
 */
public class BrowserController implements Initializable {

    @FXML
    private TextField urlField;

    @FXML
    private WebView webView;

    @FXML
    private ProgressBar progress;

    // Parent Controllers
    private TillController tillController;

    // Browser Engine
    private WebEngine webEngine;

    public void setupTillController(TillController tillController) {
        this.tillController = tillController;
    }

    public void closeBrowserEngine() {
        webEngine.load(null);
    }

    private void navigateToSite() {
        String specifiedURL = urlField.getText();
        progress.setVisible(true);

        if (!specifiedURL.substring(0, 7).equalsIgnoreCase("http://")) {
            specifiedURL = "http://" + specifiedURL;
            urlField.setText(specifiedURL);
        }

        webEngine.load(specifiedURL);
    }

    private void setupEventListeners() {
        urlField.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                closeBrowserEngine();
                tillController.goBack();
            }
        });

        urlField.setOnAction((ActionEvent ae) -> {
            navigateToSite();
        });

        webView.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                closeBrowserEngine();
                tillController.goBack();
            }
        });
    }

    private void setupBrowser() {
        webEngine = webView.getEngine();
        webEngine.load(urlField.getText());

        // http://stackoverflow.com/questions/14094264/display-loading-image-before-javafx-webview-loads-the-page
        progress.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
        webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState == State.SUCCEEDED) {
                // hide progress bar then page is ready
                progress.setVisible(false);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert urlField != null : "fx:id=\"urlField\" was not injected: check your FXML file 'till_browser.fxml'.";
        assert webView != null : "fx:id=\"webView\" was not injected: check your FXML file 'till_browser.fxml'.";
        assert progress != null : "fx:id=\"progress\" was not injected: check your FXML file 'till_browser.fxml'.";

        setupBrowser();
        setupEventListeners();
    }
}
