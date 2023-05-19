package com.treasure_hunter_java.controllers;

import com.treasure_hunter_java.Main;
import com.treasure_hunter_java.zip.DirectoryArchiver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.File;
import java.io.IOException;

public class ZipController extends Controller{

    DirectoryArchiver directoryArchiver = new DirectoryArchiver();

    @FXML
    private Button searchPasswordsButton;

    @FXML
    private Button generateDictionaryButton;

    @FXML
    private TextField nameArchive;

    @FXML
    private CheckBox installPassword;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button reportButton;

    @FXML
    private Button zipButton;

    @FXML
    private Button telegramButton;

    @FXML
    private Button selectDirectoryButton;

    @FXML
    private Button start;

    @FXML
    public void onStartButtonClick(ActionEvent actionEvent) throws IOException {

        if (installPassword.isSelected()){
            directoryArchiver.setPassword(passwordField.getText());
        }
        if (!nameArchive.getText().isEmpty()) {
            directoryArchiver.setName(nameArchive.getText());
        }
        directoryArchiver.archiveDirectory();
    }

    @FXML
    protected void onSearchPasswordsButtonClick() throws IOException {
        super.onSearchPasswordsButtonClick();
    }

    @FXML
    protected void GenerateDictionaryButtonClick() throws IOException {
        super.onGenerateDictionaryButtonClick();
    }

    @FXML
    protected void onReportButtonClick() throws Exception {
        super.onReportButtonClick();
    }

    @FXML
    protected void onZipButtonClick() throws IOException {

    }

    @FXML
    protected void onTelegramButtonClick() throws IOException {
        super.onTelegramButtonClick();
    }

    @FXML
    protected void onSelectDirectoryButtonClick() {
        super.onSelectDirectoryButtonClick();
    }

}

