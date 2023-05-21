package com.treasure_hunter_java.controllers;

import com.treasure_hunter_java.report.GenerateReport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class ReportController extends Controller{

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

    }

    @FXML
    protected void onZipButtonClick() throws IOException {
        super.onZipButtonClick();
    }

    @FXML
    protected void onTelegramButtonClick() throws IOException {
        super.onTelegramButtonClick();
    }

    @FXML
    protected void onSelectDirectoryButtonClick() {
        super.onSelectDirectoryButtonClick();
    }

    @FXML
    public void onStartButtonClick(ActionEvent actionEvent) throws IOException {
        GenerateReport generateReport = new GenerateReport();
        generateReport.rocket();
    }

}
