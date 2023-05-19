package com.treasure_hunter_java.controllers;

import com.treasure_hunter_java.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
public class Controller {

    protected Stage currentStage;

    @FXML
    protected Button searchPasswordsButton;

    @FXML
    protected Button generateDictionaryButton;

    @FXML
    protected Button reportButton;

    @FXML
    protected Button zipButton;

    @FXML
    protected Button telegramButton;

    @FXML
    protected void onSearchPasswordsButtonClick() throws IOException {

        setScene(searchPasswordsButton, "/com/treasure_hunter_java/fxml/MainScene.fxml");
    }

    @FXML
    protected void onGenerateDictionaryButtonClick() throws IOException {

        DictionaryController dictionaryController = new DictionaryController();
        setScene(generateDictionaryButton, "/com/treasure_hunter_java/fxml/DictionaryScene.fxml");
    }

    @FXML
    protected void onReportButtonClick() throws Exception {

    }

    @FXML
    protected void onZipButtonClick() throws IOException {

        setScene(zipButton, "/com/treasure_hunter_java/fxml/ArchiveScene.fxml");
    }

    @FXML
    protected void onTelegramButtonClick() throws IOException {

        setScene(telegramButton, "/com/treasure_hunter_java/fxml/TelegramScene.fxml");
    }

    @FXML
    protected void onSelectDirectoryButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(currentStage);

        if (selectedDirectory != null) {
            Main.mainWorkDirectory = Path.of(selectedDirectory.getAbsolutePath());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Treasure hunter");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Вы не выбрали рабочую папку.");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/icon.png"))));
            alert.showAndWait();
        }
    }

    private void setScene(Button sceneButton, String path) throws IOException {

        currentStage = (Stage) sceneButton.getScene().getWindow();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
        JMetro jMetro = new JMetro(root, Style.LIGHT);
        Scene scene = new Scene(root, 750, 500);

        currentStage.setScene(scene);
        currentStage.show();

    }

}
