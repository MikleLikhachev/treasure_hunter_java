package com.treasure_hunter_java.controllers;

import com.treasure_hunter_java.report.GenerateReport;
import com.treasure_hunter_java.report.GenerateReportBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

import java.io.IOException;

/**
 * Класс контроллера ReportController, отвечающий за функциональность связанную с генерацией отчета.
 */
public class ReportController extends Controller {

    @FXML
    protected CheckBox passwordCount;

    @FXML
    protected CheckBox totalPasswordCount;

    @FXML
    protected CheckBox uniquePasswordCount;

    @FXML
    protected CheckBox passwordLength;

    @FXML
    protected CheckBox passwordMaxLength;

    @FXML
    protected CheckBox passwordAverageLength;

    @FXML
    protected CheckBox passwordMinLength;

    @FXML
    protected CheckBox mostPopularSymbols;

    @FXML
    protected CheckBox mostPopularGroupSymbols;

    @FXML
    protected Slider groupSymbolLength;

    @FXML
    protected CheckBox topMostPopularGroupSymbol;

    @FXML
    protected Slider topGroupSymbolsLength;

    @FXML
    protected CheckBox mostPopularSymbol;

    @FXML
    protected CheckBox topPopularSymbol;

    @FXML
    protected Slider topPopularSymbolCount;

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
     * Генерируется отчет на основе параметров, заданных пользователем
     * @param actionEvent событие нажатия на кнопку
     * @throws IOException если происходит ошибка ввода-вывода
     */
    @FXML
    public void onStartButtonClick(ActionEvent actionEvent) throws IOException {
        if (checkError()) {
            return;
        }
        GenerateReport report = new GenerateReportBuilder((int) groupSymbolLength.getValue())
                .totalCountPasswords(totalPasswordCount.isSelected())
                .uniqueCountPasswords(uniquePasswordCount.isSelected())
                .passwordMaxLengthIsSelected(passwordMaxLength.isSelected())
                .passwordAverageLengthIsSelected(passwordAverageLength.isSelected())
                .passwordMinLengthIsSelected(passwordMinLength.isSelected())
                .mostPopularGroupSymbols(mostPopularGroupSymbols.isSelected())
                .topPopularGroupSymbolCount((int) topGroupSymbolsLength.getValue())
                .mostPopularSymbolIsSelected(mostPopularSymbol.isSelected())
                .topPopularSymbolIsSelected(topPopularSymbol.isSelected())
                .topPopularSymbolCount((int) topPopularSymbolCount.getValue())
                .build();
        report.startGenerate();
        showDialog("Отчёт сгенерирован!", Alert.AlertType.INFORMATION);
    }

    /**
     * Обрабатывает событие нажатия на чекбокс "Количество паролей".
     */
    @FXML
    private void onPasswordCountClick() {
        totalPasswordCount.disableProperty().bind(passwordCount.selectedProperty().not());
        uniquePasswordCount.disableProperty().bind(passwordCount.selectedProperty().not());
    }

    /**
     * Обрабатывает событие нажатия на чекбокс "Длина паролей".
     */
    @FXML
    private void onPasswordLengthClick() {
        passwordMaxLength.disableProperty().bind(passwordLength.selectedProperty().not());
        passwordAverageLength.disableProperty().bind(passwordLength.selectedProperty().not());
        passwordMinLength.disableProperty().bind(passwordLength.selectedProperty().not());
    }

    /**
     * Обрабатывает событие нажатия на чекбокс "Самые популярные символы".
     */
    @FXML
    private void onMostPopularSymbolsClick() {
        mostPopularGroupSymbols.disableProperty().bind(mostPopularSymbols.selectedProperty().not());
        topMostPopularGroupSymbol.disableProperty().bind(mostPopularSymbols.selectedProperty().not());
    }

    /**
     * Обрабатывает событие нажатия на чекбокс "Самые популярные группы символов".
     */
    @FXML
    private void onMostPopularGroupSymbolsClick() {
        groupSymbolLength.valueProperty().addListener((observable, oldValue, newValue) ->
                groupSymbolLength.setValue(newValue.intValue()));
        groupSymbolLength.disableProperty().bind(mostPopularGroupSymbols.selectedProperty().not());
    }

    /**
     * Обрабатывает событие нажатия на чекбокс "Самый популярный символ".
     */
    @FXML
    private void onTopMostPopularGroupSymbolClick() {
        topGroupSymbolsLength.valueProperty().addListener((observable, oldValue, newValue) ->
                topGroupSymbolsLength.setValue(newValue.intValue()));
        topGroupSymbolsLength.disableProperty().bind(topMostPopularGroupSymbol.selectedProperty().not());
    }

    /**
     * Обрабатывает событие нажатия на чекбокс "Самый популярный символ".
     */
    @FXML
    private void onMostPopularSymbolClick() {
        topPopularSymbol.disableProperty().bind(mostPopularSymbol.selectedProperty().not());
    }

    /**
     * Обрабатывает событие нажатия на чекбокс "Самый популярный символ".
     */
    @FXML
    private void onTopPopularSymbolClick() {
        topPopularSymbolCount.valueProperty().addListener((observable, oldValue, newValue) ->
                topPopularSymbolCount.setValue(newValue.intValue()));
        topPopularSymbolCount.disableProperty().bind(topPopularSymbol.selectedProperty().not());
    }
}