package com.treasure_hunter_java.directory;

import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;

import static com.treasure_hunter_java.controllers.Controller.showDialog;

public class Directory {

    private static Path workDirectory;

    public static void setWorkDirectory(Path workDirectory){
        Directory.workDirectory = workDirectory;
    }

    public static Path getWorkDirectory(){
        return workDirectory;
    }

    public void selectDirectory(Stage currentStage){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(currentStage);

        if (selectedDirectory != null) {
            setWorkDirectory(Path.of(selectedDirectory.getAbsolutePath()));
        } else {
            showDialog("Вы не выбрали папку!", Alert.AlertType.ERROR);
        }
    }

}