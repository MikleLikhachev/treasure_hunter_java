package com.treasure_hunter_java.dictionary;

import com.treasure_hunter_java.Main;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class GenerateDictionary {

    private final ArrayList<Password> passwords = new ArrayList<>();

    private final boolean strictFilter;
    private final boolean isContainsCapitalLetters;
    private final boolean isContainsSmallLetters;
    private final boolean isContainsDigits;
    private final boolean isContainsSpecialSign;
    private final boolean isContainsSpace;
    private final int lengthFrom;
    private final int lengthTo;
    private final boolean googleChromeIsSelected;
    private final boolean operaIsSelected;
    private final boolean chromiumIsSelected;
    private final boolean atomIsSelected;

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

    private Filter generateFilter() {

        return new Filter(isContainsCapitalLetters, isContainsSmallLetters, isContainsDigits,
                isContainsSpecialSign, isContainsSpace, lengthFrom, lengthTo);
    }

    private List<Password> filterPasswords(Filter filter) {

        if (strictFilter) {
            return passwords.stream()
                    .filter(p -> (p.getLength() >= filter.getLengthFrom() && p.getLength() <= filter.getLengthTo())
                            && (!filter.isContainsCapitalLetters || p.getPassword().matches(".*[A-Z].*"))
                            && (!filter.isContainsSmallLetters || p.getPassword().matches(".*[a-z].*"))
                            && (!filter.isContainsDigits || p.getPassword().matches(".*\\d.*"))
                            && (!filter.isContainsSpecialSign || p.getPassword().matches(".*[^\\w\\s].*"))
                            && (!filter.isContainsSpace || p.getPassword().matches(".*\\s+.*")))
                    .collect(Collectors.toList());
        } else {
            return passwords.stream()
                    .filter(p -> (p.getLength() >= filter.getLengthFrom() && p.getLength() <= filter.getLengthTo())
                            || (!filter.isContainsCapitalLetters || p.getPassword().matches(".*[A-Z].*"))
                            || (!filter.isContainsSmallLetters || p.getPassword().matches(".*[a-z].*"))
                            || (!filter.isContainsDigits || p.getPassword().matches(".*\\d.*"))
                            || (!filter.isContainsSpecialSign || p.getPassword().matches(".*[^\\w\\s].*"))
                            || (!filter.isContainsSpace || p.getPassword().matches(".*\\s+.*")))
                    .collect(Collectors.toList());
        }
    }

    public GenerateDictionary(boolean strictFilter, boolean isContainsCapitalLetters, boolean isContainsSmallLetters, boolean isContainsDigits,
                              boolean isContainsSpecialSign, boolean isContainsSpace, Integer lengthFrom, int lengthTo,
                              boolean googleChrome, boolean opera, boolean chromium, boolean atom) {

        this.strictFilter = strictFilter;
        this.isContainsCapitalLetters = isContainsCapitalLetters;
        this.isContainsSmallLetters = isContainsSmallLetters;
        this.isContainsDigits = isContainsDigits;
        this.isContainsSpecialSign = isContainsSpecialSign;
        this.isContainsSpace = isContainsSpace;
        this.lengthFrom = lengthFrom;
        this.lengthTo = lengthTo;
        this.googleChromeIsSelected = googleChrome;
        this.operaIsSelected = opera;
        this.chromiumIsSelected = chromium;
        this.atomIsSelected = atom;

    }

    public void compileDictionary() {

        extractGooglePasswords();
        extractChromiumPasswords();
        extractOperaPasswords();
        extractAtomPasswords();
        for (Password pas : filterPasswords(generateFilter())) {
            System.out.println(pas.getPassword());
        }

    }

}