package com.treasure_hunter_java.dictionary;

import java.util.regex.Pattern;

public class Filter {

    int minLength;

    int maxLength;

    int minCountCapitalLetters;

    int maxCountCapitalLetters;

    int minCountSmallLetters;

    int maxCountSmallLetters;

    String password;

    String mask;

    boolean isContainsDigits;

    int minCountDigits;
    int maxCountDigits;

    boolean isContainsCapitalLetters;

    boolean isContainsSmallLetters;

    boolean isContainsSpecialSign;

    int minCountSpecialSign;
    int maxCountSpecialSign;


    boolean isContainsSpace;

    public Filter() {}

    public Filter(boolean isContainsCapitalLetters, boolean isContainsSmallLetters, boolean isContainsDigits,
                  boolean isContainsSpecialSign, boolean isContainsSpace, int minLength, int maxLength,
                  int minCountDigits, int maxCountDigits, int minCountSpecialSign, int maxCountSpecialSign,
                  int minCountCapitalLetters, int maxCountCapitalLetters, int minCountSmallLetters,
                  int maxCountSmallLetters, String mask) {

        this.isContainsCapitalLetters = isContainsCapitalLetters;
        this.isContainsSmallLetters = isContainsSmallLetters;
        this.isContainsDigits = isContainsDigits;
        this.isContainsSpecialSign = isContainsSpecialSign;
        this.isContainsSpace = isContainsSpace;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.minCountDigits = minCountDigits;
        this.maxCountDigits = maxCountDigits;
        this.minCountSpecialSign = minCountSpecialSign;
        this.maxCountSpecialSign = maxCountSpecialSign;
        this.minCountCapitalLetters = minCountCapitalLetters;
        this.maxCountCapitalLetters = maxCountCapitalLetters;
        this.minCountSmallLetters = minCountSmallLetters;
        this.maxCountSmallLetters = maxCountSmallLetters;
        this.mask = mask;
        System.out.println(getMask());
    }

    public int getMinLength() {
        return minLength;
    }

    public int getMaxLength() {return maxLength;}

    public int getMinCountCapitalLetters() {
        return minCountCapitalLetters;
    }

    public int getMaxCountCapitalLetters() {
        return maxCountCapitalLetters;
    }

    public int getMinCountSmallLetters() {
        return minCountSmallLetters;
    }

    public int getMaxCountSmallLetters() {
        return maxCountSmallLetters;
    }

    public int getMinCountDigits() {
        return minCountDigits;
    }

    public int getMaxCountDigits() {
        return maxCountDigits;
    }

    public int getMinCountSpecialSign() {
        return minCountSpecialSign;
    }

    public int getMaxCountSpecialSign() {
        return maxCountSpecialSign;
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

    public String getMask() {
        return mask.replace("*", ".");

    }
}
