package com.treasure_hunter_java.decrypt;

import com.treasure_hunter_java.Main;
import com.treasure_hunter_java.browsers.Atom;
import com.treasure_hunter_java.browsers.Chrome;
import com.treasure_hunter_java.browsers.Chromium;
import com.treasure_hunter_java.browsers.Opera;
import javafx.scene.control.CheckBox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ExtractFunctionality {

    private CopyFiles copyFiles = new CopyFiles();

    private void writeData(String text, String dataName, String browserDirectory) throws IOException {

        File file = new File(Main.mainWorkDirectory + browserDirectory + dataName);

        try (FileWriter fw = new FileWriter(file);
             BufferedWriter bw = new BufferedWriter(fw))
        {
            bw.write(text);
        }
    }

    private void extractData(String browserName, CheckBox passwords, List<Path> profiles,
                             Path localState, CheckBox cookies, CheckBox history) throws Exception {
        if (passwords.isSelected()){
            copyFiles.copyLoginData(profiles, localState, browserName);
            writeData(Decrypt.decryptPasswords(Main.mainWorkDirectory + browserName),
                    "passwords.txt", browserName);
        }

        if (cookies.isSelected()){
            copyFiles.copyCookies(profiles, browserName);
            writeData(Decrypt.extractCookies(Main.mainWorkDirectory.toString()+browserName),
                    "cookies.txt", browserName);
        }

        if (history.isSelected()){
            copyFiles.copyHistory(profiles, browserName);
            writeData(Decrypt.getHistory(Main.mainWorkDirectory.toString() + browserName + "History"),
                    "history.txt", browserName);
        }
    }

    public void getGoogleData(CheckBox passwordsGoogle, CheckBox cookiesChrome, CheckBox historyGoogle) throws Exception {

        Chrome chrome = new Chrome();
        chrome.collectProfiles();
        String browserName = "/chrome_data/";
        extractData(browserName, passwordsGoogle, chrome.getProfiles(),
                chrome.getLocalState(), cookiesChrome, historyGoogle);
    }

    public void getAtomData(CheckBox passwordsAtom, CheckBox cookiesAtom, CheckBox historyAtom) throws Exception {

        Atom atom = new Atom();
        atom.collectProfiles();
        String browserName = "/atom_data/";
        extractData(browserName, passwordsAtom, atom.getProfiles(),
                atom.getLocalState(), cookiesAtom, historyAtom);
    }

    public void getOperaData(CheckBox passwordsOpera, CheckBox cookiesOpera, CheckBox historyOpera) throws Exception {

        Opera opera = new Opera();
        opera.collectProfiles();
        String browserName = "/opera_data/";
        extractData(browserName, passwordsOpera, opera.getProfiles(),
                opera.getLocalState(), cookiesOpera, historyOpera);
    }

    public void getChromiumData(CheckBox passwordsChromium, CheckBox cookiesChromium, CheckBox historyChromium) throws Exception {

        Chromium chromium = new Chromium();
        chromium.collectProfiles();
        String browserName = "/chromium_data/";
        extractData(browserName, passwordsChromium, chromium.getProfiles(),
                chromium.getLocalState(), cookiesChromium, historyChromium);
    }
}
