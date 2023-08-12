
package com.example.software1project;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.example.software1project.Inventory.*;

/**
 * @author Trevor Bower
 *
 * JavaDoc Comments folder located in:
 * My Project\Software 1 Project\src\main\resources\com\example\software1project\JavaDoc Comments
 *
 * FUTURE ENHANCEMENTS:
 * 1) Making it so if a part/product is deleted, the other part/product ID's could collapse to keep everything sequential
 * 2) Making a "recycle bin" of sorts to save previous parts and products that were deleted
 * 3) Making a 3rd tableview for associated parts in the Main form that would open/populate when a product is selected
 * 4) Providing templates for potential associated parts when creating a new product (Ex: A new product containing the
 * word "bike" could come with 2 wheels and a seat)
 *
 * EXTRA METHODS: ExtraMethods class was added to provide non-mandatory methods to reduce redundancy in code and to
  * keep core methods in the appropriate classes for ease of evaluation. All core methods that reference these extra methods
  * will contain comments saying "See ExtraMethods Class."
  */


public class Main extends Application {

    /**
     * Loads and displays Main form
     * @param mainStage Main form
     * @throws Exception Main form unable to load
     */
    @Override
    public void start(Stage mainStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1077, 378);
        mainStage.setTitle(" ");
        mainStage.setScene(scene);
        mainStage.show();
    }

    /**
     * Inventory program with sample data as part of testing included
     * @param args Main
     */
    public static void main(String[] args) {
        InHouse inSample1 = new InHouse(newPartID(),"Brakes", 15.00, 10, 1, 20, 111);
        InHouse inSample2 = new InHouse(newPartID(),"Wheel", 11.00, 16, 1, 30, 222);
        Outsourced outSample1 = new Outsourced(newPartID(), "Seat", 10.0, 10, 3, 22, "Example Company");
        Outsourced outSample2 = new Outsourced(newPartID(), "Rim", 56.99, 15, 1, 20, "Super Bikes");
        Product prodSample1 = new Product(newProductID(), "Giant Bike", 299.99, 5, 1, 5);
        Product prodSample2 = new Product(newProductID(), "Tricycle", 99.99, 3, 1, 10);
        prodSample1.addAssociatedPart(inSample1); // Adds "Brakes" to Giant Bike's associated parts list
        prodSample1.addAssociatedPart(inSample2); // Adds "Wheel" to Giant Bike's associated parts list
        prodSample1.addAssociatedPart(inSample2); // Adds 2nd "Wheel" to Giant Bike's associated parts list
        prodSample1.addAssociatedPart(inSample2); // Adds 3rd "Wheel" to Giant Bike's associated parts list
        prodSample1.addAssociatedPart(outSample1); // Adds "Seat" to Giant Bike's associated parts list
        prodSample1.deleteAssociatedPart(inSample2); // Removes 3rd "Wheel" from Giant Bike's associated parts list
        // Giant Bike should now have the following associated parts: Brakes, Seat, Wheel, Wheel
        prodSample2.addAssociatedPart(inSample2); // Adds "Wheel" to Tricycle's associated parts list
        prodSample2.addAssociatedPart(inSample2); // Adds 2nd "Wheel" to Tricycle's associated parts list
        prodSample2.addAssociatedPart(inSample2); // Adds 3rd "Wheel" to Tricycle's associated parts list
        prodSample2.addAssociatedPart(outSample1); // Adds "Seat" to Tricycle's associated parts list
        // Giant Bike should now have the following associated parts: Wheel, Wheel, Wheel, Seat
        addPart(inSample1); // Adds "Brakes" to allParts
        addPart(inSample2); // Adds "Wheel" to allParts
        addPart(outSample1); // Adds "Seat" to allParts
        addPart(outSample2); // Adds "Rim" to allParts
        addProduct(prodSample1); // Adds "Giant Bike" to allProducts
        addProduct(prodSample2); // Adds "Tricycle" to allProducts

        launch(); // Starts the program
    }
}