package com.trevorBower.inventoryManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import static com.trevorBower.inventoryManager.Utility.*;

/**
 * @author Trevor Bower
 */
public class ModifyProductController {
    // Entry fields and buttons
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField inventoryField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField minField;
    @FXML
    private TextField searchBar;


    // All Parts Table + Buttons
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

    // Associated Parts Table + Buttons
    @FXML
    private TableView<Part> associatedPartTable;
    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;
    @FXML
    private TableColumn<Part, String> associatedPartNameCol;
    @FXML
    private TableColumn<Part, Integer> associatedPartInvLevelCol;
    @FXML
    private TableColumn<Part, Double> associatedPartPriceCol;

    @FXML
    private void initialize() {
        idField.setDisable(true);
    }

    @FXML
    void searchBar(){
        displayPartSearchResults(partTable, searchBar);
    }

    @FXML
    void addAssociatedPart() {
        ObservableList<Part> associatedParts;
        associatedParts = associatedPartTable.getItems();
        associatedParts.addAll(partTable.getSelectionModel().getSelectedItems());
        associatedPartTable.setItems(associatedParts);
        associatedPartTable.getSortOrder().add(partIdCol);
    }

    @FXML
    void removeAssociatedPartEvent() {
        Inventory.removeAssociatedPart(associatedPartTable);
    }

    @FXML
    void modifyProductSave(ActionEvent event) {
        saveProduct(idField, nameField, inventoryField, priceField, maxField, minField, true,
                associatedPartTable, event);
    }

    @FXML
    public void switchToMain(ActionEvent event) { Utility.switchScene("/forms/MainForm.fxml", event); }

    public void populateProductValues(Product selectedProduct) {
        idField.setText(String.valueOf(selectedProduct.getId()));
        nameField.setText((selectedProduct.getName()));
        inventoryField.setText(String.valueOf(selectedProduct.getStock()));
        priceField.setText(String.valueOf(selectedProduct.getPrice()));
        maxField.setText((String.valueOf(selectedProduct.getMax())));
        minField.setText((String.valueOf(selectedProduct.getMin())));
        setPartsTable(partTable, partIdCol, partNameCol, partInvLevelCol, partPriceCol,
                SelectionMode.MULTIPLE);
        associatedPartTable.setItems(selectedProduct.getAllAssociatedParts());
        setAssociatedPartsTable(associatedPartTable, associatedPartIdCol, associatedPartInvLevelCol,
                associatedPartNameCol, associatedPartPriceCol);
    }

}
