package com.treasure_hunter_java.browsers;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс Chromium расширяет абстрактный класс Browser и представляет функциональность браузера Chromium.
 */
public class Chromium extends Browser {

    // Путь к директории браузера Chromium
    Path dirName = Path.of("C:/Users/Mikle/AppData/Local/Chromium/User Data/");

    // Путь к файлу Local State
    Path localStatePath = Path.of("C:/Users/Mikle/AppData/Local/Chromium/User Data/Local State");

    // Список профилей браузера Chromium
    List<Path> profiles = new ArrayList<>();

    /**
     * Собирает профили браузера Chromium.
     * Вызывает метод collectProfiles() родительского класса Browser,
     * чтобы собрать профили на основе указанной директории.
     */
    public void collectProfiles() {
        super.collectProfiles(dirName);
        profiles = super.getProfiles();
    }

    /**
     * Возвращает список профилей браузера Chromium.
     *
     * @return Список профилей браузера Chromium
     */
    @Override
    public List<Path> getProfiles() {
        return profiles;
    }

    /**
     * Возвращает путь к файлу Local State браузера Chromium.
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
