package com.treasure_hunter_java.controllers;

import com.treasure_hunter_java.telegram.TelegramBotFileSender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;

/**
 * Класс контроллера TelegramController, отвечающий за функциональность связанную с отправкой данных в Telegram бот.
 */
public class TelegramController extends Controller {

    @FXML
    private TextField token;

    @FXML
    private TextField chatId;

    @FXML
    private TextArea textArea;

    private String selectedFile;

    /**
     * Обрабатывает событие нажатия на кнопку "Поиск паролей".
     *
     * @throws IOException если происходит ошибка ввода-вывода
     */
    @Override
    @FXML
    protected void onSearchPasswordsButtonClick() throws IOException {
        super.onSearchPasswordsButtonClick();
    }

    /**
     * Обрабатывает событие нажатия на кнопку "Генерация словаря".
     *
     * @throws IOException если происходит ошибка ввода-вывода
     */
    @Override
    @FXML
    protected void onGenerateDictionaryButtonClick() throws IOException {
        super.onGenerateDictionaryButtonClick();
    }

    /**
     * Обрабатывает событие нажатия на кнопку "Отчет".
     *
     * @throws IOException если происходит ошибка ввода-вывода
     */
    @Override
    @FXML
    protected void onReportButtonClick() throws IOException {
        super.onReportButtonClick();
    }

    /**
     * Обрабатывает событие нажатия на кнопку "Упаковать".
     *
     * @throws IOException если происходит ошибка ввода-вывода
     */
    @Override
    @FXML
    protected void onZipButtonClick() throws IOException {
        super.onZipButtonClick();
    }

    /**
     * Обрабатывает событие нажатия на кнопку "Выбрать директорию".
     */
    @Override
    @FXML
    protected void onSelectDirectoryButtonClick() {
        super.onSelectDirectoryButtonClick();
    }

    /**
     * Обрабатывает событие нажатия на кнопку "Выбрать файл".
     */
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

    /**
     * Обрабатывает событие нажатия на кнопку "Старт".
     * Отправка файла в Telegram bot
     * @param actionEvent событие нажатия на кнопку
     */
    @FXML
    public void onStartButtonClick(ActionEvent actionEvent) {
        if (token.getText().isEmpty() || chatId.getText().isEmpty()) {
            showDialog("Вы не указали TOKEN и/или CHAT_ID", Alert.AlertType.ERROR);
            return;
        } else if (selectedFile == null) {
            showDialog("Выберите файл!", Alert.AlertType.ERROR);
            return;
        }
        TelegramBotFileSender telegramBotFileSender = new TelegramBotFileSender(token.getText(), chatId.getText());
        telegramBotFileSender.sendFile(selectedFile);
        if (!textArea.getText().isEmpty()) {
            telegramBotFileSender.sendText(textArea.getText());
        }
        showDialog("Данные успешно переданы!", Alert.AlertType.INFORMATION);
    }

}