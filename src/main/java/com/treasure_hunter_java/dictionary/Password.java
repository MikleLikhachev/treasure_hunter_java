package com.treasure_hunter_java.dictionary;

public class Password {

    int length;

    String password;

    boolean isContainsDigits;

    boolean isContainsCapitalLetters;

    boolean isContainsSmallLetters;

    boolean isContainsSpecialSign;

    boolean isContainsSpace;

    public boolean getContainsDigits(){

        return password.matches("^\\d+$");
    }

    public boolean getContainsCapitalLetters(){

        return password.matches(".*[A-Z].*");
    }

    public boolean getContainsSmallLetters() {

        return password.matches(".*[a-z].*");
    }

    Password(String password) {
        this.password = password;
    }

}
