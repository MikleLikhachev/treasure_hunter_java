package com.treasure_hunter_java.dictionary;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Класс, предоставляющий функциональность фильтрации паролей с использованием заданного фильтра.
 */
public class FilterFunctionality {

    private final List<Password> passwords;

    /**
     * Конструктор класса FilterFunctionality.
     *
     * @param passwords список паролей для фильтрации.
     */
    public FilterFunctionality(List<Password> passwords) {
        this.passwords = passwords;
    }

    /**
     * Фильтрует список паролей с использованием строгого фильтра.
     *
     * @param filter фильтр для применения.
     * @return отфильтрованный список паролей.
     */
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

    /**
     * Фильтрует список паролей с использованием нестрогого фильтра.
     *
     * @param filter фильтр для применения.
     * @return отфильтрованный список паролей.
     */
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

    /**
     * Фильтрует список паролей с использованием заданного фильтра.
     *
     * @param filter фильтр для применения.
     * @return отфильтрованный список паролей.
     */
    public List<Password> filterPasswords(Filter filter) {
        if (filter.isStrict()) {
            return strictFilter(filter);
        } else {
            return noneStrictFilter(filter);
        }
    }

    /**
     * Сравнивает длину пароля с заданными ограничениями.
     *
     * @param filter   фильтр для применения.
     * @param password пароль для сравнения.
     * @return true, если длина пароля удовлетворяет ограничениям; false в противном случае.
     */
    private boolean compareLength(Filter filter, Password password) {
        return compare(filter.getMinLength(), password.getLength(), filter.getMaxLength());
    }

    /**
     * Проверяет, содержит ли пароль заданное количество заглавных букв.
     *
     * @param filter   фильтр для применения.
     * @param password пароль для проверки.
     * @return true, если пароль содержит заданное количество заглавных букв
     * или фильтр не требует проверки заглавных букв; false в противном случае.
     */
    private boolean filterContainsCapitalLetters(Filter filter, Password password) {
        return !filter.isContainsCapitalLetters()
                || compare(filter.getMinCountCapitalLetters(), password.getCountCapitalLetters(),
                filter.getMaxCountCapitalLetters());
    }

    /**
     * Проверяет, содержит ли пароль заданное количество строчных букв.
     *
     * @param filter   фильтр для применения.
     * @param password пароль для проверки.
     * @return true, если пароль содержит заданное количество строчных букв
     * или фильтр не требует проверки строчных букв; false в противном случае.
     */
    private boolean filterContainsSmallLetters(Filter filter, Password password) {
        return !filter.isContainsSmallLetters()
                || compare(filter.getMinCountSmallLetters(), password.getCountSmallLetters(),
                filter.getMaxCountSmallLetters());
    }

    /**
     * Проверяет, содержит ли пароль заданное количество цифр.
     *
     * @param filter   фильтр для применения.
     * @param password пароль для проверки.
     * @return true, если пароль содержит заданное количество цифр или фильтр не требует проверки цифр; false в противном случае.
     */
    private boolean filterContainsDigits(Filter filter, Password password) {
        return !filter.isContainsDigits()
                || compare(filter.getMinCountDigits(), password.getCountDigits(), filter.getMaxCountDigits());
    }

    /**
     * Проверяет, содержит ли пароль заданное количество специальных символов.
     *
     * @param filter   фильтр для применения.
     * @param password пароль для проверки.
     * @return true, если пароль содержит заданное количество специальных символов или фильтр не требует проверки специальных символов; false в противном случае.
     */
    private boolean filterContainsSpecialSign(Filter filter, Password password) {
        return !filter.isContainsSpecialSign()
                || compare(filter.getMinCountSpecialSign(), password.getCountSpecialSign(),
                filter.getMaxCountSpecialSign());
    }

    /**
     * Проверяет, содержит ли пароль пробел.
     *
     * @param filter   фильтр для применения.
     * @param password пароль для проверки.
     * @return true, если пароль содержит пробел или фильтр не требует проверки пробела; false в противном случае.
     */
    private boolean filterContainsSpace(Filter filter, Password password) {
        return !filter.isContainsSpace() || password.getPassword().matches(".*\\s+.*");
    }

    /**
     * Проверяет, соответствует ли пароль заданной маске.
     *
     * @param filter   фильтр для применения.
     * @param password пароль для проверки.
     * @return true, если пароль соответствует заданной маске или фильтр не требует проверки маски; false в противном случае.
     */
    private boolean filterMatchesMask(Filter filter, Password password) {
        return filter.getMask().isEmpty() || Pattern.compile(filter.getMask()).matcher(password.getPassword()).matches();
    }

    /**
     * Сравнивает значение с минимальным и максимальным значениями.
     *
     * @param minValue минимальное значение.
     * @param value    значение для сравнения.
     * @param maxValue максимальное значение.
     * @return true, если значение находится в диапазоне от minValue до maxValue включительно; false в противном случае.
     */
    private boolean compare(int minValue, int value, int maxValue) {
        return minValue <= value && value <= maxValue;
    }
}