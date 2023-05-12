package com.treasure_hunter_java.controllers;

import com.treasure_hunter_java.Main;
import com.treasure_hunter_java.dictionary.Filter;
import com.treasure_hunter_java.dictionary.GenerateDictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class DictionaryController implements Initializable{

    private final String white = "-fx-background-color: #ffffff";

    private final String grey =  "-fx-background-color: #cccccc";

    private final GenerateDictionary dictionary = new GenerateDictionary();

    @FXML
    private Button searchPasswordsButton;

    @FXML
    private Button generateDictionaryButton;

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
    private CheckBox isOnlyContainsCapitalLetters;

    @FXML
    private CheckBox isContainsSmallLetters;

    @FXML
    private CheckBox isOnlyContainsSmallLetters;

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
    public void onGenerateDictionaryButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onSearchPasswordsButtonClick(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/treasure_hunter_java/fxml/MainScene.fxml")));
        JMetro jMetro = new JMetro(root, Style.LIGHT);
        Scene scene = new Scene(root, 750, 500);

        MainController.currentStage.setScene(scene);
        MainController.currentStage.show();

    }

    @FXML
    public void onReportButtonClick(ActionEvent actionEvent) {
        searchPasswordsButton.setStyle(white);
        generateDictionaryButton.setStyle(white);
        telegramButton.setStyle(white);
        zipButton.setStyle(white);
        reportButton.setStyle(grey);
    }

    @FXML
    public void onZipButtonClick(ActionEvent actionEvent) {
        searchPasswordsButton.setStyle(white);
        reportButton.setStyle(white);
        generateDictionaryButton.setStyle(white);
        telegramButton.setStyle(white);
        zipButton.setStyle(grey);
    }

    @FXML
    public void onTelegramButtonClick(ActionEvent actionEvent) {
        searchPasswordsButton.setStyle(white);
        reportButton.setStyle(white);
        generateDictionaryButton.setStyle(white);
        zipButton.setStyle(white);
        telegramButton.setStyle(grey);
    }

    @FXML
    protected void onSelectDirectoryButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(MainController.currentStage);

        if (selectedDirectory != null) {
            Main.mainWorkDirectory = Path.of(selectedDirectory.getAbsolutePath());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Treasure hunter");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Вы не выбрали рабочую папку.");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/icon.png"))));
            alert.showAndWait();
        }
    }

    @FXML
    public void onStartButtonClick(ActionEvent actionEvent) throws IOException {

        Filter filter = new Filter(isContainsCapitalLetters.isSelected(),
                isContainsSmallLetters.isSelected(), isContainsDigits.isSelected(), isContainsSpecialSign.isSelected(),
                isContainsSpace.isSelected(), (int) minLength.getValue(), (int) maxLength.getValue(),
                (int) minCountDigits.getValue(), (int)maxCountDigits.getValue(), (int) minCountSpecialSign.getValue(),
                (int) maxCountSpecialSign.getValue(), (int) minCountCapitalLetters.getValue(),
                (int) maxCountCapitalLetters.getValue(), (int) minCountSmallLetters.getValue(),
                (int) maxCountSmallLetters.getValue(), mask.getText());

        this.dictionary.setGoogleChromeIsSelected(googleChrome.isSelected());
        this.dictionary.setChromiumIsSelected(chromium.isSelected());
        this.dictionary.setOperaIsSelected(opera.isSelected());
        this.dictionary.setAtomIsSelected(atom.isSelected());
        this.dictionary.setStrictFilterIsSelected(strictFilter.isSelected());
        this.dictionary.compileDictionary(filter);
    }

    private void onCriteriaClick(Slider minSlider, Slider maxSlider, CheckBox criteriaCheckbox) {
        minSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            minSlider.setValue(newValue.intValue());
        });
        maxSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            maxSlider.setValue(newValue.intValue());
        });
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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        minLength.valueProperty().addListener((observable, oldValue, newValue) -> {
            minLength.setValue(newValue.intValue());
        });
        maxLength.valueProperty().addListener((observable, oldValue, newValue) -> {
            maxLength.setValue(newValue.intValue());
        });
    }

}