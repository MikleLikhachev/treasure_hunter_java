package com.treasure_hunter_java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.util.Objects;

public class DictionaryController {

    private final String white = "-fx-background-color: #ffffff";

    private final String grey =  "-fx-background-color: #cccccc";

    @FXML
    private Button searchPasswordsButton;

    @FXML
    private Button generateDictionaryButton;

    @FXML
    private Button reportButton;

    @FXML
    private Button zipButton;

    @FXML
    private Button telegramButton;

    @FXML
    private Button start;

    @FXML
    public void onGenerateDictionaryButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onSearchPasswordsButtonClick(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/MainScene.fxml")));
        JMetro jMetro = new JMetro(root, Style.LIGHT);
        Scene scene = new Scene(root, 750, 500);

        MainController.currentStage.setScene(scene);
        MainController.currentStage.show();

    }

    @FXML
    public void onReportButtonClick(ActionEvent actionEvent) {
        searchPasswordsButton.setStyle(white);
        generateDictionaryButton.setStyle(white);
        telegramButton.setStyle(white);
        zipButton.setStyle(white);
        reportButton.setStyle(grey);
    }

    @FXML
    public void onZipButtonClick(ActionEvent actionEvent) {
        searchPasswordsButton.setStyle(white);
        reportButton.setStyle(white);
        generateDictionaryButton.setStyle(white);
        telegramButton.setStyle(white);
        zipButton.setStyle(grey);
    }

    @FXML
    public void onTelegramButtonClick(ActionEvent actionEvent) {
        searchPasswordsButton.setStyle(white);
        reportButton.setStyle(white);
        generateDictionaryButton.setStyle(white);
        zipButton.setStyle(white);
        telegramButton.setStyle(grey);
    }

    @FXML
    public void onStartButtonClick(ActionEvent actionEvent) {
    }

}
