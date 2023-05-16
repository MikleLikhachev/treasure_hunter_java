package com.treasure_hunter_java.dictionary;

import java.util.regex.Pattern;

public class Filter {

    private boolean isStrict;

    private int minLength;

    private int maxLength;

    private int minCountCapitalLetters;

    private int maxCountCapitalLetters;

    private int minCountSmallLetters;

    private int maxCountSmallLetters;

    private String mask;

    private boolean isContainsDigits;

    private int minCountDigits;
    private int maxCountDigits;

    private boolean isContainsCapitalLetters;

    private boolean isContainsSmallLetters;

    private boolean isContainsSpecialSign;

    private int minCountSpecialSign;
    private int maxCountSpecialSign;


    private boolean isContainsSpace;

    public Filter() {}

    public Filter(boolean isStrict, boolean isContainsCapitalLetters, boolean isContainsSmallLetters, boolean isContainsDigits,
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
    }

    public boolean isStrict(){return isStrict;}

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

    public String getMask() {
        return mask.replace("*", ".");

    }
}
