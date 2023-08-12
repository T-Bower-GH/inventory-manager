package com.example.software1project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.software1project.Inventory.*;
import static com.example.software1project.ExtraMethods.*;

public class MainFormController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;


    // Main Pane + Button
    @FXML
    private GridPane mainPane;

    @FXML
    private Button exitButton;


    // Part Table
    @FXML
    private TableView<Part> partTable;

    /**
     * // RUNTIME ERROR: Tried using "int" instead of "Integer"
     */
    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvLevelCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private Button partAddButton;

    @FXML
    private Button partModifyButton;

    @FXML
    private Button partDeleteButton;

    @FXML
    private TextField partSearchBar;


    // Product Table
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
    private Button productAddButton;

    @FXML
    private Button productModifyButton;

    @FXML
    private Button productDeleteButton;

    @FXML
    private TextField productSearchBar;

    /**
     * Populates data for both tables in main form
     * // RUNTIME ERROR: Encountered error here because I accidentally misnamed fx:id of "productPriceCol"
     * as "prodctPriceCol" in SceneBuilder
     * @param url Data for Main form tables
     * @param rb Data for Main form tables
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setAllPartsTable(partTable, partIdCol, partNameCol, partInvLevelCol, partPriceCol); // See "Table Value Methods" below
        setAllProductsTable(productTable, productIdCol, productNameCol, productInvLevelCol, productPriceCol); // See "Table Value Methods" below
    }

    // Part Event Methods:

    /**
     * Switches to Add Part form when Add button in Parts pane is clicked
     * @param event Add button in Parts pane is clicked
     * @throws Exception Add Part form fails to load
     */
    public void switchToAddPart(ActionEvent event) throws Exception {
        addPartForm(event); //See "Extra Methods" Class
    }

    /**
     * Switches to Modify Part form when Modify button in Parts pane is clicked
     * See "Form-Changing Methods" below for modifyPartForm
     * @param event Modify button in Parts pane is clicked
     * @throws Exception Modify Part form fails to load
     */
    public void switchToModifyPart(ActionEvent event) throws Exception {
        modifyPartForm(event); // See "Form-Changing Methods" below
    }

    /**
     * Deletes selected part in Part table in Main form
     * See "Part Function Methods" below for deleteSelectedPart
     * @param event Delete button in Parts pane is clicked
     */
    @FXML
    void partDelete(ActionEvent event) {
        deleteSelectedPart(partTable); // See "Part Function Methods" below
    }

    /**
     * Searches Parts table by Part ID or name using search bar in Parts pane in Main form
     * See "Part Function Methods" below for partMatchSearch and searchPart
     * @param event Value is entered into search bar in Parts pane in Main form
     */
    @FXML
    void partSearchBar(ActionEvent event) {
        if (partMatchSearch(partSearchBar.getText())) { // See "Part Function Methods" below
            // If an ID match is found, result will be highlighted among all parts.
            // If a name match is found, non-matching parts will be filtered out.
            searchPart(partSearchBar, partTable); // See "Part Function Methods" below
        } else { // If no search matches are found...
            Alert alert = new Alert(Alert.AlertType.ERROR); // ...displays error...
            alert.setTitle("No matching parts found");
            alert.setHeaderText(null);
            alert.setContentText("There are no parts matching the search criteria.");
            alert.showAndWait();
            partTable.setItems(getAllParts()); // ...and returns all parts to partTable.
        }
    }


    // Product Event Methods:

    /**
     * Switches to Add Product form when Add button in Products pane of Main form is clicked
     * See "Extra Methods" Class for addProductForm
     * @param event Add button in Products pane of Main form is clicked
     * @throws Exception Add Product form fails to load
     */
    public void switchToAddProduct(ActionEvent event) throws Exception {
        addProductForm(event); // See "Extra Methods" Class
    }

    /**
     * Switches to Modify Product form when Modify button in Products pane of Main form is clicked
     * @param event Modify button in Products pane of Main form is clicked
     * @throws Exception Modify Product form fails to load
     */
    public void switchToModifyProduct(ActionEvent event) throws Exception {
        modifyProductForm(event); // See "Form-Changing Methods" below
    }

    /**
     * Deletes selected part in Product table in Main form
     * See "Product Function Methods" below for deleteSelectedProduct
     * @param event Delete button in Product pane of Main form is clicked
     */
    @FXML
    void productDelete(ActionEvent event) {
        deleteSelectedProduct(productTable); // See "Product Function Methods" below
    }

    /**
     * Searches Products table by Product ID or name using search bar in Products pane in Main form
     *  See "Part Function Methods" below for productMatchSearch and searchProduct
     * @param event Value is entered into search bar in Parts pane in Main form
     */
    @FXML
    void productSearchBar(ActionEvent event) {
        if (productMatchSearch(productSearchBar.getText())) { // See "Product Function Methods" below
            // If an ID match is found, result will be highlighted among all products.
            // If a name match is found, non-matching products will be filtered out.
            searchProduct(productSearchBar, productTable); // See "Product Function Methods" below
        } else { // If no search matches are found...
            Alert alert = new Alert(Alert.AlertType.ERROR); // ...displays error...
            alert.setTitle("No matching products found");
            alert.setHeaderText(null);
            alert.setContentText("There are no products matching the search criteria.");
            alert.showAndWait();
            productTable.setItems(getAllProducts()); // ...and returns all products to productTable.
        }
    }

    // Other Event Methods:

    /**
     * Exits program if Exit button is clicked in Main form
     * @param event Exit button is clicked in Main form
     */
    public void mainExit(ActionEvent event) {
        // Confirmation required before program closes
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to close the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) { // If user confirms...
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close(); // ...the program closes.
            System.out.println("The program has successfully closed. Have a great day!");
        }
    }

    // Table Value Methods:

    /**
     * Sets values for Parts table in Parts pane in Main form based on allParts
     * @param partTable Parts table in Parts pane in Main form
     * @param partIdCol Part ID Column in Parts table in Parts pane in Main form
     * @param partNameCol Part Name Column in Parts table in Parts pane in Main form
     * @param partInvLevelCol Inventory Level Column in Parts table in Parts pane in Main form
     * @param partPriceCol Price/ Cost per Unit Column in Parts table in Parts pane in Main form
     */
    public static void setAllPartsTable(TableView<Part> partTable, TableColumn<Part, Integer> partIdCol, TableColumn<Part, String> partNameCol, TableColumn<Part, Integer> partInvLevelCol, TableColumn<Part, Double> partPriceCol) {
        partTable.setItems(getAllParts()); // Retrieves values for all parts and sets those values into the partTable view
        partTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Makes sure columns are appropriate size
        // Sets the cell data in each column with the appropriate values
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Sets values for Products table in Products pane in Main form based on allProducts
     * RUNTIME ERROR: Encountered a runtime error that stumped me for almost an hour because I forgot to bracket
     *      this off.
     * @param productTable Products table in Products pane in Main form
     * @param productIdCol Products ID Column in Products table in Products pane in Main form
     * @param productNameCol Products Name Column in Products table in Products pane in Main form
     * @param productInvLevelCol Inventory Level Column in Products table in Products pane in Main form
     * @param productPriceCol Price/ Cost per Unit Column in Products table in Products pane in Main form
     */
    public static void setAllProductsTable(TableView<Product> productTable, TableColumn<Product, Integer> productIdCol, TableColumn<Product, String> productNameCol, TableColumn<Product, Integer> productInvLevelCol, TableColumn<Product, Double> productPriceCol) {
        productTable.setItems(getAllProducts()); // Retrieves values for all products and sets those values into the productTable view
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Makes sure columns are appropriate size
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id")); // Sets the cell data in each column with the appropriate values
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    // Form-Changing Methods:

    /**
     * Opens Modify Part form if Modify button in Parts pane in Main form is clicked and a part in the Parts table is
     * selected, or displays error if part is not selected
     * RUNTIME ERROR: Encountered error here because I forgot to assign the controller (ModifyPartController) in
     *                SceneBuilder
     * @param event Modify button in Parts pane in Main form is clicked
     * @throws Exception Modify Part form fails to load
     */
    public void modifyPartForm(ActionEvent event) throws Exception {
        if (partTable.getSelectionModel().getSelectedItem() == null) { // If a part is not selected
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Please select a part to modify.");
            alert.showAndWait();
        } else {  // If a part is selected, the Modify Part form opens with text fields populated with appropriate values for the part
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPartForm.fxml")); // Gets info for Modify Part interface
            Parent root = loader.load();
            ModifyPartController controller = loader.getController(); // Retrieves data from ModifyPartController to enable fetchPart method
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            controller.fetchPart(partTable.getSelectionModel().getSelectedItem()); //See "Methods" section in ModifyPartController class
        }
    }

    /**
     * Opens Modify Product form if Modify button in Products pane in Main form is clicked and a product in the
     * Products table is selected, or displays error if product is not selected
     * @param event Modify button in Product pane in Main form is clicked
     * @throws Exception Modify Product form fails to load
     */
    public void modifyProductForm(ActionEvent event) throws Exception {
        if (productTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Please select a product to modify.");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProductForm.fxml")); // Gets info for Modify Product interface
            Parent root = loader.load();
            ModifyProductController controller = loader.getController(); // Retrieves data from ModifyProductController to enable fetchProduct method
            // Begins to display Modify Product Interface
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            controller.fetchProduct(productTable.getSelectionModel().getSelectedItem());
        }
    }


    // Part Function Methods:

    /**
     * Deletes selected part after confirmation. If no part is selected, an error is displayed.
     * @param partTable Part table of Main form
     */
    public static void deleteSelectedPart(TableView<Part> partTable) {
        if (partTable.getSelectionModel().getSelectedItem() == null) { // If no part is selected
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Selected value is null. Please check your part selection.");
            alert.showAndWait();
        } else {
            // Confirmation of deletion required before part is deleted
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you want to delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) { // If user confirms...
                deletePart(partTable.getSelectionModel().getSelectedItem(), partTable.getItems()); // ...the part is deleted...
                partTable.setItems(partTable.getItems()); // ...and partTable is updated.
            }
        }
    }

    // Part search:
    /**
     * Populates Part table in Main form with parts that have part names that match the value entered
     * into search bar in Parts pane of Main form. If no matches are found, allParts is displayed.
     * @param search Value entered into search bar in the Part pane of the Main form
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
        } else {
            return tempPartsList;
        }
    }

    /**
     * If a part name match is not found by searchPartValue, then parts with a matching part number are highlighted
     * in the Parts table in the Main form
     * RUNTIME ERROR: Technically a bug, not an error, occurred here that cancelled the filter-by-string because
     *             I originally had partTable.setItems(getAllParts()) here, and it kept overriding the search results.
     * @param partSearchBar Value entered into search bar in Parts pane of the Main form
     * @param partTable Part table in Main form
     */
    public static void searchPart(TextField partSearchBar, TableView<Part> partTable) {
        try {
            ObservableList<Part> searchResults = searchPartValue(partSearchBar.getText());
            partTable.setItems(searchResults);

            // Highlight the matching part in the table
            int selectedPartIndex = -1;
            for (int i = 0; i < searchResults.size(); i++) {
                if (searchResults.get(i).getId() == Integer.parseInt(partSearchBar.getText())) {
                    selectedPartIndex = i;
                    break;
                }
            }
            if (selectedPartIndex != -1) {
                partTable.getSelectionModel().select(selectedPartIndex);
            }
        } catch (Exception e) {
        }
    }

    /**
     * Determines if value entered into search bar in the Parts pane of the Main form matches a Part ID or Part Name
     * of a part in the Parts table in the Main form
     * @param search Value entered into search bar in the Parts pane of the Main form
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

    // Product Function Methods:

    /**
     * Deletes selected product after confirmation. If no product is selected or the product has associated parts,
     * an error is displayed.
     * @param productTable Part table of Main form
     */
    public static void deleteSelectedProduct(TableView<Product> productTable) { // If no product is selected
        if (productTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Selected value is null. Please check your product selection.");
            alert.showAndWait();
        }
        if (checkAssociatedParts(productTable.getSelectionModel().getSelectedItem())) { // If a product has associated parts
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Product still has associated parts. Please remove all associated parts before deleting.");
            alert.showAndWait();
        } else {
            // Confirmation of deletion required before product is deleted
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you want to delete the selected product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) { // If user confirms...
                deleteProduct(productTable.getSelectionModel().getSelectedItem(), productTable.getItems()); // ...the product is deleted...
                productTable.setItems(productTable.getItems()); // ...and productTable is updated.
            }
        }
    }

    // Product search:
    /**
     * Populates Product table in Main form with parts that have part names that match the value entered
     * into search bar in Product pane of Main form. If no matches are found, allProducts is displayed.
     * @param search Value entered into search bar in the Product pane of the Main form
     * @return Products with matching product names to entered value, or all products if no matches are found
     */
    public static ObservableList<Product> searchProductValue(String search) {
        ObservableList<Product> tempProductsList = FXCollections.observableArrayList();
        search = search.trim().toLowerCase();
        for (Product product : getAllProducts()) {
            if (product.getName().toLowerCase().contains(search)) {
                tempProductsList.add(product);
            }
        }
        if (tempProductsList.isEmpty()) {
            return getAllProducts();
        } else {
            return tempProductsList;
        }
    }

    /**
     * If a product name match is not found by searchProductValue, then product with a matching product number are
     * highlighted in the Products table in the Main form
     * @param productSearchBar Value entered into search bar in Products pane of the Main form
     * @param productTable Product table in Main form
     */
    public static void searchProduct(TextField productSearchBar, TableView<Product> productTable) {
        try {
            ObservableList<Product> searchResults = searchProductValue(productSearchBar.getText());
            productTable.setItems(searchResults);

            // Highlight the matching product in the table
            int selectedProductIndex = -1;
            for (int i = 0; i < searchResults.size(); i++) {
                if (searchResults.get(i).getId() == Integer.parseInt(productSearchBar.getText())) {
                    selectedProductIndex = i;
                    break;
                }
            }
            if (selectedProductIndex != -1) {
                productTable.getSelectionModel().select(selectedProductIndex);
            }
        } catch (Exception e) {
        }
    }

    /**
     * Determines if value entered into search bar in the Products pane of the Main form matches a Product ID or
     * Product Name of a product in the Product table in the Main form
     * @param search Value entered into search bar in the Products pane of the Main form
     * @return True if either a Product ID or Product Name search match is found; false if no matches are found
     */
    public static boolean productMatchSearch(String search) {
        search = search.trim().toLowerCase();
        for (Product product : getAllProducts()) {
            if (product.getName().toLowerCase().contains(search) || Integer.toString(product.getId()).contains(search)) {
                return true;
            }
        }
        return false;
    }

}
