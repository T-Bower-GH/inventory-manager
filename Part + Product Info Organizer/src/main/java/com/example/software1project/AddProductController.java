package com.example.software1project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.software1project.Inventory.*;
import static com.example.software1project.ExtraMethods.*;

/**
 * @author Trevor Bower
 */

public class AddProductController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;


    // Entry fields and buttons
    @FXML
    private TextField addProductIdField;

    @FXML
    private TextField addProductNameField;

    @FXML
    private TextField addProductInvField;

    @FXML
    private TextField addProductPriceField;

    @FXML
    private TextField addProductMaxField;

    @FXML
    private TextField addProductMinField;

    @FXML
    private TextField addProductSearchBar;

    @FXML
    private Button addProductSaveButton;

    @FXML
    private Button addProductCancelButton;


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
    private Button addProductRemoveButton;

    /**
     * Initializes Add Product form
     * @param url Data for Add Product form tables
     * @param rb Data for Add Product form tables
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addProductIdField.setDisable(true); // Disables the ID field
        addProductIdField.setText("Auto Gen- Disabled");
        // Calls methods to populate partTable and associatedParts table views. See "Methods" below
        setAddProductPartsTable(partTable, partIdCol, partNameCol, partInvLevelCol, partPriceCol, SelectionMode.MULTIPLE);
        setAssociatedPartTable(associatedPartTable, associatedPartIdCol, associatedPartInvLevelCol, associatedPartNameCol, associatedPartPriceCol);
    }

    /**
     * Searches Parts table by Part ID or name using appropriate text field.
     * If no search matches are found, an error is displayed, and partTable is repopulated with allParts.
     * See "Methods" below for utilized search methods.
     * @param event Value entered into search bar in Add Product form
     */
    @FXML
    void addProductSearchBar(ActionEvent event){
        if (partMatchSearch(addProductSearchBar.getText())) {
            searchPart(addProductSearchBar, partTable);
        } else { // If no search matches are found...
            Alert alert = new Alert(Alert.AlertType.ERROR); // ...displays error...
            alert.setTitle("No matching parts found");
            alert.setHeaderText(null);
            alert.setContentText("There are no parts matching the search criteria.");
            alert.showAndWait();
            partTable.setItems(getAllParts()); // ...and returns all parts to partTable
        }
    }

    /**
     * Adds associatedPart to associatedPartTable when addAssociatedPartButton is clicked
     * @param event Add button above Associated Parts table in Add Product form is clicked
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
     * @param event Remove Associated Part button in Add Product form is clicked
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
     * Saves new product info when Save button is clicked
     * // See "Methods" below for productSave
     * @param event Save button is clicked
     * @throws Exception Main form fails to load
     */
    @FXML
    void addProductSave(ActionEvent event) throws Exception {
        productSave(event); // See "Methods" below
    }

    /**
     * Returns to Main form without saving if Cancel button is clicked
     * See ExtraMethods Class for mainForm
     * @param event Cancel button in Add Product form is clicked
     * @throws Exception Main form fails to load
     */
    public void switchToMain(ActionEvent event) throws Exception {
        mainForm(event); // See Extra Methods Class
    }

    // Methods:

    /**
     * Sets values for partTable. Multiple selection mode added to allow multiple associated parts to be added at once.
     * Brings values from allParts and not partTable from Main form, although the values will be the same.
     * RUNTIME ERROR: Originally, I named this table addProductAllPartsTable but changed the name to partTable, since
     *         the values would be consistent with partTable in the Main form, but I forgot to update the name in this method.
     * @param partTable Part table being displayed in Add Product form
     * @param partNameCol Part Name Column in Part table being displayed in Add Product form
     * @param partIdCol Part ID Column in Part table being displayed in Add Product form
     * @param partInvLevelCol Inventory Level Column in Part table being displayed in Add Product form
     * @param partPriceCol Price/ Cost per Unit Column in Part table being displayed in Add Product form
     * @param mode Selection Mode for Part table being displayed in Add Product form
     */
    public static void setAddProductPartsTable(TableView<Part> partTable, TableColumn<Part, Integer> partIdCol, TableColumn<Part, String> partNameCol, TableColumn<Part, Integer> partInvLevelCol, TableColumn<Part, Double> partPriceCol, SelectionMode mode) {
        partTable.setItems(getAllParts());

        partTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        partTable.getSelectionModel().setSelectionMode(mode); //Added to allow multiple selection mode
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Sets values for associatedPartTable in Add Product form using values from associatedParts
     * @param associatedPartTable Associated Parts table being displayed in Add Product form
     * @param associatedPartNameCol Part Name Column in the Associated Parts table being displayed in Add Product form
     * @param associatedPartIdCol Part ID Column in the Associated Parts table being displayed in Add
     *                            Product form
     * @param associatedPartInvLevelCol Inventory Level Column in the Associated Parts table being displayed in Add
     *                                  Product form
     * @param associatedPartPriceCol Price/ Cost per Unit Column in the Associated Parts table being displayed in Add
     *                               Product form
     */
    public static void setAssociatedPartTable(TableView<Part> associatedPartTable, TableColumn<Part, Integer> associatedPartIdCol, TableColumn<Part, Integer> associatedPartInvLevelCol, TableColumn<Part, String> associatedPartNameCol, TableColumn<Part, Double> associatedPartPriceCol) {
        associatedPartTable.getSortOrder().add(associatedPartIdCol);
        associatedPartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    // Part search:
    /**
     * Populates Parts table in Add Product form with parts that have part names that match the value entered
     * into search bar in Add Product form. If no matches are found, allParts is displayed.
     * @param search Value entered into search bar in Add Product form
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
     * in the Parts table in the Add Product form
     * @param addProductSearchBar Value entered into search bar in Add Product form
     * @param partTable Part table in Add Product form
     */
    public static void searchPart(TextField addProductSearchBar, TableView<Part> partTable) {
        try {
            ObservableList<Part> searchResults = searchPartValue(addProductSearchBar.getText());
            partTable.setItems(searchResults);

            // Highlight the matching part in the table
            int selectedPartIndex = -1;
            for (int i = 0; i < searchResults.size(); i++) {
                if (searchResults.get(i).getId() == Integer.parseInt(addProductSearchBar.getText())) {
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
     * Determines if value entered into search bar in Add Product form matches a Part ID or Part Name of a part in the
     * Parts table in the Add Product form
     * @param search Value entered into search bar in Add Product form
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
     * Creates unique product ID, takes values entered into text fields in Add Product form, checks for appropriate
     * values, and uses those values to create a new product which is entered into allParts. See further comments
     * in method for greater detail.
     * Note that productSave will not provide details about exceptions thrown because each check failed provides them.
     * @param event addProductSave is triggered (See addProductSave)
     */
    public void productSave(ActionEvent event) {
        int newProductId = newProductID(); // Generates a unique product ID
        // Takes the values entered in each text field and stores them in an array
        TextField[] addProductFields = new TextField[]{
                addProductNameField, // Element 0, string
                addProductInvField, // Element 1, int
                addProductPriceField, // Element 2, double
                addProductMaxField, // Element 3, int
                addProductMinField, // Element 4, int
        };

        for (TextField f : addProductFields) {
            f.setStyle("-fx-border-color: #999999"); //Resets red borders from previous errors
        }

        // Checks each field for correct value inputs. If incorrect value is found, the field border turns red,
        // and an error is displayed based on the validation function used. See "Extra Methods" class for check methods.
        checkName(addProductFields[0]);
        checkInt(addProductFields[1]); // Checks Inv
        checkPrice(addProductFields[2]);
        checkInt(addProductFields[3]); // Checks Max
        checkInt(addProductFields[4]); // Checks Min
        try { // After all fields are validated, the values entered in the text fields are parsed, set as their
            // corresponding variables for addProduct, the new part is added using addProduct, and the user is sent back to
            // Main form via mainForm.
            int id = newProductId;
            String name = addProductNameField.getText().trim();
            int stock = Integer.parseInt(addProductFields[1].getText().trim());
            double price = Double.parseDouble(addProductFields[2].getText().trim());
            int max = Integer.parseInt(addProductFields[3].getText().trim());
            int min = Integer.parseInt(addProductFields[4].getText().trim());
            checkMaxMinInv(addProductFields[3], addProductFields[4], Integer.parseInt(addProductFields[1].getText().trim()));
            addProduct(new Product(id, name, price, stock, min, max)); // See "Extra Methods" class
            lookupProduct(id).addAssociatedPart(associatedPartTable.getItems()); // Saves associated parts to product
            mainForm(event);
        } catch (Exception e) {
        }
    }
}
