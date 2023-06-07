package com.treasure_hunter_java.controllers;

import com.treasure_hunter_java.decrypt.ExtractFunctionality;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class MainController extends Controller {

    @FXML
    private CheckBox passwordsGoogle;

    @FXML
    private CheckBox cookiesGoogle;

    @FXML
    private CheckBox historyGoogle;

    @FXML
    private CheckBox passwordsAtom;

    @FXML
    private CheckBox cookiesAtom;

    @FXML
    private CheckBox historyAtom;

    @FXML
    private CheckBox passwordsOpera;

    @FXML
    private CheckBox cookiesOpera;

    @FXML
    private CheckBox historyOpera;

    @FXML
    private CheckBox passwordsChromium;

    @FXML
    private CheckBox cookiesChromium;

    @FXML
    private CheckBox historyChromium;

    @Override
    @FXML
    protected void onSearchPasswordsButtonClick() throws IOException {
        super.onSearchPasswordsButtonClick();
    }

    @Override
    @FXML
    protected void onGenerateDictionaryButtonClick() throws IOException {
        super.onGenerateDictionaryButtonClick();
    }

    @Override
    @FXML
    protected void onReportButtonClick() throws IOException {
        super.onReportButtonClick();
    }

    @Override
    @FXML
    protected void onZipButtonClick() throws IOException {
        super.onZipButtonClick();
    }

    @Override
    @FXML
    protected void onTelegramButtonClick() throws IOException {
        super.onTelegramButtonClick();
    }

    @Override
    @FXML
    protected void onSelectDirectoryButtonClick() {
        super.onSelectDirectoryButtonClick();
    }

    @FXML
    protected void onStartButtonClick() throws Exception {

        ExtractFunctionality extractFunctionality = new ExtractFunctionality();
        extractFunctionality.getGoogleData(passwordsGoogle, cookiesGoogle, historyGoogle);
        extractFunctionality.getAtomData(passwordsAtom, cookiesAtom, historyAtom);
        extractFunctionality.getOperaData(passwordsOpera, cookiesOpera, historyOpera);
        extractFunctionality.getChromiumData(passwordsChromium, cookiesChromium, historyChromium);
    }
}