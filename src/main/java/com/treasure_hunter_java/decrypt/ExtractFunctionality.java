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
import java.util.List;

/**
 * Класс ExtractFunctionality предоставляет функциональность для извлечения данных из браузеров.
 */
public class ExtractFunctionality {

    private CopyFiles copyFiles = new CopyFiles();

    /**
     * Записывает данные в файл.
     *
     * @param text             текст данных для записи в файл.
     * @param dataName         имя файла для записи данных.
     * @param browserDirectory директория браузера, в которую будет сохранен файл.
     * @throws IOException если возникают проблемы при записи данных в файл.
     */
    private void writeData(String text, String dataName, String browserDirectory) throws IOException {
        File file = new File(Main.mainWorkDirectory + browserDirectory + dataName);

        try (FileWriter fw = new FileWriter(file);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(text);
        }
    }

    /**
     * Извлекает данные из браузера.
     *
     * @param browserName имя браузера, из которого будут извлечены данные.
     * @param passwords   флажок, указывающий на необходимость извлечения паролей.
     * @param profiles    список путей к профилям браузера.
     * @param localState  путь к файлу Local State браузера.
     * @param cookies     флажок, указывающий на необходимость извлечения cookies.
     * @param history     флажок, указывающий на необходимость извлечения истории.
     * @throws Exception если возникают ошибки при извлечении данных из браузера.
     */
    private void extractData(String browserName, CheckBox passwords, List<Path> profiles,
                             Path localState, CheckBox cookies, CheckBox history) throws Exception {
        if (passwords.isSelected()) {
            copyFiles.copyLoginData(profiles, localState, browserName);
            writeData(Decrypt.decryptPasswords(Main.mainWorkDirectory + browserName),
                    "passwords.txt", browserName);
        }

        if (cookies.isSelected()) {
            copyFiles.copyCookies(profiles, browserName);
            writeData(Decrypt.extractCookies(Main.mainWorkDirectory.toString() + browserName),
                    "cookies.txt", browserName);
        }

        if (history.isSelected()) {
            copyFiles.copyHistory(profiles, browserName);
            writeData(Decrypt.getHistory(Main.mainWorkDirectory.toString() + browserName + "History"),
                    "history.txt", browserName);
        }
    }

    /**
     * Извлекает данные из Google Chrome.
     *
     * @param passwordsGoogle флажок, указывающий на необходимость извлечения паролей из Google Chrome.
     * @param cookiesChrome   флажок, указывающий на необходимость извлечения cookies из Google Chrome.
     * @param historyGoogle   флажок, указывающий на необходимость извлечения истории из Google Chrome.
     * @throws Exception если возникают ошибки при извлечении данных из Google Chrome.
     */
    public void getGoogleData(CheckBox passwordsGoogle, CheckBox cookiesChrome, CheckBox historyGoogle) throws Exception {
        Chrome chrome = new Chrome();
        chrome.collectProfiles();
        String browserName = "/chrome_data/";
        extractData(browserName, passwordsGoogle, chrome.getProfiles(),
                chrome.getLocalState(), cookiesChrome, historyGoogle);
    }

    /**
     * Извлекает данные из Atom.
     *
     * @param passwordsAtom флажок, указывающий на необходимость извлечения паролей из Atom.
     * @param cookiesAtom   флажок, указывающий на необходимость извлечения cookies из Atom.
     * @param historyAtom   флажок, указывающий на необходимость извлечения истории из Atom.
     * @throws Exception если возникают ошибки при извлечении данных из Atom.
     */
    public void getAtomData(CheckBox passwordsAtom, CheckBox cookiesAtom, CheckBox historyAtom) throws Exception {
        Atom atom = new Atom();
        atom.collectProfiles();
        String browserName = "/atom_data/";
        extractData(browserName, passwordsAtom, atom.getProfiles(),
                atom.getLocalState(), cookiesAtom, historyAtom);
    }

    /**
     * Извлекает данные из Opera.
     *
     * @param passwordsOpera флажок, указывающий на необходимость извлечения паролей из Opera.
     * @param cookiesOpera   флажок, указывающий на необходимость извлечения cookies из Opera.
     * @param historyOpera   флажок, указывающий на необходимость извлечения истории из Opera.
     * @throws Exception если возникают ошибки при извлечении данных из Opera.
     */
    public void getOperaData(CheckBox passwordsOpera, CheckBox cookiesOpera, CheckBox historyOpera) throws Exception {
        Opera opera = new Opera();
        opera.collectProfiles();
        String browserName = "/opera_data/";
        extractData(browserName, passwordsOpera, opera.getProfiles(),
                opera.getLocalState(), cookiesOpera, historyOpera);
    }

    /**
     * Извлекает данные из Chromium.
     *
     * @param passwordsChromium флажок, указывающий на необходимость извлечения паролей из Chromium.
     * @param cookiesChromium   флажок, указывающий на необходимость извлечения cookies из Chromium.
     * @param historyChromium   флажок, указывающий на необходимость извлечения истории из Chromium.
     * @throws Exception если возникают ошибки при извлечении данных из Chromium.
     */
    public void getChromiumData(CheckBox passwordsChromium, CheckBox cookiesChromium, CheckBox historyChromium) throws Exception {
        Chromium chromium = new Chromium();
        chromium.collectProfiles();
        String browserName = "/chromium_data/";
        extractData(browserName, passwordsChromium, chromium.getProfiles(),
                chromium.getLocalState(), cookiesChromium, historyChromium);
    }
}