module com.example.software1project {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.software1project to javafx.fxml;
    exports com.example.software1project;
}