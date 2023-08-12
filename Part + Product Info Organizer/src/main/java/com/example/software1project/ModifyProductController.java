package com.example.software1project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

import static com.example.software1project.Inventory.*;
import static com.example.software1project.ExtraMethods.*;

/**
 * @author Trevor Bower
 */

public class ModifyProductController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    // Entry fields and buttons
    @FXML
    private TextField modifyProductIdField;

    @FXML
    private TextField modifyProductNameField;

    @FXML
    private TextField modifyProductInvField;

    @FXML
    private TextField modifyProductPriceField;

    @FXML
    private TextField modifyProductMaxField;

    @FXML
    private TextField modifyProductMinField;

    @FXML
    private TextField modifyProductSearchBar;

    @FXML
    private Button modifyProductSaveButton;

    @FXML
    private Button modifyProductCancelButton;

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

    @FXML
    private Button addAssociatedPartButton;


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
    private Button removeAssociatedPartButton;

    /**
     * Initializes Modify Product form
     */
    @FXML
    private void initialize() {
        modifyProductIdField.setDisable(true);  // // Disables the ID field (fetchProduct will populate the field)
    }

    /**
     * Searches Parts table by Part ID or name using appropriate text field.
     * If no search matches are found, an error is displayed, and partTable is repopulated with allParts.
     * See "Methods" below for utilized search methods.
     * @param event Value entered into search bar in Modify Product form
     */
    @FXML
    void modifyProductSearchBar(ActionEvent event){
        if (partMatchSearch(modifyProductSearchBar.getText())) {
            searchPart(modifyProductSearchBar, partTable);
        } else { // If no search matches are found...
            Alert alert = new Alert(Alert.AlertType.ERROR); // Displays error
            alert.setTitle("No matching parts found");
            alert.setHeaderText(null);
            alert.setContentText("There are no parts matching the search criteria.");
            alert.showAndWait();
            partTable.setItems(getAllParts()); // And returns all parts to partTable
        }
    }

    /**
     * Adds associatedPart to associatedPartTable when addAssociatedPartButton is clicked
     * @param event Add button above Associated Parts table in Modify Product form is clicked
     */
    @FXML
    void addAssociatedPart(ActionEvent event) {
        ObservableList<Part> associatedParts;
        associatedParts = associatedPartTable.getItems();
        associatedParts.addAll(partTable.getSelectionModel().getSelectedItems());
        associatedPartTable.setItems(associatedParts);
        associatedPartTable.getSortOrder().add(partIdCol);
    }

    /**
     * Removes the selected associated part from associatedPartTable. Confirmation required before part is deleted
     * If no part is selected, an error is displayed.
     * @param event Remove Associated Part button in Modify Product form is clicked
     */
    @FXML
    void removeAssociatedPart(ActionEvent event) {
        if (associatedPartTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Selected value is null. Please check your associated part selection.");
            alert.showAndWait();
        }
        else {
            // Confirmation of deletion required before part is deleted
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you want to remove the selected associated part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                deletePart(associatedPartTable.getSelectionModel().getSelectedItem(), associatedPartTable.getItems());
                associatedPartTable.setItems(associatedPartTable.getItems());
            }
        }
    }

    /**
     * Saves updated product info when Save button is clicked
     * //See "Methods" below for productSave
     * @param event Save button is clicked
     * @throws Exception Main form fails to load
     */
    @FXML
    void modifyProductSave(ActionEvent event) throws Exception {
        productSave(event); // See "Methods" below
    }

    /**
     * Returns to Main form without saving if Cancel button is clicked
     * See ExtraMethods Class for mainForm
     * @param event Cancel button in Modify Product form is clicked
     * @throws Exception Main form fails to load
     */
    public void switchToMain(ActionEvent event) throws Exception {
        mainForm(event); //See "Extra Methods" Class
    }


    // Methods:

    /**
     * Fetches part from partTable to populate Modify Product Screen
     * @param selectedProduct Product selected in Products table in Main form
     */
    public void fetchProduct(Product selectedProduct) {
        modifyProductIdField.setText(String.valueOf(selectedProduct.getId()));
        modifyProductNameField.setText((selectedProduct.getName()));
        modifyProductInvField.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductPriceField.setText(String.valueOf(selectedProduct.getPrice()));
        modifyProductMaxField.setText((String.valueOf(selectedProduct.getMax())));
        modifyProductMinField.setText((String.valueOf(selectedProduct.getMin())));
        setModifyProductPartsTable(partTable, partIdCol, partNameCol, partInvLevelCol, partPriceCol, SelectionMode.MULTIPLE);
        setAssociatedPartTable(associatedPartTable, associatedPartIdCol, associatedPartInvLevelCol, associatedPartNameCol, associatedPartPriceCol, selectedProduct);
    }

    /**
     * Sets values for partTable. Multiple selection mode added to allow multiple associated parts to be added at once.
     * Brings values from allParts and not partTable from Main form, although the values will be the same.
     * @param partTable Part table being displayed in Modify Product form
     * @param partNameCol Part Name Column in Part table being displayed in Modify Product form
     * @param partIdCol Part ID Column in Part table being displayed in Modify Product form
     * @param partInvLevelCol Inventory Level Column in Part table being displayed in Modify Product form
     * @param partPriceCol Price/ Cost per Unit Column in Part table being displayed in Modify Product form
     * @param mode Selection Mode for Part table being displayed in Modify Product form
     */
    public static void setModifyProductPartsTable(TableView<Part> partTable, TableColumn<Part, Integer> partIdCol, TableColumn<Part, String> partNameCol, TableColumn<Part, Integer> partInvLevelCol, TableColumn<Part, Double> partPriceCol, SelectionMode mode) {
        partTable.setItems(getAllParts());
        partTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        partTable.getSelectionModel().setSelectionMode(mode); // Added to allow multiple selection mode
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Sets values for associatedPartTable in Modify Product form using values from associatedParts
     * @param associatedPartTable Associated Parts table being displayed in Modify Product form
     * @param associatedPartNameCol Part Name Column in the Associated Parts table being displayed in Modify Product form
     * @param associatedPartIdCol Part ID Column in the Associated Parts table being displayed in Modify
     *                            Product form
     * @param associatedPartInvLevelCol Inventory Level Column in the Associated Parts table being displayed in Modify
     *                                  Product form
     * @param associatedPartPriceCol Price/ Cost per Unit Column in the Associated Parts table being displayed in Modify
     *                               Product form
     */
    public static void setAssociatedPartTable(TableView<Part> associatedPartTable, TableColumn<Part, Integer> associatedPartIdCol, TableColumn<Part, Integer> associatedPartInvLevelCol, TableColumn<Part, String> associatedPartNameCol, TableColumn<Part, Double> associatedPartPriceCol, Product product) {
        associatedPartTable.setItems(product.getAllAssociatedParts());
        associatedPartTable.getSortOrder().add(associatedPartIdCol);
        associatedPartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    // Part search:
    /**
     * Populates Parts table in Modify Product form with parts that have part names that match the value entered
     * into search bar in Modify Product form. If no matches are found, allParts is displayed.
     * @param search Value entered into search bar in Modify Product form
     * @return Parts with matching part names to entered value, or all parts if no matches are found
     */
    public static ObservableList<Part> searchPartValue(String search) {
        ObservableList<Part> tempPartsList = FXCollections.observableArrayList();
        search = search.trim().toLowerCase();
        for (Part part : getAllParts()) {
            if (part.getName().toLowerCase().contains(search)) {
                tempPartsList.add(part);
            }
        }
        if (tempPartsList.isEmpty()) {
            return getAllParts();
        }
        else {
            return tempPartsList;
        }
    }

    /**
     * If a part name match is not found by searchPartValue, then parts with a matching part number are highlighted
     * in the Parts table in the Modify Product form
     * @param modifyProductSearchBar Value entered into search bar in Modify Product form
     * @param partTable Part table in Modify Product form
     */
    public static void searchPart(TextField modifyProductSearchBar, TableView<Part> partTable) {
        try {
            ObservableList<Part> searchResults = searchPartValue(modifyProductSearchBar.getText());
            partTable.setItems(searchResults);

            // Highlight the matching part in the table
            int selectedPartIndex = -1;
            for (int i = 0; i < searchResults.size(); i++) {
                if (searchResults.get(i).getId() == Integer.parseInt(modifyProductSearchBar.getText())) {
                    selectedPartIndex = i;
                    break;
                }
            }
            if (selectedPartIndex != -1) {
                partTable.getSelectionModel().select(selectedPartIndex);
            }
        }
        catch ( Exception e) {
        }
    }

    /**
     * Determines if value entered into search bar in Modify Product form matches a Part ID or Part Name of a part in the
     * Parts table in the Modify Product form
     * @param search Value entered into search bar in Modify Product form
     * @return True if either a Part ID or Part Name search match is found; false if no matches are found
     */
    public static boolean partMatchSearch(String search) {
        search = search.trim().toLowerCase();
        for (Part part : getAllParts()) {
            if (part.getName().toLowerCase().contains(search) || Integer.toString(part.getId()).contains(search)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes values entered into text fields in Modify Product form, checks for appropriate values, and uses those
     * values to update the existing product values in allProducts. See further comments in method for greater detail.
     * Note that productSave will not provide details about exceptions thrown because each check failed provides them.
     * @param event modifyProductSave is triggered (See modifyProductSave above)
     */
    @FXML
    void productSave(ActionEvent event) throws IOException {
        // Takes the values entered in each text field and stores them in an array
        TextField[] modifyProductFields = new TextField[]{
                modifyProductNameField, // Element 0, string
                modifyProductInvField, // Element 1, int
                modifyProductPriceField, // Element 2, double
                modifyProductMaxField, // Element 3, int
                modifyProductMinField, // Element 4, int
        };

        for (TextField f : modifyProductFields) {
            f.setStyle("-fx-border-color: #999999"); //Resets red borders from previous errors
        }

        // Checks each field for correct value inputs. If incorrect value is found, the field border turns red,
        // and an error is displayed based on the validation function used. See "Extra Methods" class for check methods.
        checkName(modifyProductFields[0]);
        checkInt(modifyProductFields[1]); // Checks Inv
        checkPrice(modifyProductFields[2]);
        checkInt(modifyProductFields[3]); // Checks Max
        checkInt(modifyProductFields[4]); // Checks Min
        try { // After all fields are validated, the values entered in the text fields are parsed, set as their
            // corresponding variables for modifyProduct, the new part is added using modifyProduct, and the user is sent back to
            // Main form via mainForm.
            int id = Integer.parseInt(modifyProductIdField.getText().trim());
            String name = modifyProductNameField.getText().trim();
            int stock = Integer.parseInt(modifyProductFields[1].getText().trim());
            double price = Double.parseDouble(modifyProductFields[2].getText().trim());
            int max = Integer.parseInt(modifyProductFields[3].getText().trim());
            int min = Integer.parseInt(modifyProductFields[4].getText().trim());
            checkMaxMinInv(modifyProductFields[3], modifyProductFields[4], Integer.parseInt(modifyProductFields[1].getText().trim()));
            updateProduct(id, new Product(id, name, price, stock, min, max)); // See below
            lookupProduct(id).addAssociatedPart(associatedPartTable.getItems()); // Saves associated parts to product
            mainForm(event);
        } catch (Exception e) {
        }
    }

}
