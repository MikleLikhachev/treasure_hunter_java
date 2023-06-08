package com.treasure_hunter_java.dictionary;

/**
 * Строитель для создания объекта класса Filter с заданными параметрами.
 */
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

    /**
     * Устанавливает значение флага строгости фильтра.
     *
     * @param isStrict true, если фильтр должен быть строгим; false, если фильтр не должен быть строгим.
     * @return текущий объект FilterBuilder.
     */
    public FilterBuilder strict(boolean isStrict) {
        this.isStrict = isStrict;
        return this;
    }

    /**
     * Устанавливает значение флага, указывающего на наличие заглавных букв в словах, проходящих фильтр.
     *
     * @param isContainsCapitalLetters true, если слова должны содержать заглавные буквы; false, если слова не должны содержать заглавные буквы.
     * @return текущий объект FilterBuilder.
     */
    public FilterBuilder containsCapitalLetters(boolean isContainsCapitalLetters) {
        this.isContainsCapitalLetters = isContainsCapitalLetters;
        return this;
    }

    /**
     * Устанавливает значение флага, указывающего на наличие строчных букв в словах, проходящих фильтр.
     *
     * @param isContainsSmallLetters true, если слова должны содержать строчные буквы; false, если слова не должны содержать строчные буквы.
     * @return текущий объект FilterBuilder.
     */
    public FilterBuilder containsSmallLetters(boolean isContainsSmallLetters) {
        this.isContainsSmallLetters = isContainsSmallLetters;
        return this;
    }

    /**
     * Устанавливает значение флага, указывающего на наличие цифр в словах, проходящих фильтр.
     *
     * @param isContainsDigits true, если слова должны содержать цифры; false, если слова не должны содержать цифры.
     * @return текущий объект FilterBuilder.
     */
    public FilterBuilder containsDigits(boolean isContainsDigits) {
        this.isContainsDigits = isContainsDigits;
        return this;
    }

    /**
     * Устанавливает значение флага, указывающего на наличие специальных символов в словах, проходящих фильтр.
     *
     * @param isContainsSpecialSign true, если слова должны содержать специальные символы; false, если слова не должны содержать специальные символы.
     * @return текущий объект FilterBuilder.
     */
    public FilterBuilder containsSpecialSign(boolean isContainsSpecialSign) {
        this.isContainsSpecialSign = isContainsSpecialSign;
        return this;
    }

    /**
     * Устанавливает значение флага, указывающего на наличие пробелов в словах, проходящих фильтр.
     *
     * @param isContainsSpace true, если слова должны содержать пробелы; false, если слова не должны содержать пробелы.
     * @return текущий объект FilterBuilder.
     */
    public FilterBuilder containsSpace(boolean isContainsSpace) {
        this.isContainsSpace = isContainsSpace;
        return this;
    }

    /**
     * Устанавливает минимальную длину слова, проходящего фильтр.
     *
     * @param minLength минимальная длина слова.
     * @return текущий объект FilterBuilder.
     */
    public FilterBuilder minLength(int minLength) {
        this.minLength = minLength;
        return this;
    }

    /**
     * Устанавливает максимальную длину слова, проходящего фильтр.
     *
     * @param maxLength максимальная длина слова.
     * @return текущий объект FilterBuilder.
     */
    public FilterBuilder maxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    /**
     * Устанавливает минимальное количество цифр в слове, проходящем фильтр.
     *
     * @param minCountDigits минимальное количество цифр.
     * @return текущий объект FilterBuilder.
     */
    public FilterBuilder minCountDigits(int minCountDigits) {
        this.minCountDigits = minCountDigits;
        return this;
    }

    /**
     * Устанавливает максимальное количество цифр в слове, проходящем фильтр.
     *
     * @param maxCountDigits максимальное количество цифр.
     * @return текущий объект FilterBuilder.
     */
    public FilterBuilder maxCountDigits(int maxCountDigits) {
        this.maxCountDigits = maxCountDigits;
        return this;
    }

    /**
     * Устанавливает минимальное количество специальных символов в слове, проходящем фильтр.
     *
     * @param minCountSpecialSign минимальное количество специальных символов.
     * @return текущий объект FilterBuilder.
     */
    public FilterBuilder minCountSpecialSign(int minCountSpecialSign) {
        this.minCountSpecialSign = minCountSpecialSign;
        return this;
    }

    /**
     * Устанавливает максимальное количество специальных символов в слове, проходящем фильтр.
     *
     * @param maxCountSpecialSign максимальное количество специальных символов.
     * @return текущий объект FilterBuilder.
     */
    public FilterBuilder maxCountSpecialSign(int maxCountSpecialSign) {
        this.maxCountSpecialSign = maxCountSpecialSign;
        return this;
    }

    /**
     * Устанавливает минимальное количество заглавных букв в слове, проходящем фильтр.
     *
     * @param minCountCapitalLetters минимальное количество заглавных букв.
     * @return текущий объект FilterBuilder.
     */
    public FilterBuilder minCountCapitalLetters(int minCountCapitalLetters) {
        this.minCountCapitalLetters = minCountCapitalLetters;
        return this;
    }

    /**
     * Устанавливает максимальное количество заглавных букв в слове, проходящем фильтр.
     *
     * @param maxCountCapitalLetters максимальное количество заглавных букв.
     * @return текущий объект FilterBuilder.
     */
    public FilterBuilder maxCountCapitalLetters(int maxCountCapitalLetters) {
        this.maxCountCapitalLetters = maxCountCapitalLetters;
        return this;
    }

    /**
     * Устанавливает минимальное количество строчных букв в слове, проходящем фильтр.
     *
     * @param minCountSmallLetters минимальное количество строчных букв.
     * @return текущий объект FilterBuilder.
     */
    public FilterBuilder minCountSmallLetters(int minCountSmallLetters) {
        this.minCountSmallLetters = minCountSmallLetters;
        return this;
    }

    /**
     * Устанавливает максимальное количество строчных букв в слове, проходящем фильтр.
     *
     * @param maxCountSmallLetters максимальное количество строчных букв.
     * @return текущий объект FilterBuilder.
     */
    public FilterBuilder maxCountSmallLetters(int maxCountSmallLetters) {
        this.maxCountSmallLetters = maxCountSmallLetters;
        return this;
    }

    /**
     * Устанавливает маску для фильтрации слов.
     *
     * @param mask маска для фильтрации.
     * @return текущий объект FilterBuilder.
     */
    public FilterBuilder mask(String mask) {
        this.mask = mask;
        return this;
    }

    /**
     * Создает объект Filter с заданными параметрами.
     *
     * @return объект Filter.
     */
    public Filter build() {
        return new Filter(this);
    }
}