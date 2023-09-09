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
        loadAllDataFromJson();
        launch();
    }
}