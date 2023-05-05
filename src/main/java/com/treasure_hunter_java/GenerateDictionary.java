package com.treasure_hunter_java;

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

    public void searchSmallLetter() {

        ArrayList<String> smallLetterPasswords = new ArrayList<>();

        for (String psw : passwords.keySet()) {
            if (psw.matches(".*[a-z].*")) {
                smallLetterPasswords.add(psw);
            }
        }
        System.out.println(smallLetterPasswords);
    }

}
