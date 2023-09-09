package com.trevorBower.inventoryManager;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

import static com.trevorBower.inventoryManager.Utility.*;

/**
 * @author Trevor Bower
 */
public class AddProductController implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idField.setDisable(true);
        idField.setText("Auto Gen- Disabled");
        setPartsTable(partTable, partIdCol, partNameCol, partInvLevelCol, partPriceCol,
                SelectionMode.MULTIPLE);
        setAssociatedPartsTable(associatedPartTable, associatedPartIdCol, associatedPartInvLevelCol,
                associatedPartNameCol, associatedPartPriceCol);
    }

    @FXML
    void addProductSearchBar(){
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
    void addProductSave(ActionEvent event) {
        saveProduct(idField, nameField, inventoryField, priceField, maxField, minField, false,
                associatedPartTable, event);
    }

    @FXML
    public void switchToMain(ActionEvent event) {
        Utility.switchScene("/forms/MainForm.fxml", event);
    }

}