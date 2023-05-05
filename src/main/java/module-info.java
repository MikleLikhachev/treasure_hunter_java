module com.treasure_hunter_java {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jfxtras.styles.jmetro;
    requires com.sun.jna.platform;
    requires org.bouncycastle.provider;
    requires org.apache.commons.codec;
    requires java.sql;
    requires sqlite.jdbc;


    opens com.treasure_hunter_java to javafx.fxml;
    exports com.treasure_hunter_java;
}