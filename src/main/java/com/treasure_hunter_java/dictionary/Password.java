package com.treasure_hunter_java.dictionary;

/**
 * Представляет пароль с информацией о его свойствах и использовании.
 */
public class Password {

    private final int length;                // Длина пароля
    private final int countDigits;           // Количество цифр в пароле
    private final int countSpecialSign;      // Количество специальных символов в пароле
    private final int countCapitalLetters;   // Количество заглавных букв в пароле
    private final int countSmallLetters;     // Количество строчных букв в пароле
    private int countUsed = 0;               // Количество использований пароля
    private final String password;           // Значение пароля

    /**
     * Возвращает значение пароля.
     *
     * @return Значение пароля.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Увеличивает счетчик использований пароля на 1.
     */
    public void increaseCountUsed() {
        this.countUsed += 1;
    }

    /**
     * Возвращает количество заглавных букв в пароле.
     *
     * @return Количество заглавных букв.
     */
    public int getCountCapitalLetters() {
        return countCapitalLetters;
    }

    /**
     * Возвращает количество строчных букв в пароле.
     *
     * @return Количество строчных букв.
     */
    public int getCountSmallLetters() {
        return countSmallLetters;
    }

    /**
     * Возвращает длину пароля.
     *
     * @return Длина пароля.
     */
    public int getLength() {
        return length;
    }

    /**
     * Возвращает количество специальных символов в пароле.
     *
     * @return Количество специальных символов.
     */
    public int getCountSpecialSign() {
        return countSpecialSign;
    }

    /**
     * Возвращает количество цифр в пароле.
     *
     * @return Количество цифр.
     */
    public int getCountDigits() {
        return countDigits;
    }

    /**
     * Возвращает количество использований пароля.
     *
     * @return Количество использований пароля.
     */
    public int getUsageCount() {
        return countUsed;
    }

    /**
     * Создает новый экземпляр класса Password с заданным значением пароля.
     *
     * @param password Значение пароля.
     */
    public Password(String password) {
        this.password = password;
        this.length = password.length();
        this.countCapitalLetters = password.replaceAll("[^A-Z]", "").length();
        this.countSmallLetters = password.replaceAll("[^a-z]", "").length();
        this.countDigits = password.replaceAll("\\D", "").length();
        this.countSpecialSign = password.replaceAll("[^\\p{P}\\p{S}]+", "").length();
    }

}