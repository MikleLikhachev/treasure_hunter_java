package com.treasure_hunter_java.dictionary;

import com.treasure_hunter_java.Main;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;

public class GenerateDictionary {

    private final ArrayList<Password> passwords = new ArrayList<>();
    public Path dictionaryForCombining;

    private boolean strictFilter;
    private boolean googleChromeIsSelected;
    private boolean operaIsSelected;
    private boolean chromiumIsSelected;
    private boolean atomIsSelected;

    private void extractPassword(File file){

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //String password = line.split("\\| ")[2];
                String password;
                if (line.split("\\|").length == 3) {
                    password = line.split("\\|")[2];
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

    private void extractGooglePasswords() {
        if (googleChromeIsSelected) {
            extractPassword(new File(Main.mainWorkDirectory + "/chrome_data/passwords.txt"));
        }
    }

    private void extractOperaPasswords(){
        if (operaIsSelected) {
            extractPassword(new File(Main.mainWorkDirectory + "/opera_data/passwords.txt"));
        }
    }

    private void extractChromiumPasswords(){
        if (chromiumIsSelected) {
            extractPassword(new File(Main.mainWorkDirectory + "/chromium_data/passwords.txt"));
        }
    }

    private void extractAtomPasswords(){
        if (atomIsSelected) {
            extractPassword(new File(Main.mainWorkDirectory + "/atom_data/passwords.txt"));
        }
    }

    private boolean compare(int filterFrom, int passwordData, int filterTo){

        return (passwordData >= filterFrom && passwordData <= filterTo);

    }

    private List<Password> filterPasswords(Filter filter) {

        if (strictFilter) {
            return passwords.stream()
                    .filter(p -> (compare(filter.getminLength(), p.getLength(), filter.getmaxLength()))
                            && (!filter.isContainsCapitalLetters
                                || compare(filter.getminCountCapitalLetters(), p.getCountCapitalLetters(),
                                    filter.getmaxCountCapitalLetters()))
                            && (!filter.isContainsSmallLetters
                                || compare(filter.getminCountSmallLetters(), p.getCountSmallLetters(),
                                    filter.getmaxCountSmallLetters()))
                            && (!filter.isContainsDigits
                                || compare(filter.getminCountDigits(), p.getCountDigits(), filter.getmaxCountDigits()))
                            && (!filter.isContainsSpecialSign
                                || compare(filter.getminCountSpecialSign(), p.getCountSpecialSign(),
                                    filter.getmaxCountSpecialSign()))
                            && (!filter.isContainsSpace || p.getPassword().matches(".*\\s+.*"))
                            && (filter.getMask().isEmpty() || p.getPassword().matches(filter.getMask())))
                    .collect(Collectors.toList());
        } else {
            return passwords.stream()
                    .filter(p -> compare(filter.getminLength(), p.getLength(), filter.getmaxLength())
                            && ((!filter.isContainsCapitalLetters ||
                                compare(filter.getminCountCapitalLetters(), p.getCountCapitalLetters(),
                                        filter.getmaxCountCapitalLetters()))
                            || (!filter.isContainsSmallLetters ||
                                compare(filter.getminCountSmallLetters(), p.getCountSmallLetters(),
                                        filter.getmaxCountSmallLetters()))
                            || (!filter.isContainsDigits ||
                                compare(filter.getminCountDigits(), p.getCountDigits(), filter.getmaxCountDigits()))
                            || (!filter.isContainsSpecialSign ||
                                compare(filter.getminCountSpecialSign(), p.getCountSpecialSign(),
                                        filter.getmaxCountSpecialSign())))
                            || (!filter.isContainsSpace || p.getPassword().matches(".*\\s+.*")))
                    .collect(Collectors.toList());

        }
    }

    public GenerateDictionary() {}

    public void setGoogleChromeIsSelected(boolean googleChromeIsSelected){
        this.googleChromeIsSelected = googleChromeIsSelected;
    }

    public void setOperaIsSelected(boolean operaIsSelected) {
        this.operaIsSelected = operaIsSelected;
    }

    public void setChromiumIsSelected(boolean chromiumIsSelected){
        this.chromiumIsSelected = chromiumIsSelected;
    }

    public void setAtomIsSelected(boolean atomIsSelected){
        this.atomIsSelected = atomIsSelected;
    }

    public void setStrictFilterIsSelected(boolean strictFilterIsSelected){
        this.strictFilter = strictFilterIsSelected;
    }

    public void compileDictionary(Filter filter) throws IOException {

        extractGooglePasswords();
        extractChromiumPasswords();
        extractOperaPasswords();
        extractAtomPasswords();
        combiningDictionaries();

        File dictionaryFile = new File(Main.mainWorkDirectory.toUri());
        try (FileWriter fw = new FileWriter(dictionaryFile + "/TreasureHunterDictionary.txt");
             BufferedWriter bw = new BufferedWriter(fw))
        {
            for (Password pas : filterPasswords(filter))
            {
                bw.write(pas.getPassword() + "\n");
            }
        }
    }

    private void combiningDictionaries() throws IOException {

        Path directory = Paths.get(dictionaryForCombining.toUri());

        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
                if (Files.isRegularFile(filePath) && filePath.toString().endsWith(".txt")) {
                    // Чтение содержимого файла
                    extractPassword(filePath.toFile());
                    /*System.out.println("File: " + filePath);
                    System.out.println("Content: " + content);
                    System.out.println("----------------------------------------");*/
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                // Обработка ошибок доступа к файлу
                System.err.println("Failed to access file: " + file);
                return FileVisitResult.CONTINUE;
            }
        });


    }
}