package com.treasure_hunter_java.dictionary;

public class Filter {

    int length;

    boolean isContainsDigits;

    boolean isContainsCapitalLetters;

    boolean isContainsSmallLetters;

    boolean isContainsSpecialSign;

    boolean isContainsSpace;

    Filter(boolean isContainsCapitalLetters, boolean isContainsSmallLetters, boolean isContainsDigits,
           boolean isContainsSpecialSign, boolean isContainsSpace, int length) {

        this.isContainsCapitalLetters = isContainsCapitalLetters;
        this.isContainsSmallLetters = isContainsSmallLetters;
        this.isContainsDigits = isContainsDigits;
        this.isContainsSpecialSign = isContainsSpecialSign;
        this.isContainsSpace = isContainsSpace;
        this.length = length;

    }

    public int getLength() {
        return length;
    }

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
}
