package com.treasure_hunter_java.dictionary;

import com.treasure_hunter_java.Main;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.regex.Pattern;

public class GenerateDictionary {

    private ArrayList<Password> passwords = new ArrayList<>();
    public Path dictionaryForCombining;

    private boolean strictFilter;
    private boolean googleChromeIsSelected;
    private boolean operaIsSelected;
    private boolean chromiumIsSelected;
    private boolean atomIsSelected;
    private boolean directoryForCombiningIsSelected;

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

    public void setDictionaryForCombiningIsSelected(boolean directoryForCombiningIsSelected){
        this.directoryForCombiningIsSelected = directoryForCombiningIsSelected;
    }

    private String generateNameForDictionary(Filter filter) {
        File directory = new File(Main.mainWorkDirectory + "/filters");
        if (!directory.exists()) {
            directory.mkdir();
        }
        String name = "/filters/";
        if (!filter.getMask().isEmpty()) {name += filter.getMask();}
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
                    System.err.println("Failed to access file: " + file);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }
}