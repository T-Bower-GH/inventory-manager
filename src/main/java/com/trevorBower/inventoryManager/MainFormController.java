package com.trevorBower.inventoryManager;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import static com.trevorBower.inventoryManager.Inventory.saveAllDataToJson;
import static com.trevorBower.inventoryManager.Utility.*;

/**
 * @author Trevor Bower
 */
public class MainFormController implements Initializable {
    // Parts Table
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvLevelCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TextField partSearchBar;

    // Products Table
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInvLevelCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    @FXML
    private TextField productSearchBar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setPartsTable(partTable, partIdCol, partNameCol, partInvLevelCol, partPriceCol, SelectionMode.SINGLE);
        setProductsTable(productTable, productIdCol, productNameCol, productInvLevelCol, productPriceCol);
        Platform.runLater(() -> {
            if (partTable.getItems().isEmpty() && productTable.getItems().isEmpty()) {
                displayError("No previously saved part/product values found. Please be sure to use 'Save " +
                        "All' button to save part/product values.");
            }
        });
    }

    @FXML
    public void switchToAddPart(ActionEvent event) { Utility.switchScene("/forms/AddPartForm.fxml", event); }

    @FXML
    public void switchToModifyPart(ActionEvent event) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            displayError("Please select a part to modify.");
            return;
        }

        switchSceneWithFunction("/forms/ModifyPartForm.fxml", event, (ModifyPartController controller) ->
                controller.populatePartValues(selectedPart));
    }

    @FXML
    void partDelete() { deleteSelectedPart(partTable); }

    @FXML
    void partSearchBar() {
        displayPartSearchResults(partTable, partSearchBar);
    }

    @FXML
    public void switchToAddProduct(ActionEvent event) {
        switchScene("/forms/AddProductForm.fxml", event);
    }

    @FXML
    public void switchToModifyProduct(ActionEvent event) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            displayError("Please select a product to modify.");
            return;
        }

        switchSceneWithFunction("/forms/ModifyProductForm.fxml", event, (ModifyProductController controller) ->
                controller.populateProductValues(selectedProduct));
    }

    @FXML
    void productDelete() {
        deleteSelectedProduct(productTable);
    }

    @FXML
    void productSearchBar() {
        displayProductSearchResults(productTable, productSearchBar);
    }

    @FXML
    public void mainExit(ActionEvent event) {
        if (Utility.hasUserConfirmed("close the program")) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void saveAll() {
        if (Utility.hasUserConfirmed("overwrite all previous parts, products, and associated parts")) {
            saveAllDataToJson();
        }
    }

}
