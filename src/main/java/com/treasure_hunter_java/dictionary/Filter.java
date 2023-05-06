package com.treasure_hunter_java.dictionary;

public class Filter {

    int lengthFrom;

    int lengthTo;

    String password;

    boolean isContainsDigits;

    boolean isContainsCapitalLetters;

    boolean isContainsSmallLetters;

    boolean isContainsSpecialSign;

    boolean isContainsSpace;

    public Filter(boolean isContainsCapitalLetters, boolean isContainsSmallLetters, boolean isContainsDigits,
                  boolean isContainsSpecialSign, boolean isContainsSpace, Integer lengthFrom, int lengthTo) {

        this.isContainsCapitalLetters = isContainsCapitalLetters;
        this.isContainsSmallLetters = isContainsSmallLetters;
        this.isContainsDigits = isContainsDigits;
        this.isContainsSpecialSign = isContainsSpecialSign;
        this.isContainsSpace = isContainsSpace;
        this.lengthFrom = lengthFrom;
        this.lengthTo = lengthTo;

    }

    public int getLengthFrom() {
        return lengthFrom;
    }

    public int getLengthTo() {return lengthTo;}

    public boolean isContainsDigits() {
        return isContainsDigits;
    }

    public boolean isContainsCapitalLetters() {
        return isContainsCapitalLetters;
    }

    public boolean isContainsSmallLetters() {
        return isContainsSmallLetters;
    }

    public boolean isContainsSpecialSign() {
        return isContainsSpecialSign;
    }

    public boolean isContainsSpace() {
        return isContainsSpace;
    }

    public String getPassword(){
        return password;
    }
}
