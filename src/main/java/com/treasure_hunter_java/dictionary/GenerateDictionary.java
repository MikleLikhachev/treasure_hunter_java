package com.treasure_hunter_java.dictionary;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class GenerateDictionary {

    ArrayList<Password> passwords = new ArrayList<>();

    public void extractPassword(File file){

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

    private void descendingSort(){
        passwords.sort(Comparator.comparing(Password::getCountUsed).reversed());
    }

    private void ascendingSort(){
        passwords.sort(Comparator.comparing(Password::getCountUsed));

    }

    public void searchCapitalsLetters() {

        ArrayList<Password> capitalsLettersPasswords = new ArrayList<>();
        System.out.println(passwords);
        for (Password password : passwords) {
            if (password.getOnlyContainsCapitalLetters()){
                capitalsLettersPasswords.add(password);         // !!!!
            }
        }

        System.out.println(capitalsLettersPasswords.size());
    }

    /*public void searchOnlyCapitalsLetters() {

        ArrayList<String> onlyCapitalsLettersPasswords = new ArrayList<>();

        for (String psw : passwords.keySet()) {
            if (psw.matches("^[A-Z]+$")) {
                onlyCapitalsLettersPasswords.add(psw);
            }
        }
        System.out.println(onlyCapitalsLettersPasswords);
    }

    public void searchSmallLetters() {

        ArrayList<String> smallLettersPasswords = new ArrayList<>();

        for (String psw : passwords.keySet()) {
            if (psw.matches(".*[a-z].*")) {
                smallLettersPasswords.add(psw);
            }
        }
        System.out.println(smallLettersPasswords);
    }

    public void searchOnlySmallLetters() {

        ArrayList<String> onlySmallLettersPasswords = new ArrayList<>();

        for (String psw : passwords.keySet()) {
            if (psw.matches("^[a-z]+$")) {
                onlySmallLettersPasswords.add(psw);
            }
        }
        System.out.println(onlySmallLettersPasswords);
    }
*/

    public List<Password> filterPasswords(Filter filter, boolean strictFilter) {

        if (strictFilter) {
            return passwords.stream()
                    .filter(p -> (p.getLength() >= filter.getLengthFrom() && p.getLength() <= filter.lengthTo)
                            && (!filter.isContainsCapitalLetters || p.getPassword().matches(".*[A-Z].*"))
                            && (!filter.isContainsSmallLetters || p.getPassword().matches(".*[a-z].*"))
                            && (!filter.isContainsDigits || p.getPassword().matches(".*\\d.*"))
                            && (!filter.isContainsSpecialSign || p.getPassword().matches(".*[^\\w\\s].*"))
                            && (!filter.isContainsSpace || p.getPassword().matches(".*\\s+.*")))
                    .collect(Collectors.toList());
        } else {
            return passwords.stream()
                    .filter(p -> (p.getLength() >= filter.getLengthFrom() && p.getLength() <= filter.lengthTo)
                            || (!filter.isContainsCapitalLetters || p.getPassword().matches(".*[A-Z].*"))
                            || (!filter.isContainsSmallLetters || p.getPassword().matches(".*[a-z].*"))
                            || (!filter.isContainsDigits || p.getPassword().matches(".*\\d.*"))
                            || (!filter.isContainsSpecialSign || p.getPassword().matches(".*[^\\w\\s].*"))
                            || (!filter.isContainsSpace || p.getPassword().matches(".*\\s+.*")))
                    .collect(Collectors.toList());
        }
    }

}
