/**
 * Абстрактный класс Browser представляет общий функционал браузера.
 * Он определяет методы для сбора профилей и получения данных входа в систему.
 */
package com.treasure_hunter_java.browsers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class Browser {

    // Список профилей браузера
    private List<Path> profiles = new ArrayList<>();

    /**
     * Собирает профили браузера на основе указанной директории.
     *
     * @param dirName Путь к директории браузера
     */
    public void collectProfiles(Path dirName) {
        for (int i = 1; i < 10; i++) {
            Path path = Path.of(dirName + "/Profile " + i);
            if (Files.exists(path)) {
                profiles.add(path);
            }
        }

        Path path = Path.of(dirName + "/Default");
        if (Files.exists(path)) {
            profiles.add(path);
        }

        if (profiles.isEmpty()) {
            profiles.add(Path.of(dirName + ""));
        }
    }

    /**
     * Возвращает список профилей браузера.
     *
     * @return Список профилей браузера
     */
    public List<Path> getProfiles() {
        return profiles;
    }
}
