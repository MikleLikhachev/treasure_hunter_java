package com.treasure_hunter_java.controllers;

import com.treasure_hunter_java.directory.Directory;
import com.treasure_hunter_java.zip.DirectoryArchiver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import java.io.IOException;
import java.util.Objects;

/**
 * Класс Controller является базовым контроллером для управления событиями на главном экране приложения.
 */
public class Controller {

    protected Stage currentStage;

    protected Directory directory = new Directory();

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
    /**
     * Обработчик события при нажатии кнопки "Поиск паролей".
     * Переключает сцену на экран поиска паролей.
     *
     * @throws IOException если возникают проблемы с загрузкой FXML-файла сцены.
     */
    @FXML
    protected void onSearchPasswordsButtonClick() throws IOException {
        setScene(searchPasswordsButton, "/com/treasure_hunter_java/fxml/MainScene.fxml");
    }
    /**
     * Обработчик события при нажатии кнопки "Генерация словаря".
     * Переключает сцену на экран генерации словаря.
     *
     * @throws IOException если возникают проблемы с загрузкой FXML-файла сцены.
     */
    @FXML
    protected void onGenerateDictionaryButtonClick() throws IOException {
        setScene(generateDictionaryButton, "/com/treasure_hunter_java/fxml/DictionaryScene.fxml");
    }
    /**
     * Обработчик события при нажатии кнопки "Отчет".
     * Переключает сцену на экран отчета.
     *
     * @throws IOException если возникают проблемы с загрузкой FXML-файла сцены.
     */
    @FXML
    protected void onReportButtonClick() throws IOException {
        setScene(zipButton, "/com/treasure_hunter_java/fxml/ReportScene.fxml");
    }

    /**
     * Обработчик события при нажатии кнопки "Архивация".
     * Архивирует текущую рабочую директорию и отображает диалоговое окно с информацией об успешной архивации.
     *
     * @throws IOException если возникают проблемы при архивации директории или при отображении диалогового окна.
     */
    @FXML
    protected void onZipButtonClick() throws IOException {
        if (Directory.getWorkDirectory() != null) {
            DirectoryArchiver directoryArchiver = new DirectoryArchiver();
            directoryArchiver.archiveDirectory();
            showDialog("Данные успешно архивированы!", Alert.AlertType.INFORMATION);
        } else {
            showDialog("Выберите папку для архивации!", Alert.AlertType.ERROR);
        }
    }
    /**
     * Обработчик события при нажатии кнопки "Telegram".
     * Переключает сцену на экран Telegram.
     *
     * @throws IOException если возникают проблемы с загрузкой FXML-файла сцены.
     */
    @FXML
    protected void onTelegramButtonClick() throws IOException {
        setScene(telegramButton, "/com/treasure_hunter_java/fxml/TelegramScene.fxml");
    }
    /**
     * Обработчик события при нажатии кнопки "Выбрать папку".
     * Открывает диалог выбора папки и устанавливает выбранную папку как рабочую директорию.
     */
    @FXML
    protected void onSelectDirectoryButtonClick() {
        directory.selectDirectory(currentStage);
    }
    /**
     * Устанавливает новую сцену и стиль JMetro для текущего окна.
     *
     * @param sceneButton кнопка, вызывающая переключение сцены
     * @param path        путь к FXML-файлу новой сцены
     * @throws IOException если возникают проблемы с загрузкой FXML-файла сцены.
     */
    private void setScene(Button sceneButton, String path) throws IOException {
        currentStage = (Stage) sceneButton.getScene().getWindow();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
        JMetro jMetro = new JMetro(root, Style.LIGHT);
        Scene scene = new Scene(root, 750, 500);
        currentStage.setScene(scene);
        jMetro.setScene(currentStage.getScene());
    }

    /**
     * Отображает диалоговое окно с заданным текстом и типом сообщения.
     *
     * @param contentText текст сообщения
     * @param alertType   тип сообщения
     */
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

    /**
     * Проверяет наличие ошибки, связанной с не выбранной рабочей директорией.
     * Если рабочая директория не выбрана, отображается соответствующее диалоговое окно ошибки.
     *
     * @return true, если есть ошибка, иначе false.
     */
    public static boolean checkError() {
        if (Directory.getWorkDirectory() == null) {
            showDialog("Выберите папку!", Alert.AlertType.ERROR);
            return true;
        }
        return false;
    }
}