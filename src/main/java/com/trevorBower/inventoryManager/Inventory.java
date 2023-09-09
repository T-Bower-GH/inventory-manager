package com.trevorBower.inventoryManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import javax.json.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.trevorBower.inventoryManager.Utility.displayError;

/**
 * @author Trevor Bower
 */
public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static int partID = 0;
    private static int productID = 0;

    public static int newPartID() {
        return ++partID;
    }
    public static int newProductID() {
        return ++productID;
    }

    public static void addPart(Part newPart) { allParts.add(newPart); }
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


    public static Part lookupPart(int partId) {
        for (Part part : getAllParts()) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
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
            displayError("Selected value is null. Please check your associated part selection.");
            return;
        }
        if (Utility.hasUserConfirmed("remove selected part from product's associated parts")) {
            currentAssociatedParts.remove(selectedPart);
            associatedPartTable.setItems(currentAssociatedParts);
        }
    }

    // JSON Management:

    public static void loadAllDataFromJson() {
        try {
            InputStream inputStream = Main.class.getResourceAsStream("/data/inventory.json");
            if (inputStream != null && inputStream.available() > 0) {
                loadPartDataFromJson();
                loadProductDataFromJson();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadPartDataFromJson() {
        int maxLoadedPartID = 0;

        try (InputStream inputStream = Main.class.getResourceAsStream("/data/inventory.json")) {
            JsonReader reader = Json.createReader(inputStream);
            JsonObject inventoryData = reader.readObject();
            JsonArray partsData = inventoryData.getJsonArray("parts");

            for (JsonObject partData : partsData.getValuesAs(JsonObject.class)) {
                int id = partData.getInt("id");
                String name = partData.getString("name");
                double price = partData.getJsonNumber("price").doubleValue();
                int stock = partData.getInt("stock");
                int min = partData.getInt("min");
                int max = partData.getInt("max");

                Part part;
                if (partData.containsKey("machineId")) {
                    int machineId = partData.getInt("machineId");
                    part = new InHouse(id, name, price, stock, min, max, machineId);
                } else if (partData.containsKey("companyName")) {
                    String companyName = partData.getString("companyName");
                    part = new Outsourced(id, name, price, stock, min, max, companyName);
                } else {
                    displayError("Neither Machine ID or Company Name found for attempted part load.");
                    part = null;
                }

                if (part != null) {
                    addPart(part);
                    maxLoadedPartID = Math.max(maxLoadedPartID, id);
                }
            }
            partID = Math.max(partID, maxLoadedPartID);
        } catch (IOException e) {
            displayError("Part data load failed.");
            e.printStackTrace();
        }
    }

    public static void loadProductDataFromJson() {
        int maxLoadedProductID = 0;
        try (InputStream inputStream = Main.class.getResourceAsStream("/data/inventory.json")) {
            JsonReader reader = Json.createReader(inputStream);
            JsonObject inventoryData = reader.readObject();
            JsonArray productsData = inventoryData.getJsonArray("products");

            for (JsonObject productData : productsData.getValuesAs(JsonObject.class)) {
                int id = productData.getInt("id");
                String name = productData.getString("name");
                double price = productData.getJsonNumber("price").doubleValue();
                int stock = productData.getInt("stock");
                int min = productData.getInt("min");
                int max = productData.getInt("max");

                Product product = new Product(id, name, price, stock, min, max);

                JsonArray associatedPartsArray = productData.getJsonArray("associatedParts");
                for (JsonValue partIdValue : associatedPartsArray) {
                    String partIdStr = partIdValue.toString();
                    int partId = Integer.parseInt(partIdStr);
                    Part associatedPart = lookupPart(partId);
                    if (associatedPart != null) {
                        product.addAssociatedPart(associatedPart);
                    }
                }

                addProduct(product);
                maxLoadedProductID = Math.max(maxLoadedProductID, id);
            }
            productID = Math.max(productID, maxLoadedProductID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAllDataToJson() {
        JsonObjectBuilder combinedDataBuilder = Json.createObjectBuilder()
                .add("parts", buildPartsJsonArray())
                .add("products", buildProductsJsonArray());

        try (JsonWriter writer = Json.createWriter(new FileWriter("src/main/resources/data/inventory.json"))) {
            writer.writeObject(combinedDataBuilder.build());
        } catch (IOException e) {
            displayError("Save operation failed.");
            e.printStackTrace();
        }
    }

    private static JsonArray buildPartsJsonArray(){
        JsonArrayBuilder partsArrayBuilder = Json.createArrayBuilder();
        for (Part part : getAllParts()) {
            JsonObjectBuilder partBuilder = buildPartJsonObject(part);
            partsArrayBuilder.add(partBuilder);
        }
        return partsArrayBuilder.build();
    }

    private static JsonArray buildProductsJsonArray(){
        JsonArrayBuilder productsArrayBuilder = Json.createArrayBuilder();
        for (Product product : getAllProducts()) {
            JsonObjectBuilder productBuilder = buildProductJsonObject(product);
            productsArrayBuilder.add(productBuilder);
        }
        return productsArrayBuilder.build();
    }

    private static JsonObjectBuilder buildPartJsonObject(Part part) {
        JsonObjectBuilder partBuilder = Json.createObjectBuilder()
                .add("id", part.getId())
                .add("name", part.getName())
                .add("price", part.getPrice())
                .add("stock", part.getStock())
                .add("min", part.getMin())
                .add("max", part.getMax());
        if (part instanceof InHouse) {
            InHouse inHousePart = (InHouse) part;
            partBuilder.add("machineId", inHousePart.getMachineId());
        } else if (part instanceof Outsourced) {
            Outsourced outsourcedPart = (Outsourced) part;
            partBuilder.add("companyName", outsourcedPart.getCompanyName());
        }

        return partBuilder;
    }

    private static JsonObjectBuilder buildProductJsonObject(Product product) {
        JsonObjectBuilder productBuilder = Json.createObjectBuilder()
                .add("id", product.getId())
                .add("name", product.getName())
                .add("price", product.getPrice())
                .add("stock", product.getStock())
                .add("min", product.getMin())
                .add("max", product.getMax())
                .add("associatedParts", buildJsonAssociatedParts(product));

        return productBuilder;
    }

    private static JsonArrayBuilder buildJsonAssociatedParts(Product product) {
        JsonArrayBuilder jsonAssociatedParts = Json.createArrayBuilder();
        for (Part part : product.getAllAssociatedParts()) {
            jsonAssociatedParts.add(part.getId());
        }
        return jsonAssociatedParts;
    }

}
