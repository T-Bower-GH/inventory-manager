package com.example.software1project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static com.example.software1project.Inventory.*;
import static com.example.software1project.ExtraMethods.*;

/**
 * @author Trevor Bower
 */

public class AddPartController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private RadioButton addPartInHouseRadio;

    @FXML
    private RadioButton addPartOutsourceRadio;

    @FXML
    private TextField addPartIdField;

    @FXML
    private TextField addPartNameField;

    @FXML
    private TextField addPartInvField;

    @FXML
    private TextField addPartPriceField;

    @FXML
    private TextField addPartMaxField;

    @FXML
    private TextField addPartMinField;

    @FXML
    private Label partRadioLabel;

    @FXML
    private TextField addPartRadioField;

    @FXML
    private Button addPartSaveButton;

    /**
     * Initializes Add Part form
     */
    @FXML
    private void initialize() {
        addPartIdField.setDisable(true); // Disables the ID field
        addPartIdField.setText("Auto Gen- Disabled");
    }

    /**
     * Sets Radio-button-based label and field for Add Part form when "In-House" radio button selected
     * @param event "In-House" radio button selected (In-House radio button selected by default)
     */
    @FXML
    void partInHouseRadio(ActionEvent event) {
        partRadioLabel.setText("   Machine ID");
        addPartRadioField.setPromptText("Enter Machine ID");
    }

    /**
     * Sets Radio-button-based label and field for Add Part form when "Outsourced" radio button selected
     * @param event "Outsourced" radio button selected
     */
    @FXML
    void partOutsourcedRadio(ActionEvent event) {
        partRadioLabel.setText("   Company Name");
        addPartRadioField.setPromptText("Enter Company Name");
    }

    /**
     * Saves new part info and returns to Main form when Save button is clicked
     * @param event Save button in Add Part form is clicked
     */
    @FXML
    void addPartSave(ActionEvent event) {
        partSave(event); //See "Methods" below
    }

    /**
     * Returns to Main form without saving if Cancel button is clicked
     * @param event Cancel button in Add Part form is clicked
     * @throws Exception Main form unable to load
     */
    public void switchToMain(ActionEvent event) throws Exception {
        mainForm(event); // See ExtraMethods Class
    }


    // Methods:

    /**
     * Creates unique part ID, takes values entered into text fields in Add Part form, checks for appropriate values,
     * and uses those values to create a new part which is entered into allParts. See further comments in method for
     * greater detail.
     * Note that partSave will not provide details about exceptions thrown because each check failed provides them.
     * @param event addPartSave is triggered (See addPartSave)
     */
    public void partSave(ActionEvent event) {
        int newPartId = newPartID(); // Generates a unique part ID
        // Takes the values entered in each text field and stores them in an array
        TextField[] addPartFields = new TextField[]{
                addPartNameField, // Element 0, string
                addPartInvField, // Element 1, int
                addPartPriceField, // Element 2, double
                addPartMaxField, // Element 3, int
                addPartMinField, // Element 4, int
                addPartRadioField // Element 5, int OR string, based on radio button selection
        };

        for (TextField f : addPartFields) {
            f.setStyle("-fx-border-color: #999999"); //Resets red borders from previous errors
        }
        // Checks each field for correct value inputs. If incorrect value is found, the field border turns red,
        // and an error is displayed based on the validation function used. See ExtraMethods Class for check methods.
        checkName(addPartFields[0]);
        checkInt(addPartFields[1]); // Checks Inv
        checkPrice(addPartFields[2]);
        checkInt(addPartFields[3]); // Checks Max
        checkInt(addPartFields[4]); // Checks Min
        if (addPartInHouseRadio.isSelected()) { // In-house selected
            checkInHouse(addPartFields[5]); //Checks Machine ID
        } else { // Outsource selected due to toggle group
            checkName(addPartFields[5]); //Checks Company Name
        }
        try { // After all fields are validated, the values entered in the text fields are parsed, set as their
            // corresponding variables for addPart, the new part is added using addPart, and the user is sent back to
            // Main form via mainForm.
            int id = newPartId;
            String name = addPartNameField.getText().trim();
            int stock = Integer.parseInt(addPartFields[1].getText().trim());
            double price = Double.parseDouble(addPartFields[2].getText().trim());
            int max = Integer.parseInt(addPartFields[3].getText().trim());
            int min = Integer.parseInt(addPartFields[4].getText().trim());
            checkMaxMinInv(addPartFields[3], addPartFields[4], Integer.parseInt(addPartFields[1].getText().trim()));
            if (addPartInHouseRadio.isSelected()) {
                int machineID = Integer.parseInt(addPartFields[5].getText().trim());
                addPart(new InHouse(id, name, price, stock, min, max, machineID));
            } else {
                String companyName = addPartFields[5].getText().trim();
                addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }
            mainForm(event);
        } catch (Exception e) {
        }
    }
}
