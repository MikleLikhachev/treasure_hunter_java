package com.treasure_hunter_java;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.util.Objects;

public class DictionaryController {
    public void onGenerateDictionaryButtonClick(ActionEvent actionEvent) {
    }

    public void onSearchPasswordsButtonClick(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/MainScene.fxml")));
        JMetro jMetro = new JMetro(root, Style.LIGHT);
        Scene scene = new Scene(root, 750, 500);

        MainController.currentStage.setScene(scene);
        MainController.currentStage.show();

    }

    public void onReportButtonClick(ActionEvent actionEvent) {
    }

    public void onZipButtonClick(ActionEvent actionEvent) {
    }

    public void onTelegramButtonClick(ActionEvent actionEvent) {
    }

    public void onStartButtonClick(ActionEvent actionEvent) {
    }
}
