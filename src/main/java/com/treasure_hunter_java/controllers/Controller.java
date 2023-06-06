package com.treasure_hunter_java.controllers;

import com.treasure_hunter_java.Main;
import com.treasure_hunter_java.zip.DirectoryArchiver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {

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

        setScene(generateDictionaryButton, "/com/treasure_hunter_java/fxml/DictionaryScene.fxml");
    }

    @FXML
    protected void onReportButtonClick() throws IOException {

        setScene(zipButton, "/com/treasure_hunter_java/fxml/ReportScene.fxml");
    }

    @FXML
    protected void onZipButtonClick() throws IOException {

        if (Main.mainWorkDirectory != null) {
            DirectoryArchiver directoryArchiver = new DirectoryArchiver();
            directoryArchiver.archiveDirectory();
            showDialog("Данные успешно архивированы!", Alert.AlertType.INFORMATION);
        } else {
            showDialog("Выберите папку для архивации!", Alert.AlertType.ERROR);
        }
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
           showDialog("Вы не выбрали папку!", Alert.AlertType.ERROR);
        }
    }

    private void setScene(Button sceneButton, String path) throws IOException {

        currentStage = (Stage) sceneButton.getScene().getWindow();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
        JMetro jMetro = new JMetro(root, Style.LIGHT);
        Scene scene = new Scene(root, 750, 500);
        currentStage.setScene(scene);
        jMetro.setScene(currentStage.getScene());

    }

    public static void showDialog(String contentText, Alert.AlertType alertType) {
        String title = "Treasure hunter";
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(contentText);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Objects.requireNonNull(
                Controller.class.getResourceAsStream("/com/treasure_hunter_java/icons/icon.png"))));

        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
