package com.treasure_hunter_java.controllers;

import com.treasure_hunter_java.decrypt.ExtractFunctionality;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

/**
 * Класс контроллера MainController, отвечающий за функциональность связанную с основным окном приложения Treasure Hunter.
 */
public class MainController extends Controller {

    @FXML
    private CheckBox passwordsGoogle;

    @FXML
    private CheckBox cookiesGoogle;

    @FXML
    private CheckBox historyGoogle;

    @FXML
    private CheckBox passwordsAtom;

    @FXML
    private CheckBox cookiesAtom;

    @FXML
    private CheckBox historyAtom;

    @FXML
    private CheckBox passwordsOpera;

    @FXML
    private CheckBox cookiesOpera;

    @FXML
    private CheckBox historyOpera;

    @FXML
    private CheckBox passwordsChromium;

    @FXML
    private CheckBox cookiesChromium;

    @FXML
    private CheckBox historyChromium;

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
     * Обрабатывает событие нажатия на кнопку "Создать отчет".
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
     * Обрабатывает событие нажатия на кнопку "Telegram".
     *
     * @throws IOException если происходит ошибка ввода-вывода
     */
    @Override
    @FXML
    protected void onTelegramButtonClick() throws IOException {
        super.onTelegramButtonClick();
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
     * Обрабатывает событие нажатия на кнопку "Старт".
     * Извлекает данные из выбранных браузеров
     * @throws Exception если происходит исключение
     */
    @FXML
    protected void onStartButtonClick() throws Exception {
        if (checkError()) {
            return;
        }
        ExtractFunctionality extractFunctionality = new ExtractFunctionality();
        extractFunctionality.getGoogleData(passwordsGoogle, cookiesGoogle, historyGoogle);
        extractFunctionality.getAtomData(passwordsAtom, cookiesAtom, historyAtom);
        extractFunctionality.getOperaData(passwordsOpera, cookiesOpera, historyOpera);
        extractFunctionality.getChromiumData(passwordsChromium, cookiesChromium, historyChromium);
        showDialog("Данные успешно извлечены!", Alert.AlertType.INFORMATION);
    }
}