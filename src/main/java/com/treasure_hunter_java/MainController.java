package com.treasure_hunter_java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Button searchPasswordsButton;

    @FXML
    private CheckBox passwordsGoogle;

    @FXML
    private CheckBox historyGoogle;

    @FXML
    private Button generateDictionaryButton;

    @FXML
    private Button reportButton;

    @FXML
    private Button zipButton;

    @FXML
    private Button telegramButton;

    @FXML
    private Button start;

    @FXML
    protected void onSearchPasswordsButtonClick() {

        generateDictionaryButton.setStyle("-fx-background-color: ffffff");
        reportButton.setStyle("-fx-background-color: ffffff");
        searchPasswordsButton.setStyle("-fx-background-color: #cccccc");
    }

    @FXML
    protected void onStartButtonClick() throws Exception {
        if (passwordsGoogle.isSelected()){
            System.out.println(Decrypt.decryptPasswords());
        }
        if (historyGoogle.isSelected()){
            System.out.println(Decrypt.getHistory());
        }
    }

    @FXML
    protected void onGenerateDictionaryButtonClick() {
        searchPasswordsButton.setStyle("-fx-background-color: ffffff");
        reportButton.setStyle("-fx-background-color: ffffff");
        telegramButton.setStyle("-fx-background-color: ffffff");
        zipButton.setStyle("-fx-background-color: ffffff");
        generateDictionaryButton.setStyle("-fx-background-color: #cccccc");
    }

    @FXML
    protected void onReportButtonClick() throws Exception {
        searchPasswordsButton.setStyle("-fx-background-color: ffffff");
        generateDictionaryButton.setStyle("-fx-background-color: ffffff");
        telegramButton.setStyle("-fx-background-color: ffffff");
        zipButton.setStyle("-fx-background-color: ffffff");
        reportButton.setStyle("-fx-background-color: #cccccc");
        CollectProfiles test = new CollectProfiles();
        test.generatePathsToProfile();
        test.copyFiles();
        Decrypt decrypt = new Decrypt();
        System.out.println(Decrypt.getMasterKey());
        System.out.println(Decrypt.decryptPasswords());
    }

    @FXML
    protected void onZipButtonClick() {
        searchPasswordsButton.setStyle("-fx-background-color: ffffff");
        reportButton.setStyle("-fx-background-color: ffffff");
        generateDictionaryButton.setStyle("-fx-background-color: ffffff");
        telegramButton.setStyle("-fx-background-color: ffffff");
        zipButton.setStyle("-fx-background-color: cccccc");
    }

    @FXML
    protected void onTelegramButtonClick() {
        searchPasswordsButton.setStyle("-fx-background-color: ffffff");
        reportButton.setStyle("-fx-background-color: ffffff");
        generateDictionaryButton.setStyle("-fx-background-color: ffffff");
        zipButton.setStyle("-fx-background-color: ffffff");
        telegramButton.setStyle("-fx-background-color: cccccc");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchPasswordsButton.setStyle("-fx-background-color: #cccccc");
    }
}