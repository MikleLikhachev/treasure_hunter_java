package com.treasure_hunter_java.controllers;

import com.treasure_hunter_java.telegram.TelegramBotFileSender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;


public class TelegramController extends Controller{

    @FXML
    private TextField token;

    @FXML
    private TextField chatId;

    @FXML
    private TextArea textArea;

    private String selectedFile;

    @Override
    @FXML
    protected void onSearchPasswordsButtonClick() throws IOException {
        super.onSearchPasswordsButtonClick();
    }

    @Override
    @FXML
    protected void onGenerateDictionaryButtonClick() throws IOException {
        super.onGenerateDictionaryButtonClick();
    }

    @Override
    @FXML
    protected void onReportButtonClick() throws IOException {
        super.onReportButtonClick();
    }

    @Override
    @FXML
    protected void onZipButtonClick() throws IOException {
        super.onZipButtonClick();
    }

    @Override
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
    public void onStartButtonClick(ActionEvent actionEvent) {
        TelegramBotFileSender telegramBotFileSender = new TelegramBotFileSender(token.getText(), chatId.getText());
        telegramBotFileSender.sendFile(selectedFile);
        if(!textArea.getText().isEmpty()){
            telegramBotFileSender.sendText(textArea.getText());
        }
        showDialog("Данные успешно переданы!", Alert.AlertType.INFORMATION);
    }

}
