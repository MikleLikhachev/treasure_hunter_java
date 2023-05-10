package com.treasure_hunter_java.dictionary;

public class Filter {

    int lengthFrom;

    int lengthTo;

    int countCapitalLettersFrom;

    int countCapitalLettersTo;

    int countSmallLettersFrom;

    int countSmallLettersTo;

    String password;

    boolean isContainsDigits;

    int countDigitsFrom;
    int countDigitsTo;

    boolean isContainsCapitalLetters;

    boolean isContainsSmallLetters;

    boolean isContainsSpecialSign;

    int countSpecialSignFrom;
    int countSpecialSignTo;


    boolean isContainsSpace;

    public Filter(boolean isContainsCapitalLetters, boolean isContainsSmallLetters, boolean isContainsDigits,
                  boolean isContainsSpecialSign, boolean isContainsSpace, int lengthFrom, int lengthTo,
                  int countDigitsFrom, int countDigitsTo, int countSpecialSignFrom, int countSpecialSignTo,
                  int countCapitalLettersFrom, int countCapitalLettersTo, int countSmallLettersFrom,
                  int countSmallLettersTo) {

        this.isContainsCapitalLetters = isContainsCapitalLetters;
        this.isContainsSmallLetters = isContainsSmallLetters;
        this.isContainsDigits = isContainsDigits;
        this.isContainsSpecialSign = isContainsSpecialSign;
        this.isContainsSpace = isContainsSpace;
        this.lengthFrom = lengthFrom;
        this.lengthTo = lengthTo;
        this.countDigitsFrom = countDigitsFrom;
        this.countDigitsTo = countDigitsTo;
        this.countSpecialSignFrom = countSpecialSignFrom;
        this.countSpecialSignTo = countSpecialSignTo;
        this.countCapitalLettersFrom = countCapitalLettersFrom;
        this.countCapitalLettersTo = countCapitalLettersTo;
        this.countSmallLettersFrom = countSmallLettersFrom;
        this.countSmallLettersTo = countSmallLettersTo;
        System.out.println(countCapitalLettersFrom + " " + countCapitalLettersTo);

    }

    public int getLengthFrom() {
        return lengthFrom;
    }

    public int getLengthTo() {return lengthTo;}

    public int getCountCapitalLettersFrom() {
        return countCapitalLettersFrom;
    }

    public int getCountCapitalLettersTo() {
        return countCapitalLettersTo;
    }

    public int getCountSmallLettersFrom() {
        return countSmallLettersFrom;
    }

    public int getCountSmallLettersTo() {
        return countSmallLettersTo;
    }

    public int getCountDigitsFrom() {
        return countDigitsFrom;
    }

    public int getCountDigitsTo() {
        return countDigitsTo;
    }

    public int getCountSpecialSignFrom() {
        return countSpecialSignFrom;
    }

    public int getCountSpecialSignTo() {
        return countSpecialSignTo;
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

    public String getPassword(){
        return password;
    }
}
