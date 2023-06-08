package com.treasure_hunter_java.controllers;

import com.treasure_hunter_java.dictionary.Filter;
import com.treasure_hunter_java.dictionary.FilterBuilder;
import com.treasure_hunter_java.dictionary.GenerateDictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Класс DictionaryController является контроллером для функциональности, связанной со словарем.
 */

public class DictionaryController extends Controller {

    private final GenerateDictionary dictionary = new GenerateDictionary();

    // Элементы FXML
    @FXML
    private CheckBox combiningDictionaries;

    @FXML
    private Button selectDictionaryForCombining;

    @FXML
    private CheckBox googleChrome;

    @FXML
    private CheckBox opera;

    @FXML
    private CheckBox chromium;

    @FXML
    private CheckBox atom;

    @FXML
    private CheckBox strictFilter;

    @FXML
    private CheckBox isContainsDigits;

    @FXML
    private CheckBox isContainsCapitalLetters;

    @FXML
    private CheckBox isContainsSmallLetters;

    @FXML
    private CheckBox isContainsSpecialSign;

    @FXML
    private CheckBox isContainsSpace;

    @FXML
    private Slider minLength;

    @FXML
    private Slider maxLength;

    @FXML
    private Slider minCountCapitalLetters;

    @FXML
    private Slider maxCountCapitalLetters;

    @FXML
    private Slider minCountSmallLetters;

    @FXML
    private Slider maxCountSmallLetters;

    @FXML
    private Slider minCountDigits;

    @FXML
    private Slider maxCountDigits;

    @FXML
    private Slider minCountSpecialSign;

    @FXML
    private Slider maxCountSpecialSign;

    @FXML
    private TextField mask;

    /**
     * Обрабатывает событие нажатия кнопки "Поиск паролей".
     *
     * @throws IOException если происходит ошибка ввода-вывода
     */
    @FXML
    @Override
    protected void onSearchPasswordsButtonClick() throws IOException {
        super.onSearchPasswordsButtonClick();
    }

    /**
     * Обрабатывает событие нажатия кнопки "Отчет".
     *
     * @throws IOException если происходит ошибка ввода-вывода
     */
    @FXML
    @Override
    protected void onReportButtonClick() throws IOException {
        super.onReportButtonClick();
    }

    /**
     * Обрабатывает событие нажатия кнопки "Zip".
     *
     * @throws IOException если происходит ошибка ввода-вывода
     */
    @FXML
    @Override
    protected void onZipButtonClick() throws IOException {
        super.onZipButtonClick();
    }

    /**
     * Обрабатывает событие нажатия кнопки "Telegram".
     *
     * @throws IOException если происходит ошибка ввода-вывода
     */
    @FXML
    @Override
    protected void onTelegramButtonClick() throws IOException {
        super.onTelegramButtonClick();
    }

    /**
     * Обрабатывает событие нажатия кнопки "Выбрать директорию".
     */
    @FXML
    @Override
    protected void onSelectDirectoryButtonClick() {
        super.onSelectDirectoryButtonClick();
    }

    /**
     * Обрабатывает событие нажатия кнопки "Старт".
     * Генерация фильтра по выбранным пользователем параметрам и составление словаря
     * @param actionEvent событие действия
     * @throws IOException если происходит ошибка ввода-вывода
     */
    @FXML
    public void onStartButtonClick(ActionEvent actionEvent) throws IOException {
        if (checkError()) {
            return;
        }

        // Создание фильтра на основе выбранных критериев
        Filter filter = new FilterBuilder()
                .strict(strictFilter.isSelected())
                .containsCapitalLetters(isContainsCapitalLetters.isSelected())
                .containsSmallLetters(isContainsSmallLetters.isSelected())
                .containsDigits(isContainsDigits.isSelected())
                .containsSpecialSign(isContainsSpecialSign.isSelected())
                .containsSpace(isContainsSpace.isSelected())
                .minLength((int) minLength.getValue())
                .maxLength((int) maxLength.getValue())
                .minCountDigits((int) minCountDigits.getValue())
                .maxCountDigits((int) maxCountDigits.getValue())
                .minCountSpecialSign((int) minCountSpecialSign.getValue())
                .maxCountSpecialSign((int) maxCountSpecialSign.getValue())
                .minCountCapitalLetters((int) minCountCapitalLetters.getValue())
                .maxCountCapitalLetters((int) maxCountCapitalLetters.getValue())
                .minCountSmallLetters((int) minCountSmallLetters.getValue())
                .maxCountSmallLetters((int) maxCountSmallLetters.getValue())
                .mask(mask.getText())
                .build();

        // Установка выбранных опций в экземпляр GenerateDictionary
        this.dictionary.setGoogleChromeIsSelected(googleChrome.isSelected());
        this.dictionary.setChromiumIsSelected(chromium.isSelected());
        this.dictionary.setOperaIsSelected(opera.isSelected());
        this.dictionary.setAtomIsSelected(atom.isSelected());
        this.dictionary.setDirectoryForCombiningIsSelected(combiningDictionaries.isSelected());

        // Составление словаря с использованием фильтра
        this.dictionary.compileDictionary(filter);

        //Отображение окна с сообщением
        showDialog("Пароли успешно отфильтрованы!", Alert.AlertType.INFORMATION);
    }

    /**
     * Обработка события нажатия на критерии (слайдеры и флажки).
     *
     * @param minSlider слайдер минимального значения
     * @param maxSlider слайдер максимального значения
     * @param criteriaCheckbox флажок для выбора критерия
     */
    private void onCriteriaClick(Slider minSlider, Slider maxSlider, CheckBox criteriaCheckbox) {
        minSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                minSlider.setValue(newValue.intValue())
        );
        maxSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                maxSlider.setValue(newValue.intValue())
        );
        minSlider.disableProperty().bind(criteriaCheckbox.selectedProperty().not());
        maxSlider.disableProperty().bind(criteriaCheckbox.selectedProperty().not());
    }

    /**
     * Обрабатывает событие нажатия на флажок "Содержит цифры".
     */
    @FXML
    private void onDigitsClick() {
        onCriteriaClick(minCountDigits, maxCountDigits, isContainsDigits);
    }

    /**
     * Обрабатывает событие нажатия на флажок "Содержит специальные символы".
     */
    @FXML
    private void onSpecialSignClick() {
        onCriteriaClick(minCountSpecialSign, maxCountSpecialSign, isContainsSpecialSign);
    }

    /**
     * Обрабатывает событие нажатия на флажок "Содержит заглавные буквы".
     */
    @FXML
    private void onCapitalLettersClick() {
        onCriteriaClick(minCountCapitalLetters, maxCountCapitalLetters, isContainsCapitalLetters);
    }

    /**
     * Обрабатывает событие нажатия на флажок "Содержит строчные буквы".
     */
    @FXML
    private void onSmallLettersClick() {
        onCriteriaClick(minCountSmallLetters, maxCountSmallLetters, isContainsSmallLetters);
    }

    /**
     * Обрабатывает событие нажатия на флажок "Объединение словарей".
     */
    @FXML
    private void onCombiningDictionaryClick() {
        selectDictionaryForCombining.disableProperty().bind(combiningDictionaries.selectedProperty().not());
    }

    /**
     * Обрабатывает событие нажатия на кнопку "Выбрать словарь для объединения".
     */
    @FXML
    private void onSelectDictionaryForCombiningClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(super.currentStage);

        if (selectedDirectory != null) {
            dictionary.setDictionaryForCombining(Path.of(selectedDirectory.getAbsolutePath()));
        } else {
            // Отображение сообщения об ошибке, если не выбрана папка со словарями
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Treasure hunter");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Вы не выбрали папку со словарями.");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/treasure_hunter_java/icons/icon.png"))));
            alert.showAndWait();
        }
    }
}