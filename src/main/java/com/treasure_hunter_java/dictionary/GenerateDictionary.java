package com.treasure_hunter_java.dictionary;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GenerateDictionary {

    HashMap<String, Integer> passwords = new HashMap<>();

    public void extractPassword(File file){

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\| ");
                if (!passwords.containsKey(parts[2])) {
                    passwords.put(parts[2], 1);
                } else {
                    passwords.put(parts[2], passwords.get(parts[2]) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchCapitalsLetters() {

        ArrayList<String> capitalsLettersPasswords = new ArrayList<>();

        for (String psw : passwords.keySet()) {
            if (psw.matches(".*[A-Z].*")) {
                capitalsLettersPasswords.add(psw);
            }
        }

        System.out.println(capitalsLettersPasswords);
    }

    public void searchOnlyCapitalsLetters() {

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

}
