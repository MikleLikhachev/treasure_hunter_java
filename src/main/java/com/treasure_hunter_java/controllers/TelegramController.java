package com.treasure_hunter_java.controllers;

import com.treasure_hunter_java.Main;
import com.treasure_hunter_java.dictionary.Filter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import java.io.IOException;

public class TelegramController extends Controller{

    @FXML
    private Button searchPasswordsButton;

    @FXML
    private Button generateDictionaryButton;

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
        super.onZipButtonClick();
    }

    @FXML
    protected void onTelegramButtonClick() throws IOException {}

    @FXML
    protected void onSelectDirectoryButtonClick() {
        super.onSelectDirectoryButtonClick();
    }

    @FXML
    public void onStartButtonClick(ActionEvent actionEvent) throws IOException {

    }

}
