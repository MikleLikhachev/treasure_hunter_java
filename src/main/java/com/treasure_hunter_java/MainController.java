package com.treasure_hunter_java;

import com.treasure_hunter_java.browsers.*;
import com.treasure_hunter_java.dictionary.GenerateDictionary;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
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
    private CheckBox passwordsAtom;

    @FXML
    private CheckBox historyAtom;

    @FXML
    private CheckBox passwordsOpera;

    @FXML
    private CheckBox historyOpera;

    @FXML
    private CheckBox passwordsChromium;

    @FXML
    private CheckBox historyChromium;

    @FXML
    private Button generateDictionaryButton;

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

    public static Stage currentStage;

    private void writeData(String text, String dataName, String browserDirectory) throws IOException {

        File file = new File(Main.mainWorkDirectory + browserDirectory + dataName);

        try (FileWriter fw = new FileWriter(file);
             BufferedWriter bw = new BufferedWriter(fw))
        {
            bw.write(text);
        }

    }
    private void getGoogleData(CopyFiles copyFiles) throws Exception {

        Chrome chrome = new Chrome();
        chrome.collectProfiles();
        String browserName = "/chrome_data/";

        if (passwordsGoogle.isSelected()){
            copyFiles.copyLoginData(chrome.getProfiles(), chrome.getLocalState(), browserName);
            writeData(Decrypt.decryptPasswords(Main.mainWorkDirectory + browserName),
                    "passwords.txt", browserName);
        }

        if (historyGoogle.isSelected()){
            copyFiles.copyHistory(chrome.getProfiles(), chrome.getLocalState(), browserName);
            writeData(Decrypt.getHistory(Main.mainWorkDirectory.toString() + browserName + "History"),
                    "history.txt", browserName);
        }
    }

    private void getAtomData(CopyFiles copyFiles) throws Exception {

        Atom atom = new Atom();
        atom.collectProfiles();
        String browserName = "/atom_data/";

        if (passwordsAtom.isSelected()) {
            copyFiles.copyLoginData(atom.getProfiles(), atom.getLocalState(), browserName);
            writeData(Decrypt.decryptPasswords(Main.mainWorkDirectory + browserName),
                    "passwords.txt", browserName);
        }

        if (historyAtom.isSelected()){
            copyFiles.copyHistory(atom.getProfiles(), atom.getLocalState(), browserName);
            writeData(Decrypt.getHistory(Main.mainWorkDirectory.toString() + browserName + "History"),
                    "history.txt", browserName);
        }
    }

    private void getOperaData(CopyFiles copyFiles) throws Exception {

        Opera opera = new Opera();
        opera.collectProfiles();
        String browserName = "/opera_data/";

        if (passwordsOpera.isSelected()) {
            copyFiles.copyLoginData(opera.getProfiles(), opera.getLocalState(), browserName);
            writeData(Decrypt.decryptPasswords(Main.mainWorkDirectory + browserName),
                    "passwords.txt", browserName);
        }

        if (historyOpera.isSelected()) {
            copyFiles.copyHistory(opera.getProfiles(), opera.getLocalState(), browserName);
            writeData(Decrypt.getHistory(Main.mainWorkDirectory.toString() + browserName + "History"),
                    "history.txt", browserName);
        }
    }

    private void getChromiumData(CopyFiles copyFiles) throws Exception {

        Chromium chromium = new Chromium();
        chromium.collectProfiles();
        String browserName = "/chromium_data/";

        if (passwordsChromium.isSelected()) {
            copyFiles.copyLoginData(chromium.getProfiles(), chromium.getLocalState(), browserName);
            writeData(Decrypt.decryptPasswords(Main.mainWorkDirectory + browserName),
                    "passwords.txt", browserName);
        }

        if (historyChromium.isSelected()) {
            copyFiles.copyHistory(chromium.getProfiles(), chromium.getLocalState(), browserName);
            writeData(Decrypt.getHistory(Main.mainWorkDirectory.toString() + browserName + "History"),
                    "history.txt", browserName);
        }
    }

    private final String white = "-fx-background-color: #ffffff";

    private final String grey =  "-fx-background-color: #cccccc";

    @FXML
    protected void onSearchPasswordsButtonClick() {

        generateDictionaryButton.setStyle(white);
        reportButton.setStyle(white);
        telegramButton.setStyle(white);
        zipButton.setStyle(white);
        searchPasswordsButton.setStyle(grey);
    }

    @FXML
    protected void onStartButtonClick() throws Exception {

        getGoogleData(new CopyFiles());
        getAtomData(new CopyFiles());
        getOperaData(new CopyFiles());
        getChromiumData(new CopyFiles());
        GenerateDictionary test = new GenerateDictionary();
        test.extractPassword(new File(Main.mainWorkDirectory + "/chrome_data/passwords.txt"));
        test.searchCapitalsLetters();

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchPasswordsButton.setStyle(grey);
    }
}