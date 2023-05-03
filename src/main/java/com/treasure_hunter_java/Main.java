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

    public static Path mainWorkDirectory = Path.of("D:/Test2/");

    public static Scene test;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/MainScene.fxml")));
        //MainController.currentScene = primaryStage.getScene();
        primaryStage.setTitle("Treasure hunter");
        JMetro jMetro = new JMetro(root, Style.LIGHT);
        primaryStage.setScene(new Scene(root, 750, 500));
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/icon.png"))));
        primaryStage.show();

    }

    public static void switchScene(){

    }

    /*
    TODO РАЗОБРАТЬСЯ С ГИТОМ, СДЕЛАТЬ НОРМАЛЬНЫЙ КОНТРОЛЬ ВЕРСИЙ
    TODO Сделать выделение выбранной функции
    TODO Подредактировать параметры (длина, ширина) кнопок
    TODO Подредактировать параметры иконок
    TODO Сделать иконку приложения
    TODO Сделать "отделение" от меню с функциями
    TODO Стандартизировать расстояние между иконкой и текстом (возможно, можно сделать с помощью размера иконки)
    TODO Разобраться, почему текст не увеличивается
    TODO Подобрать нормальный цвет для кнопок
    TODO Создать структуру пакетов
    TODO Выгрузка логинов/паролей в файл
    TODO Красиво оформить код
    TODO ПОДРОБНО РАЗОБРАТЬСЯ, ЧТО И КАК РАБОТАЕТ
    TODO Сделать расшифровку паролей под все браузеры
    TODO Сделать расшифровку историй и куки
    TODO Связать Slider & Label
    */


    public static void main(String[] args) {
        launch();
    }
}