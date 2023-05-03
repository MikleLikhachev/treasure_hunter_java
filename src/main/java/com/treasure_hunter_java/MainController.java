package com.treasure_hunter_java;

import com.treasure_hunter_java.browsers.Chrome;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

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

    public static Stage currentStage;
    private void google() {

    }

    private final String white = "-fx-background-color: #ffffff";

    private final String grey =  "-fx-background-color: #cccccc";

    @FXML
    protected void onSearchPasswordsButtonClick() throws IOException {

        generateDictionaryButton.setStyle(white);
        reportButton.setStyle(white);
        telegramButton.setStyle(white);
        zipButton.setStyle(white);
        searchPasswordsButton.setStyle(grey);
    }

    @FXML
    protected void onStartButtonClick() throws Exception {
        CopyFiles test = new CopyFiles();
        Chrome chrome = new Chrome();
        chrome.collectProfiles();

        test.copyFiles(chrome.getProfiles(), chrome.getLocalState(), "/chrome_data");
        if (passwordsGoogle.isSelected()){
            System.out.println(Decrypt.decryptPasswords(Main.mainWorkDirectory.toString() + "/chrome_data"));
        }
        if (historyGoogle.isSelected()){
            System.out.println(Decrypt.getHistory());
        }
    }

    @FXML
    protected void onGenerateDictionaryButtonClick() throws IOException {
        searchPasswordsButton.setStyle(white);
        reportButton.setStyle(white);
        telegramButton.setStyle(white);
        zipButton.setStyle(white);
        generateDictionaryButton.setStyle(grey);
        currentStage = (Stage) searchPasswordsButton.getScene().getWindow();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/DictionaryScene.fxml")));
        JMetro jMetro = new JMetro(root, Style.LIGHT);
        Scene scene = new Scene(root, 750, 500);

        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    protected void onReportButtonClick() throws Exception {
        searchPasswordsButton.setStyle(white);
        generateDictionaryButton.setStyle(white);
        telegramButton.setStyle(white);
        zipButton.setStyle(white);
        reportButton.setStyle(grey);

        //Decrypt decrypt = new Decrypt();
        //System.out.println(Decrypt.getMasterKey());
        //System.out.println(Decrypt.decryptPasswords());
    }

    @FXML
    protected void onZipButtonClick() {
        searchPasswordsButton.setStyle(white);
        reportButton.setStyle(white);
        generateDictionaryButton.setStyle(white);
        telegramButton.setStyle(white);
        zipButton.setStyle(grey);
    }

    @FXML
    protected void onTelegramButtonClick() {
        searchPasswordsButton.setStyle(white);
        reportButton.setStyle(white);
        generateDictionaryButton.setStyle(white);
        zipButton.setStyle(white);
        telegramButton.setStyle(grey);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchPasswordsButton.setStyle(grey);
    }
}