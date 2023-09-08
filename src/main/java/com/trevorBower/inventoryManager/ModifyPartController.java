package com.trevorBower.inventoryManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * @author Trevor Bower
 */
public class ModifyPartController {
    @FXML
    private RadioButton inHouseRadioButton;
    @FXML
    private RadioButton outsourcedRadioButton;
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
    void modifyPartSave(ActionEvent event) {
        boolean inHouseSelected = inHouseRadioButton.isSelected();
        Utility.savePart(idField, nameField, inventoryField, priceField,
                maxField, minField, radioField, inHouseSelected, true, event);
    }

    @FXML
    public void switchToMain(ActionEvent event) { Utility.switchScene("/forms/MainForm.fxml", event); }

    public void populatePartValues(Part selectedPart) {
        idField.setText(String.valueOf(selectedPart.getId()));
        nameField.setText((selectedPart.getName()));
        inventoryField.setText(String.valueOf(selectedPart.getStock()));
        priceField.setText(String.valueOf(selectedPart.getPrice()));
        maxField.setText((String.valueOf(selectedPart.getMax())));
        minField.setText((String.valueOf(selectedPart.getMin())));
        if (selectedPart instanceof InHouse) {
            outsourcedRadioButton.setSelected(false);
            inHouseRadioButton.setSelected(true);
            radioLabel.setText("Machine ID");
            radioField.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        } else {
            inHouseRadioButton.setSelected(false);
            outsourcedRadioButton.setSelected(true);
            radioLabel.setText("Company Name");
            radioField.setText(((Outsourced) selectedPart).getCompanyName());
        }
    }
}
