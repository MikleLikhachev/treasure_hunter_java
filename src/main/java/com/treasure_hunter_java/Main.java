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
import java.util.Objects;

/**
 * Main - класс, являющийся точкой входа в приложение.
 * Отображает графический интерфейс и запускает приложение Treasure Hunter.
 */
public class Main extends Application {

    /**
     * Метод start, который запускается при старте приложения.
     *
     * @param primaryStage Главная сцена приложения.
     * @throws IOException Если возникают ошибки ввода-вывода при загрузке графического интерфейса.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/MainScene.fxml")));
        primaryStage.setTitle("Treasure hunter");
        JMetro jMetro = new JMetro(root, Style.LIGHT);
        Scene scene = new Scene(root, 750, 500);
        primaryStage.setScene(scene);
        jMetro.setScene(primaryStage.getScene());
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/icon.png"))));
        primaryStage.show();
    }

    /**
     * Точка входа в приложение Treasure Hunter.
     * @param args Аргументы командной строки.
     */
    public static void main(String[] args) {
        launch();
    }

}