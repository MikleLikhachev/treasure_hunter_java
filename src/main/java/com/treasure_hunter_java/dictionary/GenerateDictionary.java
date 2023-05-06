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

    public List<Password> filterPasswords(Filter filter, boolean strictFilter) {

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

}
