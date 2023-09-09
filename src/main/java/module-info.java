module com.example.software1project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.json;

    opens com.trevorBower.inventoryManager to javafx.fxml;
    exports com.trevorBower.inventoryManager;
}