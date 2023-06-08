package com.treasure_hunter_java.dictionary;

/**
 * Класс Filter представляет фильтр для фильтрации словаря в приложении Treasure Hunter.
 * Фильтр применяется для ограничения длины слова, наличия определенных символов и других параметров.
 */
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

    /**
     * Конструктор по умолчанию.
     */
    public Filter() {
    }

    /**
     * Конструктор класса Filter.
     *
     * @param filterBuilder объект FilterBuilder для построения фильтра.
     */
    public Filter(FilterBuilder filterBuilder) {
        this.isStrict = filterBuilder.isStrict;
        this.isContainsCapitalLetters = filterBuilder.isContainsCapitalLetters;
        this.isContainsSmallLetters = filterBuilder.isContainsSmallLetters;
        this.isContainsDigits = filterBuilder.isContainsDigits;
        this.isContainsSpecialSign = filterBuilder.isContainsSpecialSign;
        this.isContainsSpace = filterBuilder.isContainsSpace;
        this.minLength = filterBuilder.minLength;
        this.maxLength = filterBuilder.maxLength;
        this.minCountDigits = filterBuilder.minCountDigits;
        this.maxCountDigits = filterBuilder.maxCountDigits;
        this.minCountSpecialSign = filterBuilder.minCountSpecialSign;
        this.maxCountSpecialSign = filterBuilder.maxCountSpecialSign;
        this.minCountCapitalLetters = filterBuilder.minCountCapitalLetters;
        this.maxCountCapitalLetters = filterBuilder.maxCountCapitalLetters;
        this.minCountSmallLetters = filterBuilder.minCountSmallLetters;
        this.maxCountSmallLetters = filterBuilder.maxCountSmallLetters;
        this.mask = filterBuilder.mask;
    }

    /**
     * Возвращает значение флага строгости фильтра.
     *
     * @return true, если фильтр строгий; false, если фильтр нестрогий.
     */
    public boolean isStrict() {
        return isStrict;
    }

    /**
     * Возвращает минимальную длину слова, которую должны удовлетворять слова, проходящие фильтр.
     *
     * @return минимальная длина слова.
     */
    public int getMinLength() {
        return minLength;
    }

    /**
     * Возвращает максимальную длину слова, которую должны удовлетворять слова, проходящие фильтр.
     *
     * @return максимальная длина слова.
     */
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * Возвращает минимальное количество заглавных букв, которое должны содержать слова, проходящие фильтр.
     *
     * @return минимальное количество заглавных букв.
     */
    public int getMinCountCapitalLetters() {
        return minCountCapitalLetters;
    }

    /**
     * Возвращает максимальное количество заглавных букв, которое должны содержать слова, проходящие фильтр.
     *
     * @return максимальное количество заглавных букв.
     */
    public int getMaxCountCapitalLetters() {
        return maxCountCapitalLetters;
    }

    /**
     * Возвращает минимальное количество строчных букв, которое должны содержать слова, проходящие фильтр.
     *
     * @return минимальное количество строчных букв.
     */
    public int getMinCountSmallLetters() {
        return minCountSmallLetters;
    }

    /**
     * Возвращает максимальное количество строчных букв, которое должны содержать слова, проходящие фильтр.
     *
     * @return максимальное количество строчных букв.
     */
    public int getMaxCountSmallLetters() {
        return maxCountSmallLetters;
    }

    /**
     * Возвращает минимальное количество цифр, которое должны содержать слова, проходящие фильтр.
     *
     * @return минимальное количество цифр.
     */
    public int getMinCountDigits() {
        return minCountDigits;
    }

    /**
     * Возвращает максимальное количество цифр, которое должны содержать слова, проходящие фильтр.
     *
     * @return максимальное количество цифр.
     */
    public int getMaxCountDigits() {
        return maxCountDigits;
    }

    /**
     * Возвращает минимальное количество специальных символов, которое должны содержать слова, проходящие фильтр.
     *
     * @return минимальное количество специальных символов.
     */
    public int getMinCountSpecialSign() {
        return minCountSpecialSign;
    }

    /**
     * Возвращает максимальное количество специальных символов, которое должны содержать слова, проходящие фильтр.
     *
     * @return максимальное количество специальных символов.
     */
    public int getMaxCountSpecialSign() {
        return maxCountSpecialSign;
    }

    /**
     * Возвращает значение флага, указывающего на наличие цифр в словах, проходящих фильтр.
     *
     * @return true, если слова должны содержать цифры; false, если слова не должны содержать цифры.
     */
    public boolean isContainsDigits() {
        return isContainsDigits;
    }

    /**
     * Возвращает значение флага, указывающего на наличие заглавных букв в словах, проходящих фильтр.
     *
     * @return true, если слова должны содержать заглавные буквы; false, если слова не должны содержать заглавные буквы.
     */
    public boolean isContainsCapitalLetters() {
        return isContainsCapitalLetters;
    }

    /**
     * Возвращает значение флага, указывающего на наличие строчных букв в словах, проходящих фильтр.
     *
     * @return true, если слова должны содержать строчные буквы; false, если слова не должны содержать строчные буквы.
     */
    public boolean isContainsSmallLetters() {
        return isContainsSmallLetters;
    }

    /**
     * Возвращает значение флага, указывающего на наличие специальных символов в словах, проходящих фильтр.
     *
     * @return true, если слова должны содержать специальные символы; false, если слова не должны содержать специальные символы.
     */
    public boolean isContainsSpecialSign() {
        return isContainsSpecialSign;
    }

    /**
     * Возвращает значение флага, указывающего на наличие пробелов в словах, проходящих фильтр.
     *
     * @return true, если слова должны содержать пробелы; false, если слова не должны содержать пробелы.
     */
    public boolean isContainsSpace() {
        return isContainsSpace;
    }

    /**
     * Возвращает маску для фильтрации слов.
     *
     * @return строка с маской для фильтрации.
     */
    public String getMask() {
        return mask;
    }
}