package com.trevorBower.inventoryManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * @author Trevor Bower
 */
public class AddPartController {
    @FXML
    private RadioButton inHouseRadioButton;
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
    private Label radioLabel;
    @FXML
    private TextField radioField;

    @FXML
    private void initialize() {
        idField.setDisable(true);
        idField.setText("Auto Gen- Disabled");
    }

    @FXML
    void partInHouseRadio() {
        radioLabel.setText("   Machine ID");
        radioField.setPromptText("Enter Machine ID");
    }

    @FXML
    void partOutsourcedRadio() {
        radioLabel.setText("   Company Name");
        radioField.setPromptText("Enter Company Name");
    }

    @FXML
    void addPartSave(ActionEvent event) {
        boolean inHouseSelected = inHouseRadioButton.isSelected();
        Utility.savePart(idField, nameField, inventoryField, priceField, maxField,
                minField, radioField, inHouseSelected, false, event);
    }

    @FXML
    public void switchToMain(ActionEvent event) { Utility.switchScene("/forms/MainForm.fxml", event); }
}
