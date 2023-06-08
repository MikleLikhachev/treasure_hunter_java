package com.treasure_hunter_java.dictionary;

import com.treasure_hunter_java.Main;
import com.treasure_hunter_java.controllers.Controller;
import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * Класс GenerateDictionary отвечает за генерацию словаря на основе паролей из различных источников и применение фильтров к ним.
 */
public class GenerateDictionary {

    private ArrayList<Password> passwords = new ArrayList<>();
    private Path dictionaryForCombining;

    private boolean googleChromeIsSelected;
    private boolean operaIsSelected;
    private boolean chromiumIsSelected;
    private boolean atomIsSelected;
    private boolean directoryForCombiningIsSelected;

    /**
     * Извлекает пароли из файла.
     *
     * @param file Файл, содержащий пароли.
     */
    private void extractPassword(File file){

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String password;
                if (line.split("\\|").length == 3) {
                    password = line.split("\\| ")[2];
                } else {
                    password = line;
                }
                Optional<Password> foundPassword = passwords.stream()
                        .filter(p -> p.getPassword().equals(password)).findFirst();
                if (foundPassword.isPresent()) {
                    Password currentPassword = foundPassword.get();
                    currentPassword.increaseCountUsed();
                } else {
                    passwords.add(new Password(password));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Извлекает пароли из Google Chrome, если выбрано соответствующее условие.
     */
    private void extractGooglePasswords() {
        if (googleChromeIsSelected) {
            extractPassword(new File(Main.mainWorkDirectory + "/chrome_data/passwords.txt"));
        }
    }

    /**
     * Извлекает пароли из Opera, если выбрано соответствующее условие.
     */
    private void extractOperaPasswords(){
        if (operaIsSelected) {
            extractPassword(new File(Main.mainWorkDirectory + "/opera_data/passwords.txt"));
        }
    }

    /**
     * Извлекает пароли из Chromium, если выбрано соответствующее условие.
     */
    private void extractChromiumPasswords(){
        if (chromiumIsSelected) {
            extractPassword(new File(Main.mainWorkDirectory + "/chromium_data/passwords.txt"));
        }
    }

    /**
     * Извлекает пароли из Atom, если выбрано соответствующее условие.
     */
    private void extractAtomPasswords(){
        if (atomIsSelected) {
            extractPassword(new File(Main.mainWorkDirectory + "/atom_data/passwords.txt"));
        }
    }

    /**
     * Конструктор класса GenerateDictionary.
     */
    public GenerateDictionary() {}

    /**
     * Устанавливает флаг выбора Google Chrome.
     *
     * @param googleChromeIsSelected Флаг выбора Google Chrome.
     */
    public void setGoogleChromeIsSelected(boolean googleChromeIsSelected){
        this.googleChromeIsSelected = googleChromeIsSelected;
    }

    /**
     * Устанавливает флаг выбора Opera.
     *
     * @param operaIsSelected Флаг выбора Opera.
     */
    public void setOperaIsSelected(boolean operaIsSelected) {
        this.operaIsSelected = operaIsSelected;
    }

    /**
     * Устанавливает флаг выбора Chromium.
     *
     * @param chromiumIsSelected Флаг выбора Chromium.
     */
    public void setChromiumIsSelected(boolean chromiumIsSelected){
        this.chromiumIsSelected = chromiumIsSelected;
    }

    /**
     * Устанавливает флаг выбора Atom.
     *
     * @param atomIsSelected Флаг выбора Atom.
     */
    public void setAtomIsSelected(boolean atomIsSelected){
        this.atomIsSelected = atomIsSelected;
    }

    /**
     * Устанавливает флаг выбора директории для объединения словарей.
     *
     * @param directoryForCombiningIsSelected Флаг выбора директории для объединения словарей.
     */
    public void setDirectoryForCombiningIsSelected(boolean directoryForCombiningIsSelected){
        this.directoryForCombiningIsSelected = directoryForCombiningIsSelected;
    }

    /**
     * Устанавливает путь к директории для объединения словарей.
     *
     * @param path Путь к директории для объединения словарей.
     */
    public void setDictionaryForCombining(Path path){
        this.dictionaryForCombining = path;
    }

    /**
     * Генерирует имя для словаря на основе фильтра.
     *
     * @param filter Фильтр для генерации имени словаря.
     * @return Имя словаря.
     */
    private String generateNameForDictionary(Filter filter) {
        File directory = new File(Main.mainWorkDirectory + "/filters");
        if (!directory.exists()) {
            directory.mkdir();
        }
        String name = "/filters/";
        if (!filter.getMask().isEmpty()) {name += filter.getMask().replace("*", "#");}
        if (filter.isStrict()) {name += "ST";}
        else {name += "NST";}
        name += "-L(" + filter.getMinLength() + "-" + filter.getMaxLength() + ")";
        if (filter.isContainsCapitalLetters()){
            name += "-CL(" + filter.getMinCountCapitalLetters() + "-" + filter.getMaxCountCapitalLetters() + ")";
        }
        if (filter.isContainsSmallLetters()){
            name += "-SL(" + filter.getMinCountSmallLetters() + "-" + filter.getMaxCountSmallLetters() + ")";
        }
        if (filter.isContainsSpecialSign()){
            name += "-SS(" + filter.getMinCountSpecialSign() + "-" + filter.getMaxCountSpecialSign() + ")";
        }
        if (filter.isContainsDigits()){
            name += "-D(" + filter.getMinCountDigits() + "-" + filter.getMaxCountDigits() + ")";
        }
        if (filter.isContainsSpace()){
            name += "-S";
        }
        return "/" + name + ".txt";
    }

    /**
     * Компилирует словарь на основе указанного фильтра.
     *
     * @param filter Фильтр для компиляции словаря.
     * @throws IOException Если произошла ошибка ввода-вывода.
     */
    public void compileDictionary(Filter filter) throws IOException {

        passwords.clear();
        extractGooglePasswords();
        extractChromiumPasswords();
        extractOperaPasswords();
        extractAtomPasswords();
        combiningDictionaries();
        FilterFunctionality filterFunctionality = new FilterFunctionality(passwords);

        File dictionaryFile = new File(Main.mainWorkDirectory.toUri());
        try (FileWriter fw = new FileWriter(dictionaryFile + generateNameForDictionary(filter));
             BufferedWriter bw = new BufferedWriter(fw))
        {
            for (Password pas : filterFunctionality.filterPasswords(filter))
            {
                bw.write(pas.getPassword() + "\n");
            }
        }
    }

    /**
     * Объединяет словари из указанной директории.
     *
     * @throws IOException Если произошла ошибка ввода-вывода.
     */
    private void combiningDictionaries() throws IOException {

        if (directoryForCombiningIsSelected) {
            Path directory = Paths.get(dictionaryForCombining.toUri());

            Files.walkFileTree(directory, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
                    if (Files.isRegularFile(filePath) && filePath.toString().endsWith(".txt")) {
                        extractPassword(filePath.toFile());
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    Controller.showDialog("Failed to access file:" + file, Alert.AlertType.ERROR);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }
}