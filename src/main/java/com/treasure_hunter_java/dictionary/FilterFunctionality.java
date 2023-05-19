package com.treasure_hunter_java.dictionary;

import java.util.List;
import java.util.regex.Pattern;

public class FilterFunctionality {

    private final List<Password> passwords;

    public FilterFunctionality(List<Password> passwords) {
        this.passwords = passwords;
    }

    private List<Password> strictFilter(Filter filter) {
        return passwords.stream()
                .filter(p -> compareLength(filter, p))
                .filter(p -> filterContainsCapitalLetters(filter, p))
                .filter(p -> filterContainsSmallLetters(filter, p))
                .filter(p -> filterContainsDigits(filter, p))
                .filter(p -> filterContainsSpecialSign(filter, p))
                .filter(p -> filterContainsSpace(filter, p))
                .filter(p -> filterMatchesMask(filter, p))
                .sorted((p1, p2) -> Integer.compare(p2.getUsageCount(), p1.getUsageCount()))
                .toList();
    }

    private List<Password> noneStrictFilter(Filter filter) {
        return passwords.stream()
                .filter(p -> compareLength(filter, p)
                        && (filterContainsCapitalLetters(filter, p)
                        || filterContainsSmallLetters(filter, p)
                        || filterContainsDigits(filter, p)
                        || filterContainsSpecialSign(filter, p)
                        || filterContainsSpace(filter, p)))
                .filter(p -> filterMatchesMask(filter, p))
                .sorted((p1, p2) -> Integer.compare(p2.getUsageCount(), p1.getUsageCount()))
                .toList();
    }

    List<Password> filterPasswords(Filter filter) {
        if (filter.isStrict()){ return strictFilter(filter); }
        else { return noneStrictFilter(filter); }
    }

    private boolean compareLength(Filter filter, Password password) {
        return compare(filter.getMinLength(), password.getLength(), filter.getMaxLength());
    }

    private boolean filterContainsCapitalLetters(Filter filter, Password password) {
        return !filter.isContainsCapitalLetters()
                || compare(filter.getMinCountCapitalLetters(), password.getCountCapitalLetters(),
                filter.getMaxCountCapitalLetters());
    }

    private boolean filterContainsSmallLetters(Filter filter, Password password) {
        return !filter.isContainsSmallLetters()
                || compare(filter.getMinCountSmallLetters(), password.getCountSmallLetters(),
                filter.getMaxCountSmallLetters());
    }

    private boolean filterContainsDigits(Filter filter, Password password) {
        return !filter.isContainsDigits()
                || compare(filter.getMinCountDigits(), password.getCountDigits(), filter.getMaxCountDigits());
    }

    private boolean filterContainsSpecialSign(Filter filter, Password password) {
        return !filter.isContainsSpecialSign()
                || compare(filter.getMinCountSpecialSign(), password.getCountSpecialSign(),
                filter.getMaxCountSpecialSign());
    }

    private boolean filterContainsSpace(Filter filter, Password password) {
        return !filter.isContainsSpace() || password.getPassword().matches(".*\\s+.*");
    }

    private boolean filterMatchesMask(Filter filter, Password password) {
        return filter.getMask().isEmpty() || Pattern.compile(filter.getMask()).matcher(password.getPassword()).find();
    }

    private boolean compare(int minValue, int value, int maxValue) {
        return minValue <= value && value <= maxValue;
    }
}