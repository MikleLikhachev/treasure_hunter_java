module com.treasure_hunter_java {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jfxtras.styles.jmetro;
    requires com.sun.jna.platform;
    requires org.bouncycastle.provider;
    requires org.apache.commons.codec;
    requires java.sql;
    requires sqlite.jdbc;
    requires org.apache.commons.compress;


    opens com.treasure_hunter_java to javafx.fxml;
    exports com.treasure_hunter_java;
    exports com.treasure_hunter_java.dictionary;
    opens com.treasure_hunter_java.dictionary to javafx.fxml;
    exports com.treasure_hunter_java.controllers;
    opens com.treasure_hunter_java.controllers to javafx.fxml;
    exports com.treasure_hunter_java.decrypt;
    opens com.treasure_hunter_java.decrypt to javafx.fxml;
}