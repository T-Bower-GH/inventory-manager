module com.example.software1project {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.trevorBower.inventoryManager to javafx.fxml;
    exports com.trevorBower.inventoryManager;
}