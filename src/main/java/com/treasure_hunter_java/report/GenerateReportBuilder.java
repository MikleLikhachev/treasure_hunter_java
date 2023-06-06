package com.treasure_hunter_java.report;

public class GenerateReportBuilder {
    int groupSymbolLength;
    boolean totalCountPasswords;
    boolean uniqueCountPasswords;
    boolean passwordMaxLengthIsSelected;
    boolean passwordAverageLengthIsSelected;
    boolean passwordMinLengthIsSelected;
    boolean mostPopularGroupSymbols;
    int topPopularGroupSymbolCount;
    boolean topPopularSymbolIsSelected;
    int topPopularSymbolCount;

    public GenerateReportBuilder(int groupSymbolLength) {
        this.groupSymbolLength = groupSymbolLength;
    }

    public GenerateReportBuilder totalCountPasswords(boolean totalCountPasswords) {
        this.totalCountPasswords = totalCountPasswords;
        return this;
    }

    public GenerateReportBuilder uniqueCountPasswords(boolean uniqueCountPasswords) {
        this.uniqueCountPasswords = uniqueCountPasswords;
        return this;
    }

    public GenerateReportBuilder passwordMaxLengthIsSelected(boolean passwordMaxLengthIsSelected) {
        this.passwordMaxLengthIsSelected = passwordMaxLengthIsSelected;
        return this;
    }

    public GenerateReportBuilder passwordAverageLengthIsSelected(boolean passwordAverageLengthIsSelected) {
        this.passwordAverageLengthIsSelected = passwordAverageLengthIsSelected;
        return this;
    }

    public GenerateReportBuilder passwordMinLengthIsSelected(boolean passwordMinLengthIsSelected) {
        this.passwordMinLengthIsSelected = passwordMinLengthIsSelected;
        return this;
    }

    public GenerateReportBuilder mostPopularGroupSymbols(boolean mostPopularGroupSymbols) {
        this.mostPopularGroupSymbols = mostPopularGroupSymbols;
        return this;
    }

    public GenerateReportBuilder topPopularGroupSymbolCount(int topPopularGroupSymbolCount) {
        this.topPopularGroupSymbolCount = topPopularGroupSymbolCount;
        return this;
    }

    public GenerateReportBuilder topPopularSymbolIsSelected(boolean topPopularSymbolIsSelected) {
        this.topPopularSymbolIsSelected = topPopularSymbolIsSelected;
        return this;
    }

    public GenerateReportBuilder topPopularSymbolCount(int topPopularSymbolCount) {
        this.topPopularSymbolCount = topPopularSymbolCount;
        return this;
    }

    public GenerateReport build() {
        return new GenerateReport(this);
    }
}
