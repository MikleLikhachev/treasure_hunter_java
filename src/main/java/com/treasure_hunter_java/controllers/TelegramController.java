package com.treasure_hunter_java.controllers;

import com.treasure_hunter_java.telegram.TelegramBotFileSender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class TelegramController extends Controller{

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
    private Button selectDirectoryButton;

    @FXML
    private Button start;

    @FXML
    private TextField token;

    @FXML
    private TextField chatId;

    @FXML
    private Button selectFile;

    @FXML
    private TextArea textArea;

    private String selectedFile;

    @FXML
    protected void onSearchPasswordsButtonClick() throws IOException {
        super.onSearchPasswordsButtonClick();
    }

    @FXML
    protected void GenerateDictionaryButtonClick() throws IOException {
        super.onGenerateDictionaryButtonClick();
    }

    @FXML
    protected void onReportButtonClick() throws Exception {
        super.onReportButtonClick();
    }

    @FXML
    protected void onZipButtonClick() throws IOException {
        super.onZipButtonClick();
    }

    @FXML
    protected void onTelegramButtonClick() throws IOException {}

    @FXML
    protected void onSelectDirectoryButtonClick() {

        super.onSelectDirectoryButtonClick();
    }

    @FXML
    protected void onSelectFileButtonClick() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(currentStage);
        if (selectedFile != null) {
            this.selectedFile = selectedFile.getAbsolutePath();
        } else {
            showDialog("Вы не выбрали файл!", Alert.AlertType.ERROR);
        }
    }


    @FXML
    public void onStartButtonClick(ActionEvent actionEvent) throws IOException {
        TelegramBotFileSender telegramBotFileSender = new TelegramBotFileSender(token.getText(), chatId.getText());
        telegramBotFileSender.sendFile(selectedFile);
        if(!textArea.getText().isEmpty()){
            telegramBotFileSender.sendText(textArea.getText());
        }
        showDialog("Данные успешно переданы!", Alert.AlertType.INFORMATION);
    }

}
