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

public class DictionaryController extends Controller{

    private final GenerateDictionary dictionary = new GenerateDictionary();

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


    @FXML
    @Override
    protected void onSearchPasswordsButtonClick() throws IOException {
        super.onSearchPasswordsButtonClick();
    }

    @FXML
    @Override
    protected void onReportButtonClick() throws IOException {
        super.onReportButtonClick();
    }

    @FXML
    @Override
    protected void onZipButtonClick() throws IOException {
        super.onZipButtonClick();
    }

    @FXML
    @Override
    protected void onTelegramButtonClick() throws IOException {
        super.onTelegramButtonClick();
    }

    @FXML
    @Override
    protected void onSelectDirectoryButtonClick() {
        super.onSelectDirectoryButtonClick();
    }

    @FXML
    public void onStartButtonClick(ActionEvent actionEvent) throws IOException {

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
        this.dictionary.setGoogleChromeIsSelected(googleChrome.isSelected());
        this.dictionary.setChromiumIsSelected(chromium.isSelected());
        this.dictionary.setOperaIsSelected(opera.isSelected());
        this.dictionary.setAtomIsSelected(atom.isSelected());
        this.dictionary.setDictionaryForCombiningIsSelected(combiningDictionaries.isSelected());
        this.dictionary.compileDictionary(filter);
    }

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

    @FXML
    private void onDigitsClick() {
        onCriteriaClick(minCountDigits, maxCountDigits, isContainsDigits);
    }

    @FXML
    private void onSpecialSignClick() {
        onCriteriaClick(minCountSpecialSign, maxCountSpecialSign, isContainsSpecialSign);
    }

    @FXML
    private void onCapitalLettersClick() {
        onCriteriaClick(minCountCapitalLetters, maxCountCapitalLetters, isContainsCapitalLetters);
    }

    @FXML
    private void onSmallLettersClick() {
        onCriteriaClick(minCountSmallLetters, maxCountSmallLetters, isContainsSmallLetters);
    }

    @FXML
    private void onCombiningDictionaryClick(){
        selectDictionaryForCombining.disableProperty().bind(combiningDictionaries.selectedProperty().not());

    }

    @FXML
    private void onSelectDictionaryForCombiningClick(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(super.currentStage);

        if (selectedDirectory != null) {
            dictionary.setDictionaryForCombining(Path.of(selectedDirectory.getAbsolutePath()));
        } else {
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