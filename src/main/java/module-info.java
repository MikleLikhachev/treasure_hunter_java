module com.treasure_hunter_java {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.treasure_hunter_java to javafx.fxml;
    exports com.treasure_hunter_java;
}