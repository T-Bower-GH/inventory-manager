package com.trevorBower.inventoryManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.trevorBower.inventoryManager.Inventory.*;

/**
 * @author Trevor Bower
  */
public class Main extends Application {
    @Override
    public void start(Stage mainStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/forms/MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1077, 378);
        mainStage.setTitle(" ");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        InHouse brakes = new InHouse(newPartID(),"Brakes", 15.00, 10, 1, 20, 111);
        InHouse wheel = new InHouse(newPartID(),"Wheel", 11.00, 16, 1, 30, 222);
        Outsourced seat = new Outsourced(newPartID(), "Seat", 10.0, 10, 3, 22,
                "Expert Seats");
        Outsourced rim = new Outsourced(newPartID(), "Rim", 56.99, 15, 1, 20,
                "Remmy's");
        Product bicycle = new Product(newProductID(), "Bicycle", 299.99, 5, 1, 5);
        Product tricycle = new Product(newProductID(), "Tricycle", 99.99, 3, 1, 10);

        bicycle.addAssociatedPart(brakes);
        bicycle.addAssociatedPart(wheel);
        bicycle.addAssociatedPart(wheel);
        bicycle.addAssociatedPart(seat);

        tricycle.addAssociatedPart(wheel);
        tricycle.addAssociatedPart(wheel);
        tricycle.addAssociatedPart(wheel);
        tricycle.addAssociatedPart(seat);

        addPart(brakes);
        addPart(wheel);
        addPart(seat);
        addPart(rim);
        addProduct(bicycle);
        addProduct(tricycle);

        launch();
    }
}