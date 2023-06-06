package com.treasure_hunter_java;

import javafx.scene.image.Image;
import jfxtras.styles.jmetro.JMetro;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class Main extends Application {

    public static Path mainWorkDirectory;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/MainScene.fxml")));
        primaryStage.setTitle("Treasure hunter");
        JMetro jMetro = new JMetro(root, Style.LIGHT);
        Scene scene = new Scene(root, 750, 500);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/icon.png"))));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}