package com.treasure_hunter_java.report;

/**
 * Строитель для создания экземпляра класса GenerateReport.
 * Используется для настройки параметров генерации отчета.
 */
public class GenerateReportBuilder {
    int groupSymbolLength;
    boolean totalCountPasswords;
    boolean uniqueCountPasswords;
    boolean passwordMaxLengthIsSelected;
    boolean passwordAverageLengthIsSelected;
    boolean passwordMinLengthIsSelected;
    boolean mostPopularGroupSymbols;
    int topPopularGroupSymbolCount;
    boolean mostPopularSymbolIsSelected;
    boolean topPopularSymbolIsSelected;
    int topPopularSymbolCount;

    /**
     * Конструктор строителя генерации отчета.
     *
     * @param groupSymbolLength длина группы символов для анализа.
     */
    public GenerateReportBuilder(int groupSymbolLength) {
        this.groupSymbolLength = groupSymbolLength;
    }

    /**
     * Устанавливает параметр для вывода общего количества найденных паролей в отчете.
     *
     * @param totalCountPasswords флаг, указывающий на необходимость вывода общего количества найденных паролей.
     * @return текущий экземпляр GenerateReportBuilder для дальнейшей настройки.
     */
    public GenerateReportBuilder totalCountPasswords(boolean totalCountPasswords) {
        this.totalCountPasswords = totalCountPasswords;
        return this;
    }

    /**
     * Устанавливает параметр для вывода количества уникальных паролей в отчете.
     *
     * @param uniqueCountPasswords флаг, указывающий на необходимость вывода количества уникальных паролей.
     * @return текущий экземпляр GenerateReportBuilder для дальнейшей настройки.
     */
    public GenerateReportBuilder uniqueCountPasswords(boolean uniqueCountPasswords) {
        this.uniqueCountPasswords = uniqueCountPasswords;
        return this;
    }

    /**
     * Устанавливает параметр для вывода максимальной длины найденного пароля в отчете.
     *
     * @param passwordMaxLengthIsSelected флаг, указывающий на необходимость вывода максимальной длины пароля.
     * @return текущий экземпляр GenerateReportBuilder для дальнейшей настройки.
     */
    public GenerateReportBuilder passwordMaxLengthIsSelected(boolean passwordMaxLengthIsSelected) {
        this.passwordMaxLengthIsSelected = passwordMaxLengthIsSelected;
        return this;
    }

    /**
     * Устанавливает параметр для вывода средней длины паролей в отчете.
     *
     * @param passwordAverageLengthIsSelected флаг, указывающий на необходимость вывода средней длины паролей.
     * @return текущий экземпляр GenerateReportBuilder для дальнейшей настройки.
     */
    public GenerateReportBuilder passwordAverageLengthIsSelected(boolean passwordAverageLengthIsSelected) {
        this.passwordAverageLengthIsSelected = passwordAverageLengthIsSelected;
        return this;
    }

    /**
     * Устанавливает параметр для вывода минимальной длины найденного пароля в отчете.
     *
     * @param passwordMinLengthIsSelected флаг, указывающий на необходимость вывода минимальной длины пароля.
     * @return текущий экземпляр GenerateReportBuilder для дальнейшей настройки.
     */
    public GenerateReportBuilder passwordMinLengthIsSelected(boolean passwordMinLengthIsSelected) {
        this.passwordMinLengthIsSelected = passwordMinLengthIsSelected;
        return this;
    }

    /**
     * Устанавливает параметр для вывода информации о самой популярной группе символов в отчете.
     *
     * @param mostPopularGroupSymbols флаг, указывающий на необходимость вывода информации о популярных группах символов.
     * @return текущий экземпляр GenerateReportBuilder для дальнейшей настройки.
     */
    public GenerateReportBuilder mostPopularGroupSymbols(boolean mostPopularGroupSymbols) {
        this.mostPopularGroupSymbols = mostPopularGroupSymbols;
        return this;
    }

    /**
     * Устанавливает количество популярных групп символов для вывода в отчете.
     *
     * @param topPopularGroupSymbolCount количество популярных групп символов для вывода.
     * @return текущий экземпляр GenerateReportBuilder для дальнейшей настройки.
     */
    public GenerateReportBuilder topPopularGroupSymbolCount(int topPopularGroupSymbolCount) {
        this.topPopularGroupSymbolCount = topPopularGroupSymbolCount;
        return this;
    }

    /**
     * Устанавливает параметр для вывода информации о самом популярном символе в отчете.
     *
     * @param mostPopularSymbolIsSelected флаг, указывающий на необходимость вывода информации о популярных символах.
     * @return текущий экземпляр GenerateReportBuilder для дальнейшей настройки.
     */
    public GenerateReportBuilder mostPopularSymbolIsSelected(boolean mostPopularSymbolIsSelected) {
        this.mostPopularSymbolIsSelected = mostPopularSymbolIsSelected;
        return this;
    }

    /**
     * Устанавливает параметр для вывода топ-популярных символов в отчете.
     *
     * @param topPopularSymbolIsSelected флаг, указывающий на необходимость вывода топ-популярных символов.
     * @return текущий экземпляр GenerateReportBuilder для дальнейшей настройки.
     */
    public GenerateReportBuilder topPopularSymbolIsSelected(boolean topPopularSymbolIsSelected) {
        this.topPopularSymbolIsSelected = topPopularSymbolIsSelected;
        return this;
    }

    /**
     * Устанавливает количество топ-популярных символов для вывода в отчете.
     *
     * @param topPopularSymbolCount количество топ-популярных символов для вывода.
     * @return текущий экземпляр GenerateReportBuilder для дальнейшей настройки.
     */
    public GenerateReportBuilder topPopularSymbolCount(int topPopularSymbolCount) {
        this.topPopularSymbolCount = topPopularSymbolCount;
        return this;
    }

    /**
     * Создает и возвращает экземпляр GenerateReport с заданными параметрами.
     *
     * @return экземпляр GenerateReport с заданными параметрами.
     */
    public GenerateReport build() {
        return new GenerateReport(this);
    }
}
