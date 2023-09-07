package com.trevorBower.inventoryManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * @author Trevor Bower
 */
public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList(); // Initializes observable list of all parts
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList(); // Initializes observable list of all products

    private static int partID = 0;
    private static int productID = 0;

    public static int newPartID() {
        return ++partID;
    }
    public static int newProductID() {
        return ++productID;
    }

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static void updatePart(int index, Part selectedPart) {
        for (int i = 0; i < getAllParts().size(); i++) {
            Part part = getAllParts().get(i);
            if (part.getId() == index) {
                getAllParts().set(i, selectedPart);
                return;
            }
        }
    }

    public static void updateProduct(int index, Product selectedProduct){
        for (int i = 0; i < getAllProducts().size(); i++) {
            Product product = getAllProducts().get(i);
            if (product.getId() == index) {
                getAllProducts().set(i, selectedProduct);
                return;
            }
        }
    }

    public static void removeAssociatedPart(TableView<Part> associatedPartTable){
        Part selectedPart = associatedPartTable.getSelectionModel().getSelectedItem();
        ObservableList<Part> currentAssociatedParts = associatedPartTable.getItems();
        if (selectedPart == null) {
            Utility.displayError("Selected value is null. Please check your associated part selection.");
            return;
        }
        if (Utility.hasUserConfirmed("remove selected part from product's associated parts")) {
            currentAssociatedParts.remove(selectedPart);
            associatedPartTable.setItems(currentAssociatedParts);
        }
    }

}
