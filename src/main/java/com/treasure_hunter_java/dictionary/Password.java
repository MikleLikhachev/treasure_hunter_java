package com.treasure_hunter_java.dictionary;

public class Password {

    private int length;

    private int countUsed = 1;

    private String password;

    boolean isContainsDigits;

    boolean isContainsCapitalLetters;

    boolean isContainsSmallLetters;

    boolean isContainsSpecialSign;

    boolean isContainsSpace;

    public boolean getContainsDigits(){

        return password.matches("^\\d+$");
    }

    public boolean getOnlyContainsDigits(){
        return password.matches("^[0-9]+$");
    }

    public boolean getContainsCapitalLetters(){

        return password.matches(".*[A-Z].*");
    }

    public boolean getOnlyContainsCapitalLetters(){
        return password.matches("^[A-Z]+$");
    }

    public boolean getContainsSmallLetters() {

        return password.matches(".*[a-z].*");
    }

    public boolean getOnlyContainsSmallLetters(){

        return password.matches("^[a-z]+$");
    }

    public boolean getContainsSpecialSign(){

        return password.matches(".*[^a-zA-Z0-9].*");
    }

    public boolean getOnlyContainsSpecialSign(){
        return password.matches("^[^a-zA-Z0-9]+$");
    }

    public String getPassword(){
        return password;
    }

    public int getCountUsed(){
        return countUsed;
    }

    public void increaseCountUsed(){
        this.countUsed += 1;
    }

    public int getLength() {
        return length;
    }

    Password(String password) {
        this.password = password;
        this.length = password.length();
    }

}
