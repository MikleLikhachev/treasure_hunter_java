package com.treasure_hunter_java.dictionary;

public class FilterBuilder {
    boolean isStrict;
    boolean isContainsCapitalLetters;
    boolean isContainsSmallLetters;
    boolean isContainsDigits;
    boolean isContainsSpecialSign;
    boolean isContainsSpace;
    int minLength;
    int maxLength;
    int minCountDigits;
    int maxCountDigits;
    int minCountSpecialSign;
    int maxCountSpecialSign;
    int minCountCapitalLetters;
    int maxCountCapitalLetters;
    int minCountSmallLetters;
    int maxCountSmallLetters;
    String mask;

    public FilterBuilder strict(boolean isStrict) {
        this.isStrict = isStrict;
        return this;
    }

    public FilterBuilder containsCapitalLetters(boolean isContainsCapitalLetters) {
        this.isContainsCapitalLetters = isContainsCapitalLetters;
        return this;
    }

    public FilterBuilder containsSmallLetters(boolean isContainsSmallLetters) {
        this.isContainsSmallLetters = isContainsSmallLetters;
        return this;
    }

    public FilterBuilder containsDigits(boolean isContainsDigits) {
        this.isContainsDigits = isContainsDigits;
        return this;
    }

    public FilterBuilder containsSpecialSign(boolean isContainsSpecialSign) {
        this.isContainsSpecialSign = isContainsSpecialSign;
        return this;
    }

    public FilterBuilder containsSpace(boolean isContainsSpace) {
        this.isContainsSpace = isContainsSpace;
        return this;
    }

    public FilterBuilder minLength(int minLength) {
        this.minLength = minLength;
        return this;
    }

    public FilterBuilder maxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public FilterBuilder minCountDigits(int minCountDigits) {
        this.minCountDigits = minCountDigits;
        return this;
    }

    public FilterBuilder maxCountDigits(int maxCountDigits) {
        this.maxCountDigits = maxCountDigits;
        return this;
    }

    public FilterBuilder minCountSpecialSign(int minCountSpecialSign) {
        this.minCountSpecialSign = minCountSpecialSign;
        return this;
    }

    public FilterBuilder maxCountSpecialSign(int maxCountSpecialSign) {
        this.maxCountSpecialSign = maxCountSpecialSign;
        return this;
    }

    public FilterBuilder minCountCapitalLetters(int minCountCapitalLetters) {
        this.minCountCapitalLetters = minCountCapitalLetters;
        return this;
    }

    public FilterBuilder maxCountCapitalLetters(int maxCountCapitalLetters) {
        this.maxCountCapitalLetters = maxCountCapitalLetters;
        return this;
    }

    public FilterBuilder minCountSmallLetters(int minCountSmallLetters) {
        this.minCountSmallLetters = minCountSmallLetters;
        return this;
    }

    public FilterBuilder maxCountSmallLetters(int maxCountSmallLetters) {
        this.maxCountSmallLetters = maxCountSmallLetters;
        return this;
    }

    public FilterBuilder mask(String mask) {
        this.mask = mask;
        return this;
    }

    public Filter build() {
        return new Filter(this);
    }
}
