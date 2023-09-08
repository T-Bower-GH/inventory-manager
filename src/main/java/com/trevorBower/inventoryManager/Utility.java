package com.trevorBower.inventoryManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import static com.trevorBower.inventoryManager.Inventory.updatePart;

/**
 * @author Trevor Bower
 */
public class Utility {
    // Scene Switchers:

    public static void switchScene(String filePath, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Utility.class.getResource(filePath)));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <ControllerType> void switchSceneWithFunction(String filePath, ActionEvent event,
                                                                Consumer<ControllerType> functionToCall) {
        try {
            FXMLLoader loader = new FXMLLoader(Utility.class.getResource(filePath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            ControllerType controller = loader.getController();
            functionToCall.accept(controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Alert Messages:

    public static void displayError(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public static void displayError(TextField highlightField, String errorMessage){
        highlightField.setStyle("-fx-border-color: red");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public static void displayError(List<TextField> fields, String errorMessage){
        for (TextField field : fields) {
            field.setStyle("-fx-border-color: red");
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public static boolean hasUserConfirmed(String action) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to " + action + "?");
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent() && result.get() == ButtonType.OK);
    }


    // Value Validators:

    public static void checkInt(TextField f) {
        try {
            int value = (Integer.parseInt(f.getText().trim()));
            if (value < 0) {
                displayError("All number values must be positive.");
            }
        } catch (NumberFormatException e) {
            displayError(f, "Highlighted value must be a whole number.");
        }
    }

    public static void checkPrice(TextField f) {
        try {
            double d = Double.parseDouble(f.getText().trim());
            if (d < 0) {
                displayError("All number values must be positive.");
            }
        } catch (Exception e) {
            displayError(f, "Price must be an integer or double.");
        }
    }

    public static void checkMaxMinInv(TextField maxField, TextField minField, TextField inventoryField) {
        int max = Integer.parseInt(maxField.getText().trim());
        int min = Integer.parseInt(minField.getText().trim());
        int inv = Integer.parseInt(inventoryField.getText().trim());
        boolean errorFound = false;
        String errorString = "";
        if (max < min) {
            errorFound = true;
            errorString += "\nMax cannot be less than Min.";
        }
        if (inv < min) {
            errorFound = true;
            errorString += "\nInv cannot be less than Min.";
        }
        if (max < inv) {
            errorFound = true;
            errorString += "\nMax cannot be less than Inv.";
        }
        if (errorFound) {
            List<TextField> errorFields = new ArrayList<>();
            errorFields.add(maxField);
            errorFields.add(minField);
            errorFields.add(inventoryField);
            displayError(errorFields, errorString);
            throw new IllegalArgumentException("Max/Min/Inv input error.");
        }
    }

    public static boolean validateFields(boolean inHouseSelected, TextField... textFields) {
        // textFields format: nameField, inventoryField, priceField, maxField, minField, radioField
        for (TextField field : textFields) {
            field.setStyle("-fx-border-color: #999999"); //Resets red borders from previous errors
            if (field.getText().isEmpty()) {
                Utility.displayError(field, "Field missing value.");
                return false;
            }
        }
        Utility.checkInt(textFields[1]);
        Utility.checkPrice(textFields[2]);
        Utility.checkInt(textFields[3]);
        Utility.checkInt(textFields[4]);
        Utility.checkMaxMinInv(textFields[3], textFields[4], textFields[1]);
        if (inHouseSelected) {
            Utility.checkInt(textFields[5]);
        }

        return true;
    }

    // Save:

    public static void savePart(TextField idField, TextField nameField, TextField inventoryField, TextField priceField,
                                TextField maxField, TextField minField, TextField radioField, boolean inHouseSelected,
                                boolean partExists, ActionEvent event) {
        boolean allFieldsValid = validateFields(inHouseSelected, nameField, inventoryField, priceField, maxField,
                minField, radioField);
        if (!allFieldsValid) {
            return;
        }

        int id = partExists ? Integer.parseInt(idField.getText().trim()) : Inventory.newPartID();
        String name = nameField.getText().trim();
        int stock = Integer.parseInt(inventoryField.getText().trim());
        double price = Double.parseDouble(priceField.getText().trim());
        int max = Integer.parseInt(maxField.getText().trim());
        int min = Integer.parseInt(minField.getText().trim());
        if (inHouseSelected) {
            int machineID = Integer.parseInt(radioField.getText().trim());
            if (partExists) {
                updatePart(id, new InHouse(id, name, price, stock, min, max, machineID));
            } else {
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
            }
        } else {
            String companyName = radioField.getText().trim();
            if (partExists) {
                updatePart(id, new Outsourced(id, name, price, stock, min, max, companyName));
            } else {
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }
        }

        Utility.switchScene("/forms/MainForm.fxml", event);
    }

    public static void saveProduct(TextField idField, TextField nameField, TextField inventoryField,
                                   TextField priceField, TextField maxField, TextField minField, boolean productExists,
                                   TableView<Part> associatedPartTable, ActionEvent event) {
        boolean allFieldsValid = validateFields(false, nameField, inventoryField, priceField, maxField,
                minField);
        if (!allFieldsValid) {
            return;
        }

        int id = productExists ? Integer.parseInt(idField.getText().trim()) : Inventory.newProductID();
        String name = nameField.getText().trim();
        int stock = Integer.parseInt(inventoryField.getText().trim());
        double price = Double.parseDouble(priceField.getText().trim());
        int max = Integer.parseInt(maxField.getText().trim());
        int min = Integer.parseInt(minField.getText().trim());

        Product product = new Product(id, name, price, stock, min, max);
        if (productExists) {
            Inventory.updateProduct(id, product);
        } else {
            Inventory.addProduct(product);
        }

        product.addAssociatedParts(associatedPartTable.getItems());
        Utility.switchScene("/forms/MainForm.fxml", event);
    }

    // Delete

    public static void deleteSelectedPart(TableView<Part> partTable) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        ObservableList<Part> currentParts = partTable.getItems();
        if (selectedPart == null) {
            displayError("Selected value is null. Please check your part selection.");
        }
        if (Utility.hasUserConfirmed("delete selected part")) {
            currentParts.remove(selectedPart);
            partTable.setItems(currentParts);
        }
    }

    public static void deleteSelectedProduct(TableView<Product> productTable) { // If no product is selected
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        ObservableList<Product> currentProducts = productTable.getItems();
        if (selectedProduct == null) {
            displayError("Selected value is null. Please check your product selection.");
        }
        else if (selectedProduct.getAllAssociatedParts() != null) {
            displayError("Product still has associated parts. Please remove all associated parts before " +
                    "deleting.");
        } else {
            if (Utility.hasUserConfirmed("delete selected product")) {
                currentProducts.remove(selectedProduct);
                productTable.setItems(currentProducts);
            }
        }
    }

    // Search:

    public static void displayPartSearchResults(TableView<Part> partTable, TextField partSearchBar){
        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        String search = partSearchBar.getText().trim().toLowerCase();
        for (Part part : Inventory.getAllParts()) {
            String partName = part.getName().toLowerCase();
            String partId = Integer.toString(part.getId());
            if (partName.contains(search) || partId.contains(search)) {
                searchResults.add(part);
            }
        }
        if (searchResults.isEmpty()) {
            displayError("No parts matching search criteria.");
            partTable.setItems(Inventory.getAllParts());
        } else {
            partTable.setItems(searchResults);
        }
    }

    public static void displayProductSearchResults(TableView<Product> productTable, TextField productSearchBar){
        ObservableList<Product> searchResults = FXCollections.observableArrayList();
        String search = productSearchBar.getText().trim().toLowerCase();
        for (Product product : Inventory.getAllProducts()) {
            String productName = product.getName().toLowerCase();
            String productId = Integer.toString(product.getId());
            if (productName.contains(search) || productId.contains(search)) {
                searchResults.add(product);
            }
        }
        if (searchResults.isEmpty()) {
            displayError("No products matching search criteria.");
            productTable.setItems(Inventory.getAllProducts());
        } else {
            productTable.setItems(searchResults);
        }
    }

    // Table Setters:
    public static void setPartsTable(
            TableView<Part> partTable, TableColumn<Part, Integer> partIdCol, TableColumn<Part, String> partNameCol,
            TableColumn<Part, Integer> partInvLevelCol, TableColumn<Part, Double> partPriceCol, SelectionMode mode) {
        partTable.setItems(Inventory.getAllParts());
        partTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        partTable.getSelectionModel().setSelectionMode(mode);
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public static void setProductsTable(
            TableView<Product> productTable, TableColumn<Product, Integer> productIdCol,
            TableColumn<Product, String> productNameCol, TableColumn<Product, Integer> productInvLevelCol,
            TableColumn<Product, Double> productPriceCol) {
        productTable.setItems(Inventory.getAllProducts());
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public static void setAssociatedPartsTable(
            TableView<Part> associatedPartTable, TableColumn<Part, Integer> associatedPartIdCol,
            TableColumn<Part, Integer> associatedPartInvLevelCol, TableColumn<Part, String> associatedPartNameCol,
            TableColumn<Part, Double> associatedPartPriceCol) {
        associatedPartTable.getSortOrder().add(associatedPartIdCol);
        associatedPartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
