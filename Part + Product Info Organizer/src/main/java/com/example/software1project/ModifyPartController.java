package com.example.software1project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import static com.example.software1project.ExtraMethods.*;
import static com.example.software1project.Inventory.*;

/**
 * @author Trevor Bower
 */

public class ModifyPartController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private RadioButton modifyPartInHouseRadio;

    @FXML
    private RadioButton modifyPartOutsourcedRadio;

    @FXML
    private TextField modifyPartIdField;

    @FXML
    private TextField modifyPartNameField;

    @FXML
    private TextField modifyPartInvField;

    @FXML
    private TextField modifyPartPriceField;

    @FXML
    private TextField modifyPartMaxField;

    @FXML
    private TextField modifyPartMinField;

    @FXML
    private Label modifyPartRadioLabel;

    @FXML
    private TextField modifyPartRadioField;

    /**
     * Initializes Modify Part form
     */
    @FXML
    private void initialize() {
        modifyPartIdField.setDisable(true);  // Disables the ID field (fetchPart will populate the field)
    }


    /**
     * Sets Radio-button-based label and field for Modify Part form when "In-House" radio button selected
     * @param event "In-House" radio button selected (In-House radio button selected by default)
     */
    @FXML
    void partInHouseRadio(ActionEvent event) {
        modifyPartRadioLabel.setText("   Machine ID");
        modifyPartRadioField.setPromptText("Enter Machine ID");
    }

    /**
     * Sets Radio-button-based label and field for Modify Part form on event when "Outsourced" radio button selected
     * @param event "Outsourced" radio button selected
     */
    @FXML
    void partOutsourcedRadio(ActionEvent event) {
        modifyPartRadioLabel.setText("   Company Name");
        modifyPartRadioField.setPromptText("Enter Company Name");
    }

    /**
     * Saves modified part info and returns to Main form when Save button is clicked
     * @param event Save button in Modify Part form is clicked
     */
    @FXML
    void modifyPartSave(ActionEvent event) {
        partSave(event); //See "Methods" below
    }

    /**
     * Returns to Main form without saving if Cancel button is clicked
     * @param event Cancel button in Modify Part form is clicked
     * @throws Exception Main form unable to load
     */
    public void switchToMain(ActionEvent event) throws Exception {
        mainForm(event); // See ExtraMethods Class
    }

    //Methods:

    /**
     * Fetches part from partTable to populate Modify Part Screen
     * RUNTIME ERROR: Ran into an error here that stumped me for a while because the name of the button
     *             initialized did not match the name of the actual button in SceneBuilder. Found this was the issue
     *             because any command involving the Outsourced radio button caused issues, and I eventually isolated
     *             the line of code.
     * @param selectedPart Part selected in Parts table in Main form
     */
    public void fetchPart(Part selectedPart) {
        //String.valueOf() used to turn int values back into strings so the appropriate values display in the text fields
        modifyPartIdField.setText(String.valueOf(selectedPart.getId()));
        modifyPartNameField.setText((selectedPart.getName()));
        modifyPartInvField.setText(String.valueOf(selectedPart.getStock()));
        modifyPartPriceField.setText(String.valueOf(selectedPart.getPrice()));
        modifyPartMaxField.setText((String.valueOf(selectedPart.getMax())));
        modifyPartMinField.setText((String.valueOf(selectedPart.getMin())));
        if (selectedPart instanceof InHouse) { // If In-House
            modifyPartOutsourcedRadio.setSelected(false);
            modifyPartInHouseRadio.setSelected(true);
            modifyPartRadioLabel.setText("Machine ID");
            modifyPartRadioField.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        } else { // If Outsourced
            modifyPartInHouseRadio.setSelected(false);
            modifyPartOutsourcedRadio.setSelected(true);
            modifyPartRadioLabel.setText("Company Name");
            modifyPartRadioField.setText(((Outsourced) selectedPart).getCompanyName());
        }
    }

    /**
     * Takes values entered into text fields in Modify Part form, checks for appropriate values, and uses
     * those values to update the existing part values in allParts. See further comments in method for greater detail.
     * Note that partSave will not provide details about exceptions thrown because each check failed provides them.
     * @param event modifyPartSave is triggered (See modifyPartSave above)
     */
    public void partSave(ActionEvent event) {
        // Takes the values entered in each text field and stores them in an array
        TextField[] modifyPartFields = new TextField[]{
                modifyPartNameField, // Element 0, string
                modifyPartInvField, // Element 1, int
                modifyPartPriceField, // Element 2, double
                modifyPartMaxField, // Element 3, int
                modifyPartMinField, // Element 4, int
                modifyPartRadioField // Element 5, int OR string, based on radio button selection
        };

        for (TextField f : modifyPartFields) {
            f.setStyle("-fx-border-color: #999999"); //Resets red borders from previous errors
        }
        // Checks each field for correct value inputs. If incorrect value is found, the field border turns red,
        // and an error is displayed based on the validation function used. See "Extra Methods" class for check methods.
        checkName(modifyPartFields[0]);
        checkInt(modifyPartFields[1]); // Checks Inv
        checkPrice(modifyPartFields[2]);
        checkInt(modifyPartFields[3]); // Checks Max
        checkInt(modifyPartFields[4]); // Checks Min
        if (modifyPartInHouseRadio.isSelected()) { // In-house selected
            checkInHouse(modifyPartFields[5]); //Checks Machine ID
        } else { // Outsource selected due to toggle group
            checkName(modifyPartFields[5]); //Checks Company Name
        }
        try { // After all fields are validated, the values entered in the text fields are parsed, set as their
            // corresponding variables for updatePart, the new part is saved using updatePart, and the user is sent
            // back to Main form via mainForm.
            int id = Integer.parseInt(modifyPartIdField.getText().trim());
            String name = modifyPartNameField.getText().trim();
            int stock = Integer.parseInt(modifyPartFields[1].getText().trim());
            double price = Double.parseDouble(modifyPartFields[2].getText().trim());
            int max = Integer.parseInt(modifyPartFields[3].getText().trim());
            int min = Integer.parseInt(modifyPartFields[4].getText().trim());
            checkMaxMinInv(modifyPartFields[3], modifyPartFields[4], Integer.parseInt(modifyPartFields[1].getText().trim()));
            if (modifyPartInHouseRadio.isSelected()) {
                int machineID = Integer.parseInt(modifyPartFields[5].getText().trim());
                updatePart(id, new InHouse(id, name, price, stock, min, max, machineID));
            } else {
                String companyName = modifyPartFields[5].getText().trim();
                updatePart(id, new Outsourced(id, name, price, stock, min, max, companyName));
            }
            mainForm(event);
        } catch (Exception e) {
        }
    }

}
