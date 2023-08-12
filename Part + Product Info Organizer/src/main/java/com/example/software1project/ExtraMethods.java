package com.example.software1project;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Trevor Bower
 */

public class ExtraMethods {

    /**
     * Returns to Main form
     * @param event When cancel button is clicked while filling out a form, or after part or product is
     *              successfully saved
     * @throws Exception Main form is unable to load
     */
    public static void mainForm(ActionEvent event) throws Exception {
            Parent root = FXMLLoader.load(ExtraMethods.class.getResource("MainForm.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }


    /**
     * Opens Add Part form
     * @param event Add button clicked in Part pane of Main form
     * @throws Exception Add Part form is unable to load
     */
    public static void addPartForm(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(ExtraMethods.class.getResource("AddPartForm.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens Add Product form
     * @param event Add button clicked in Product pane of Main form
     * @throws Exception Add Product form is unable to load
     */
    public static void addProductForm(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(ExtraMethods.class.getResource("AddProductForm.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Checks to see if a selected product has associated parts
     * @param selectedProduct Product selected in Product table in Main form
     * @return True if product has associated parts; false if product does not have associated parts
     */
    public static boolean checkAssociatedParts(Product selectedProduct) {
        ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();
        if (!associatedParts.isEmpty()) {
            return true;
        }
        return false;
    }

    // For opening Modify Part and Modify Product forms, see "Methods" in MainFormController class.

    // Functions to validate field values:

    /**
     * Checks if a required text field is empty
     * @param string String in a text field (technically can be used for any string, not just text fields)
     */
    public static void checkIfEmpty(String string) {
        if (string.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Field missing value.");
            alert.showAndWait();
        }
    }

    /**
     * Checks if a text field requiring an integer does not have a positive number and displays error if such occurs
     * @param i Int in a text field (technically can be used for any int, not just text fields)
     */
    public static void checkIfPositive(int i) {
        if (i < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("All number values must be positive.");
            alert.showAndWait();
        }
    }

    /**
     * Checks if a text field requiring a double does not have a positive number and displays error if such occurs
     * @param i Double in a text field (technically can be used for any double, not just text fields)
     */
    public static void checkIfPositive(double i) {
        if (i < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("All number values must be positive.");
            alert.showAndWait();
        }
    }

    /**
     * Checks to make sure text fields expecting strings are not empty
     * @param f Text field being checked
     */
    public static void checkName(TextField f) {
        try {
            checkIfEmpty(f.getText().trim());
        } catch (Exception e) { // Highlights the field's border red if empty
            f.setStyle("-fx-border-color: red");
        }
    }

    /**
     * Checks text fields expecting integer values to make sure the field is not empty, is an integer, and is positive
     * @param f Text field being checked
     */
    public static void checkInt(TextField f) {
        try {
            int i = Integer.parseInt(f.getText().trim());
            checkIfPositive(i);
            checkIfEmpty(f.getText().trim());
        } catch (NumberFormatException e) { // If field does not meet criteria
            f.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Inventory, Max, and Min must be integers."); // Displays if the field is positive, is not empty, and is not an integer
            alert.showAndWait();
        }
    }

    /**
     * Checks text fields expecting double values to make sure the field is not empty, is an integer, and is positive
     * @param f Text field being checked
     */
    public static void checkPrice(TextField f) {
        double d;
        try {
            d = Double.parseDouble(f.getText().trim());
            checkIfPositive(d);
            checkIfEmpty(f.getText().trim());
        } catch (Exception e) {
            f.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Price must be an integer or double."); // Only displays if the field is positive, is not empty, and is not an integer
            alert.showAndWait();
        }
    }

    /**
     * Checks text fields expecting integer values to make sure the field is not empty, is an integer, and is positive
     * Similar to checkInt, but decided to keep separate because of cases when checkInt is used and In-House is
     * not selected
     * @param f Text field being checked
     */
    public static void checkInHouse(TextField f) {
        try {
            int i = Integer.parseInt(f.getText().trim());
            checkIfPositive(i);
            checkIfEmpty(f.getText().trim());
        } catch (NumberFormatException e) { // If field does not meet criteria
            f.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Machine ID must be an integer."); // Displays if the field is positive, is not empty, and is not an integer
            alert.showAndWait();
        }
    }


    /**
     * Checks Max, Min, and Inv fields if Max < Min, Max < Inv, and/or Inv < Min, and displays error if such occurs
     * RUNTIME ERROR: Technically not an error, but a bug. For some reason, this program was not throwing
     *         exceptions on its own like the above checks, so IllegalArgumentExceptions were manually added because the
     *         program kept saving invalid Max/Min/Inv values without them.
     * @param maxF Max text field
     * @param minF Min text field
     * @param invInt Inv text field
     */
    public static void checkMaxMinInv(TextField maxF, TextField minF, int invInt) {
        if (Integer.parseInt(maxF.getText().trim()) < Integer.parseInt(minF.getText().trim()) && Integer.parseInt(maxF.getText().trim()) < invInt && invInt < Integer.parseInt(minF.getText().trim())) {
            // If Max < Min, Max < Inv, AND Inv < Min ALL occur:
            minF.setStyle("-fx-border-color: red");
            maxF.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Max cannot be less than Min.\nMax cannot be less than Inv.\nInv cannot be less than Min.");
            alert.showAndWait();
            throw new IllegalArgumentException("Max/Min/Inv input error.");
        } else if (Integer.parseInt(maxF.getText().trim()) < Integer.parseInt(minF.getText().trim()) && Integer.parseInt(maxF.getText().trim()) < invInt) {
            // If Max < Min AND Max < Inv
            maxF.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Max cannot be less than Min.\nMax cannot be less than Inv.");
            alert.showAndWait();
            throw new IllegalArgumentException("Max/Min/Inv input error.");
        } else if (Integer.parseInt(maxF.getText().trim()) < Integer.parseInt(minF.getText().trim()) && invInt < Integer.parseInt(minF.getText().trim())) {
            // If Max < Min AND Inv < Min
            minF.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Max cannot be less than Min.\nInv cannot be less than Min.");
            alert.showAndWait();
            throw new IllegalArgumentException("Max/Min/Inv input error.");
        } else if (Integer.parseInt(maxF.getText().trim()) < Integer.parseInt(minF.getText().trim())) {
            // If Max < Min
            minF.setStyle("-fx-border-color: red");
            maxF.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Max cannot be less than Min.");
            alert.showAndWait();
            throw new IllegalArgumentException("Max/Min/Inv input error.");
        }
        else if (invInt < Integer.parseInt(minF.getText().trim())) {
            // If Inv < Min
            minF.setStyle("-fx-border-color: red");
            maxF.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Inv cannot be less than Min.");
            alert.showAndWait();
            throw new IllegalArgumentException("Max/Min/Inv input error.");
        }
        else if (Integer.parseInt(maxF.getText().trim()) < invInt) {
            // If Max < Inv
            minF.setStyle("-fx-border-color: red");
            maxF.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Max cannot be less than Inv.");
            alert.showAndWait();
            throw new IllegalArgumentException("Max/Min/Inv input error.");
        }
    }

}
