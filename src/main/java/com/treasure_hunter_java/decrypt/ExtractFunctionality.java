package com.treasure_hunter_java.decrypt;

import com.treasure_hunter_java.browsers.Atom;
import com.treasure_hunter_java.browsers.Chrome;
import com.treasure_hunter_java.browsers.Chromium;
import com.treasure_hunter_java.browsers.Opera;
import com.treasure_hunter_java.directory.Directory;
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

    private final CopyFiles copyFiles = new CopyFiles();

    /**
     * Записывает данные в файл.
     *
     * @param text             текст данных для записи в файл.
     * @param dataName         имя файла для записи данных.
     * @param browserDirectory директория браузера, в которую будет сохранен файл.
     * @throws IOException если возникают проблемы при записи данных в файл.
     */
    private void writeData(String text, String dataName, String browserDirectory) throws IOException {
        File file = new File(Directory.getWorkDirectory() + browserDirectory + dataName);

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
        Decrypt decrypt = new Decrypt();

        extractPasswords(decrypt, browserName, passwords, profiles, localState);
        extractCookies(decrypt, browserName, cookies, profiles);
        extractHistory(decrypt, browserName, history, profiles);
    }

    /**
     * Метод для извлечения паролей из браузеров.
     *
     * @param decrypt      объект класса Decrypt для расшифровки данных
     * @param browserName  название браузера
     * @param passwords    флажок, указывающий, нужно ли извлекать пароли
     * @param profiles     список путей к профилям браузера
     * @param localState   путь к файлу Local State
     * @throws Exception если происходит ошибка при извлечении паролей
     */
    private void extractPasswords(Decrypt decrypt, String browserName, CheckBox passwords,
                                  List<Path> profiles, Path localState) throws Exception {
        if (passwords.isSelected()) {
            // Копируем файл Login Data для извлечения паролей
            copyFiles.copyLoginData(profiles, localState, browserName);

            // Расшифровываем пароли и записываем в файл
            writeData(decrypt.decryptPasswords(Directory.getWorkDirectory() + browserName),
                    "passwords.txt", browserName);
        }
    }

    /**
     * Метод для извлечения cookies из браузеров.
     *
     * @param decrypt      объект класса Decrypt для расшифровки данных
     * @param browserName  название браузера
     * @param cookies      флажок, указывающий, нужно ли извлекать cookies
     * @param profiles     список путей к профилям браузера
     * @throws Exception если происходит ошибка при извлечении cookies
     */
    private void extractCookies(Decrypt decrypt, String browserName, CheckBox cookies,
                                List<Path> profiles) throws Exception {
        if (cookies.isSelected()) {
            // Копируем файл Cookies для извлечения cookies
            copyFiles.copyCookies(profiles, browserName);

            // Извлекаем и записываем cookies в файл
            writeData(decrypt.extractCookies(Directory.getWorkDirectory().toString() + browserName),
                    "cookies.txt", browserName);
        }
    }

    /**
     * Метод для извлечения истории посещений из браузеров.
     *
     * @param decrypt      объект класса Decrypt для расшифровки данных
     * @param browserName  название браузера
     * @param history      флажок, указывающий, нужно ли извлекать историю
     * @param profiles     список путей к профилям браузера
     * @throws Exception если происходит ошибка при извлечении истории
     */
    private void extractHistory(Decrypt decrypt, String browserName, CheckBox history,
                                List<Path> profiles) throws Exception {
        if (history.isSelected()) {
            // Копируем файл истории для извлечения истории посещений
            copyFiles.copyHistory(profiles, browserName);

            // Извлекаем и записываем историю посещений в файл
            writeData(decrypt.getHistory(Directory.getWorkDirectory().toString() + browserName + "History"),
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