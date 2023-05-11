package com.treasure_hunter_java;

import com.treasure_hunter_java.dictionary.Filter;
import com.treasure_hunter_java.dictionary.GenerateDictionary;
import com.treasure_hunter_java.dictionary.Password;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class DictionaryController {

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
    private Slider lengthFrom;

    @FXML
    private Slider lengthTo;

    @FXML
    private Slider countCapitalLettersFrom;

    @FXML
    private Slider countCapitalLettersTo;

    @FXML
    private Slider countSmallLettersFrom;

    @FXML
    private Slider countSmallLettersTo;

    @FXML
    private Slider countDigitsFrom;

    @FXML
    private Slider countDigitsTo;

    @FXML
    private Slider countSpecialSignFrom;

    @FXML
    private Slider countSpecialSignTo;

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

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/MainScene.fxml")));
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
    public void onStartButtonClick(ActionEvent actionEvent) {

        Filter filter = new Filter(isContainsCapitalLetters.isSelected(),
                isContainsSmallLetters.isSelected(), isContainsDigits.isSelected(), isContainsSpecialSign.isSelected(),
                isContainsSpace.isSelected(), (int) lengthFrom.getValue(), (int) lengthTo.getValue(),
                (int) countDigitsFrom.getValue(), (int)countDigitsTo.getValue(), (int) countSpecialSignFrom.getValue(),
                (int) countSpecialSignTo.getValue(), (int) countCapitalLettersFrom.getValue(),
                (int) countCapitalLettersTo.getValue(), (int) countSmallLettersFrom.getValue(),
                (int) countSmallLettersTo.getValue());

        this.dictionary.setGoogleChromeIsSelected(googleChrome.isSelected());
        this.dictionary.setChromiumIsSelected(chromium.isSelected());
        this.dictionary.setOperaIsSelected(opera.isSelected());
        this.dictionary.setAtomIsSelected(atom.isSelected());
        this.dictionary.setStrictFilterIsSelected(strictFilter.isSelected());
        this.dictionary.compileDictionary(filter);
    }

    @FXML
    private void onDigitsClick(){
        countDigitsFrom.valueProperty().addListener((observable, oldValue, newValue) -> {
            countDigitsFrom.setValue(newValue.intValue());
        });
        countDigitsTo.valueProperty().addListener((observable, oldValue, newValue) -> {
            countDigitsTo.setValue(newValue.intValue());
        });
        countDigitsFrom.disableProperty().bind(isContainsDigits.selectedProperty().not());
        countDigitsTo.disableProperty().bind(isContainsDigits.selectedProperty().not());


    }

    @FXML
    private void onSpecialSignClick(){
        countSpecialSignFrom.valueProperty().addListener((observable, oldValue, newValue) -> {
            countSpecialSignFrom.setValue(newValue.intValue());
        });
        countSpecialSignTo.valueProperty().addListener((observable, oldValue, newValue) -> {
            countSpecialSignTo.setValue(newValue.intValue());
        });
        countSpecialSignFrom.disableProperty().bind(isContainsSpecialSign.selectedProperty().not());
        countSpecialSignTo.disableProperty().bind(isContainsSpecialSign.selectedProperty().not());

    }

    @FXML
    private void onCapitalLettersClick(){
        countCapitalLettersFrom.valueProperty().addListener((observable, oldValue, newValue) -> {
            countCapitalLettersFrom.setValue(newValue.intValue());
        });
        countCapitalLettersTo.valueProperty().addListener((observable, oldValue, newValue) -> {
            countCapitalLettersTo.setValue(newValue.intValue());
        });
        countCapitalLettersFrom.disableProperty().bind(isContainsCapitalLetters.selectedProperty().not());
        countCapitalLettersTo.disableProperty().bind(isContainsCapitalLetters.selectedProperty().not());
    }

    @FXML
    private void onSmallLettersClick(){
        countSmallLettersFrom.valueProperty().addListener((observable, oldValue, newValue) -> {
            countSmallLettersFrom.setValue(newValue.intValue());
        });
        countSmallLettersTo.valueProperty().addListener((observable, oldValue, newValue) -> {
            countSmallLettersTo.setValue(newValue.intValue());
        });
        countSmallLettersFrom.disableProperty().bind(isContainsSmallLetters.selectedProperty().not());
        countSmallLettersTo.disableProperty().bind(isContainsSmallLetters.selectedProperty().not());
    }

}