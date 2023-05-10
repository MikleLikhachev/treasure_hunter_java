package com.treasure_hunter_java.dictionary;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Password {

    private final int length;

    private final int countDigits;

    private final int countSpecialSign;

    private final int countCapitalLetters;

    private final int countSmallLetters;

    private int countUsed = 1;

    private final String password;

    public String getPassword(){
        return password;
    }

    public void increaseCountUsed(){
        this.countUsed += 1;
    }

    public int getCountCapitalLetters() {return countCapitalLetters;}

    public int getCountSmallLetters() {return countSmallLetters;}

    public int getLength() {
        return length;
    }

    public int getCountSpecialSign() {return countSpecialSign;}

    public int getCountDigits() {return countDigits;}

    public int getCountUsed() {return countUsed;}

    Password(String password) {
        this.password = password;
        this.length = password.length();
        this.countCapitalLetters = password.replaceAll("[^A-Z]", "").length();
        this.countSmallLetters = password.replaceAll("[^a-z]", "").length();
        System.out.println(password + " capital: " + countCapitalLetters + " small: " + countSmallLetters);
        this.countDigits = password.replaceAll("\\D", "").length();
        this.countSpecialSign = password.replaceAll("[^\\p{P}\\p{S}]+", "").length();
    }

}
