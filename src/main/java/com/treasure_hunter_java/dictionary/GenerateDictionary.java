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
    private final int countCapitalLettersFrom;
    private final int countCapitalLettersTo;
    private final int countSmallLettersFrom;
    private final int countSmallLettersTo;
    private final int countDigitsFrom;
    private final int countDigitsTo;
    private final int countSpecialSignFrom;
    private final int countSpecialSignTo;
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
                isContainsSpecialSign, isContainsSpace, lengthFrom, lengthTo, countDigitsFrom, countDigitsTo,
                countSpecialSignFrom, countSpecialSignTo, countCapitalLettersFrom, countCapitalLettersTo,
                countSmallLettersFrom, countSmallLettersTo);
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

    public GenerateDictionary(boolean strictFilter, boolean isContainsCapitalLetters, boolean isContainsSmallLetters,
                              boolean isContainsDigits, boolean isContainsSpecialSign, boolean isContainsSpace,
                              int lengthFrom, int lengthTo, int countDigitsFrom, int countDigitsTo,
                              int countSpecialSignFrom, int countSpecialSignTo, int countCapitalLettersFrom,
                              int countCapitalLettersTo, int countSmallLettersFrom, int countSmallLettersTo,
                              boolean googleChrome, boolean opera, boolean chromium, boolean atom) {

        this.strictFilter = strictFilter;
        this.isContainsCapitalLetters = isContainsCapitalLetters;
        this.isContainsSmallLetters = isContainsSmallLetters;
        this.isContainsDigits = isContainsDigits;
        this.isContainsSpecialSign = isContainsSpecialSign;
        this.isContainsSpace = isContainsSpace;
        this.lengthFrom = lengthFrom;
        this.lengthTo = lengthTo;
        this.countCapitalLettersFrom = countCapitalLettersFrom;
        this.countCapitalLettersTo = countCapitalLettersTo;
        this.countSmallLettersFrom = countSmallLettersFrom;
        this.countSmallLettersTo = countSmallLettersTo;
        this.countDigitsFrom = countDigitsFrom;
        this.countDigitsTo = countDigitsTo;
        this.countSpecialSignFrom = countSpecialSignFrom;
        this.countSpecialSignTo = countSpecialSignTo;
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