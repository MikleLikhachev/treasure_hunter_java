package com.treasure_hunter_java.dictionary;

import com.treasure_hunter_java.Main;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GenerateDictionary {

    private final ArrayList<Password> passwords = new ArrayList<>();

    private boolean strictFilter;
    private boolean googleChromeIsSelected;
    private boolean operaIsSelected;
    private boolean chromiumIsSelected;
    private boolean atomIsSelected;

    private void extractPassword(File file){

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String password = line.split("\\| ")[2];
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
                    .filter(p -> (compare(filter.getLengthFrom(), p.getLength(), filter.getLengthTo()))
                            && (!filter.isContainsCapitalLetters
                                || compare(filter.getCountCapitalLettersFrom(), p.getCountCapitalLetters(),
                                    filter.getCountCapitalLettersTo()))
                            && (!filter.isContainsSmallLetters
                                || compare(filter.getCountSmallLettersFrom(), p.getCountSmallLetters(),
                                    filter.getCountSmallLettersTo()))
                            && (!filter.isContainsDigits
                                || compare(filter.getCountDigitsFrom(), p.getCountDigits(), filter.getCountDigitsTo()))
                            && (!filter.isContainsSpecialSign
                                || compare(filter.getCountSpecialSignFrom(), p.getCountSpecialSign(),
                                    filter.getCountSpecialSignTo()))
                            && (!filter.isContainsSpace || p.getPassword().matches(".*\\s+.*")))
                    .collect(Collectors.toList());
        } else {
            return passwords.stream()
                    .filter(p -> (p.getLength() >= filter.getLengthFrom() && p.getLength() <= filter.getLengthTo())
                            || (!filter.isContainsCapitalLetters || p.getPassword().matches(".*[A-Z].*"))
                            || (!filter.isContainsSmallLetters || p.getPassword().matches(".*[a-z].*"))
                            || (!filter.isContainsDigits || p.getPassword().matches(".*\\d.*"))
                            || (!filter.isContainsSpecialSign || p.getPassword().matches("[^\\p{P}\\p{S}]+"))
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

    public void compileDictionary(Filter filter) {

        extractGooglePasswords();
        extractChromiumPasswords();
        extractOperaPasswords();
        extractAtomPasswords();
        for (Password pas : filterPasswords(filter)) {
            System.out.println(pas.getPassword());
        }

    }

}