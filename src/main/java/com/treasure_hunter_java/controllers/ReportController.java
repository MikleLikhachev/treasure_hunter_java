package com.treasure_hunter_java.controllers;

import com.treasure_hunter_java.report.GenerateReport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

import java.io.IOException;

public class ReportController extends Controller{

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
    protected CheckBox mostPopularPassword;

    @FXML
    protected CheckBox HowManyTimesMostPopularPasswordWasUsed;

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

    }

    @FXML
    protected void onZipButtonClick() throws IOException {
        super.onZipButtonClick();
    }

    @FXML
    protected void onTelegramButtonClick() throws IOException {
        super.onTelegramButtonClick();
    }

    @FXML
    protected void onSelectDirectoryButtonClick() {
        super.onSelectDirectoryButtonClick();
    }

    @FXML
    public void onStartButtonClick(ActionEvent actionEvent) throws IOException {
        GenerateReport generateReport = new GenerateReport((int) groupSymbolLength.getValue(),
                (int) topGroupSymbolsLength.getValue(), totalPasswordCount.isSelected(), uniquePasswordCount.isSelected(),
                passwordMaxLength.isSelected(), passwordAverageLength.isSelected(), passwordMinLength.isSelected(),
                topPopularSymbol.isSelected(), (int) topPopularSymbolCount.getValue());
        generateReport.rocket();
    }

    @FXML
    private void onPasswordCountClick() {
        totalPasswordCount.disableProperty().bind(passwordCount.selectedProperty().not());
        uniquePasswordCount.disableProperty().bind(passwordCount.selectedProperty().not());
    }

    @FXML
    private void onTotalPasswordCountClick() {
        // Ваш код для обработки события нажатия на CheckBox totalPasswordCount
    }

    @FXML
    private void onUniquePasswordCountClick() {
        // Ваш код для обработки события нажатия на CheckBox uniquePasswordCount
    }

    @FXML
    private void onPasswordLengthClick() {
        passwordMaxLength.disableProperty().bind(passwordLength.selectedProperty().not());
        passwordAverageLength.disableProperty().bind(passwordLength.selectedProperty().not());
        passwordMinLength.disableProperty().bind(passwordLength.selectedProperty().not());
    }

    @FXML
    private void onPasswordMaxLengthClick() {
        // Ваш код для обработки события нажатия на CheckBox passwordMaxLength
    }

    @FXML
    private void onPasswordAverageLengthClick() {
        // Ваш код для обработки события нажатия на CheckBox passwordAverageLength
    }

    @FXML
    private void onPasswordMinLengthClick() {
        // Ваш код для обработки события нажатия на CheckBox passwordMinLength
    }

    @FXML
    private void onMostPopularPasswordClick() {
        // Ваш код для обработки события нажатия на CheckBox mostPopularPassword
    }

    @FXML
    private void onHowManyTimesMostPopularPasswordWasUsedClick() {
        // Ваш код для обработки события нажатия на CheckBox HowManyTimesMostPopularPasswordWasUsed
    }

    @FXML
    private void onMostPopularSymbolsClick() {
        mostPopularGroupSymbols.disableProperty().bind(mostPopularSymbols.selectedProperty().not());
        topMostPopularGroupSymbol.disableProperty().bind(mostPopularSymbols.selectedProperty().not());
    }

    @FXML
    private void onMostPopularGroupSymbolsClick() {
        groupSymbolLength.valueProperty().addListener((observable, oldValue, newValue) ->
                groupSymbolLength.setValue(newValue.intValue()));
        groupSymbolLength.disableProperty().bind(mostPopularGroupSymbols.selectedProperty().not());
    }

    @FXML
    private void onGroupSymbolLengthChange() {
        // Ваш код для обработки изменения значения Slider groupSymbolLength
    }

    @FXML
    private void onTopMostPopularGroupSymbolClick() {
        topGroupSymbolsLength.valueProperty().addListener((observable, oldValue, newValue) ->
                topGroupSymbolsLength.setValue(newValue.intValue()));
        topGroupSymbolsLength.disableProperty().bind(topMostPopularGroupSymbol.selectedProperty().not());
    }

    @FXML
    private void onTopGroupSymbolsLengthChange() {
        // Ваш код для обработки изменения значения Slider topGroupSymbolsLength
    }

    @FXML
    private void onMostPopularSymbolClick() {
        topPopularSymbol.disableProperty().bind(mostPopularSymbol.selectedProperty().not());
    }

    @FXML
    private void onTopPopularSymbolClick() {
        topPopularSymbolCount.valueProperty().addListener((observable, oldValue, newValue) ->
                topPopularSymbolCount.setValue(newValue.intValue()));
        topPopularSymbolCount.disableProperty().bind(topPopularSymbol.selectedProperty().not());
    }

    @FXML
    private void onTopPopularSymbolLengthChange() {
        // Ваш код для обработки изменения значения Slider topPopularSymbolLength
    }

}
