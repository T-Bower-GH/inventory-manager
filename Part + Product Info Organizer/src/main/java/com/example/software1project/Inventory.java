package com.example.software1project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Objects;

/**
 * @author Trevor Bower
 */

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList(); // Initializes observable list of all parts
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList(); // Initializes observable list of all products


    /**
     * Adds new part to allParts
     * @param newPart New part being added
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    private static int partID = 0; // Initial value for incrementing Part ID

    /**
     * Increments partID
     * @return partID + 1
     */
    public static int newPartID() {
        return ++partID;
    }


    /**
     * Adds new product to allProducts
     * @param newProduct New product being added
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    private static int productID = 0; // Initial value for incrementing Product ID

    /**
     * Increments productID
     * @return productID + 1
     */
    public static int newProductID() {
        return ++productID;
    }


    /**
     * Looks through allParts to find part with matching partId
     * @param partId Part ID of expected part
     * @return Part information with matching Part ID, or null if no match is found
     */
    public static Part lookupPart(int partId) {
        if (!allParts.isEmpty())
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getId() == partId) return allParts.get(i);
            }
        return null;
    }

    /**
     * Looks through allProducts to find part with matching productId
     * @param productId Product ID of expected product
     * @return Product information with matching Product ID, or null if no match is found
     */
    public static Product lookupProduct(int productId) {
        if (!allProducts.isEmpty())
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getId() == productId) return allProducts.get(i);
            }
        return null;
    }

    /**
     * Looks through allParts to find part with matching partName
     * @param partName Part Name of expected part
     * @return Part information with matching Part name, or null if no match is found
     */
    public static Part lookupPart(String partName) {
        if (!allParts.isEmpty())
            for (int i = 0; i < allParts.size(); i++) {
                if (Objects.equals(allParts.get(i).getName(), partName)) return allParts.get(i);
            }
        return null;
    }

    /**
     * Looks through allProducts to find part with matching productName
     * @param productName Product Name of expected product
     * @return Product information with matching Product Name, or null if no match is found
     */
    public static Product lookupProduct(String productName) {
        if (!allProducts.isEmpty())
            for (int i = 0; i < allProducts.size(); i++) {
                if (Objects.equals(allParts.get(i).getName(), productName)) return allProducts.get(i);
            }
        return null;
    }

    /**
     * Saves the selected part to allParts and keeps its original part ID
     * @param index partId of the selected part is used as the index
     * @param selectedPart Part selected in Part table in Main form
     */
    public static void updatePart(int index, Part selectedPart) {
        int i = -1;
        for (Part p : getAllParts()) {
            i++;
            if(p.getId() == index) {
                getAllParts().set(i, selectedPart); // When index is found, the revised part info is set in its place
                return;
            }
        }
    }

    /**
     * Saves the selected product to allProducts and keeps its original product ID
     * @param index productId of the selected product is used as the index
     * @param newProduct Product selected in Product table in Main form
     */
    /* Would recommend using "Product selectedProduct" instead of "Product newProduct" due to potential confusion (as a
    current product is being updated as opposed to a new one being created), but instructions requested "newProduct."
     */
    public static void updateProduct(int index, Product newProduct){
        int i = -1;
        for (Product p : getAllProducts()) {
            i++;
            if(p.getId() == index) {
                getAllProducts().set(i, newProduct); // When index is found, the revised product info is set in its place
                return;
            }
        }
    }

    /**
     * Deletes selected part from the Part list
     * @param selectedPart Part selected in Part table in Main form
     * @param partObservableList List of all parts
     * @return
     */
    public static boolean deletePart(Part selectedPart, ObservableList<Part> partObservableList) {
        return partObservableList.remove(selectedPart);
    }

    /**
     * Deletes selected product from the Product list
     * @param selectedProduct Product selected in Product table in Main form
     * @param productObservableList List of all products
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct, ObservableList<Product> productObservableList) {
        return productObservableList.remove(selectedProduct);
    }

    /**
     * Getter for allParts
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Getter for allProducts
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
