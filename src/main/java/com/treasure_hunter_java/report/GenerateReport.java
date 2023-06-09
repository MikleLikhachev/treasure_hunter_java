package com.treasure_hunter_java.report;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.treasure_hunter_java.dictionary.Password;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.treasure_hunter_java.directory.Directory;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Math.round;

/**
 * Класс, описывающий создание отчета в формате .pdf
 */

public class GenerateReport {

    Path directory = Path.of(Directory.getWorkDirectory().toUri());
    private final ArrayList<Password> passwords = new ArrayList<>();
    private final int groupSymbolLength;
    private final boolean totalCountPasswords;
    private final boolean uniqueCountPasswords;
    private final boolean passwordMaxLengthIsSelected;
    private final boolean passwordAverageLengthIsSelected;
    private final boolean passwordMinLengthIsSelected;
    private final boolean mostPopularGroupSymbols;
    private final int topPopularGroupSymbolCount;
    private final boolean mostPopularSymbolIsSelected;
    private final boolean topPopularSymbolIsSelected;
    private final int topPopularSymbolCount;
    private static final String FONT_FILE_PATH = "src/main/resources/com/treasure_hunter_java/font/Times New Roman.ttf";
    private static final Logger logger = Logger.getLogger(GenerateReport.class.getName());


    public GenerateReport(GenerateReportBuilder builder) {
        this.groupSymbolLength = builder.groupSymbolLength;
        this.totalCountPasswords = builder.totalCountPasswords;
        this.uniqueCountPasswords = builder.uniqueCountPasswords;
        this.passwordMaxLengthIsSelected = builder.passwordMaxLengthIsSelected;
        this.passwordAverageLengthIsSelected = builder.passwordAverageLengthIsSelected;
        this.passwordMinLengthIsSelected = builder.passwordMinLengthIsSelected;
        this.mostPopularGroupSymbols = builder.mostPopularGroupSymbols;
        this.topPopularGroupSymbolCount = builder.topPopularGroupSymbolCount;
        this.mostPopularSymbolIsSelected = builder.mostPopularSymbolIsSelected;
        this.topPopularSymbolIsSelected = builder.topPopularSymbolIsSelected;
        this.topPopularSymbolCount = builder.topPopularSymbolCount;
    }

    /**
     * Метод извлечения паролей из переданного файла
     * @param file файл, хранящий в себе словарь с паролями.
     */
    private void extractPassword(File file){

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String password;
                if (line.split("\\|").length == 3) {
                    password = line.split("\\| ")[2];
                } else {
                    password = line;
                }
                Optional<Password> foundPassword = passwords.stream()
                        .filter(p -> p.getPassword().equals(password)).findFirst();

                if (foundPassword.isPresent()) {
                    Password currentPassword = foundPassword.get();
                    currentPassword.increaseCountUsed();
                } else {
                    passwords.add(new Password(password));
                    passwords.get(passwords.size() - 1).increaseCountUsed();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Обходит каждый .txt файл в директории и извлекает из него пароли.
     *
     * @throws IOException
     */
    private void collectPasswords() throws IOException{
        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) {
                if (Files.isRegularFile(filePath) && filePath.toString().endsWith("passwords.txt")) {
                    extractPassword(filePath.toFile());
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                logger.log(Level.SEVERE,"Failed to access file: {0}.", file);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Начинает процесс генерации отчета. Собирает пароли и генерирует PDF-отчет.
     * @throws IOException если произошла ошибка при сборе паролей или генерации отчета.
     */
    public void startGenerate() throws IOException {
        collectPasswords();
        generatePDF();
    }

    /**
     * Генерирует PDF-отчет на основе данных и сохраняет его в файле report.pdf.
     * Отчет включает информацию о заголовке, общей информации, количестве уникальных паролей,
     * информации о длине паролей, самом распространенном пароле, самой популярной группе символов,
     * самом популярном символе и изображении.
     */
    private void generatePDF() {
        String reportFilePath = Directory.getWorkDirectory() + "/report.pdf";
        try (PdfWriter pdfWriter = new PdfWriter(reportFilePath);
             PdfDocument pdfDoc = new PdfDocument(pdfWriter);
             Document document = new Document(pdfDoc))
        {

            PdfFont font = PdfFontFactory.createFont(FONT_FILE_PATH);

            document.setFont(font);

            printReportInfo(document, font);

            printGeneralInfo(document, passwords, totalCountPasswords);

            printUniqueCount(document, passwords, uniqueCountPasswords);

            printLengthData(document, passwords);

            printMostPopularPassword(document, passwords);

            printMostPopularSymbolGroup(document, passwords);

            printTopPopularSymbol(document, passwords);

            printTopFrequentCharacters(document, passwords);

            printImage(document);

            logger.log(Level.SEVERE, "Отчет успешно сохранен в файл: {0}.", reportFilePath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка при создании отчета", e);
        }
    }

    /**
     * Выводит информацию о заголовке отчета и общей информации в документ document с использованием указанного шрифта font.
     *
     * @param document  Объект класса Document, представляющий PDF-документ,
     *                  в который будет добавлена информация о заголовке отчета и общей информации.
     * @param font      Шрифт, используемый для отображения текста в документе.
     */
    private static void printReportInfo(Document document, PdfFont font) {
        Paragraph paragraph = new Paragraph("Отчет")
                .setFont(font)
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER)
                .setCharacterSpacing(1);
        document.add(paragraph);
        document.add(new Paragraph("Данный отчёт был создан автоматической утилитой Treasure Hunter.")
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Общая информация:").setItalic());
        String date = "Дата создания: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        document.add(new Paragraph(date).setMarginLeft(20));
        document.add(new Paragraph("Отчёт собран на основе: " + Directory.getWorkDirectory()).setMarginLeft(20));
    }

    /**
     * Выводит общую информацию об общем количестве паролей в документ document на основе указанного списка passwords.
     *
     * @param document              Объект класса Document, представляющий PDF-документ,
     *                              в который будет добавлена общая информация об анализе паролей.
     * @param passwords             Список паролей, для которых будет выведена общая информация.
     * @param totalCountPasswords   Флаг, указывающий, следует ли выводить информацию об общем количестве найденных паролей.
     */
    private static void printGeneralInfo(Document document, ArrayList<Password> passwords, boolean totalCountPasswords) {
        document.add(new Paragraph("Анализ паролей: ").setItalic());
        if (totalCountPasswords) {
            int totalCount = 0;
            for (Password pas : passwords) {
                totalCount += pas.getUsageCount();
            }
            document.add(new Paragraph("Общее количество найденных паролей: " + totalCount).setMarginLeft(20));
        }
    }

    /**
     * Выводит информацию о количестве уникальных паролей в документ document на основе указанного списка passwords.
     *
     * @param document              Объект класса Document, представляющий PDF-документ, в который будет добавлена информация о количестве уникальных паролей.
     * @param passwords             Список паролей, для которых будет выведена информация о количестве уникальных паролей.
     * @param uniqueCountPasswords  Флаг, указывающий, следует ли выводить информацию о количестве уникальных паролей.
     */
    private static void printUniqueCount(Document document, ArrayList<Password> passwords, boolean uniqueCountPasswords) {
        if (uniqueCountPasswords) {
            long uniqueCount = passwords.stream().distinct().count();
            document.add(new Paragraph("Количество уникальных паролей: " + uniqueCount).setMarginLeft(20));
        }
    }

    /**
     * Выводит информацию о длине паролей в документ document на основе указанного списка passwords.
     *
     * @param document  Объект класса Document, представляющий PDF-документ, в который будет добавлена информация о длине паролей.
     * @param passwords Список паролей, для которых будет выведена информация о длине паролей.
     */
    private void printLengthData(Document document, ArrayList<Password> passwords){
        printMaxLength(document, passwords, passwordMaxLengthIsSelected);
        printAverageLength(document, passwords, passwordAverageLengthIsSelected);
        printMinLength(document, passwords, passwordMinLengthIsSelected);
    }

    /**
     * Выводит информацию о максимальной длине найденных паролей в указанном списке passwords в документ document.
     *
     * @param document              Объект класса Document, представляющий PDF-документ,
     *                              в который будет добавлена информация о максимальной длине паролей.
     * @param passwords             Список паролей, для которых будет определена максимальная длина.
     * @param maxLengthIsSelected   Флаг, указывающий, следует ли выводить информацию о максимальной длине паролей.
     */
    private void printMaxLength(Document document, ArrayList<Password> passwords, boolean maxLengthIsSelected){
        if (maxLengthIsSelected){
            int maxLength = passwords.stream().mapToInt(Password::getLength).max().orElse(0);
            document.add(new Paragraph("Максимальная длина найденного пароля: " + maxLength).setMarginLeft(20));
        }
    }

    /**
     * Выводит информацию о средней длине паролей в указанном списке passwords в документ document.
     *
     * @param document                  Объект класса Document, представляющий PDF-документ,
     *                                  в который будет добавлена информация о средней длине паролей.
     * @param passwords                 Список паролей, для которых будет определена средняя длина.
     * @param averageLengthIsSelected   Флаг, указывающий, следует ли выводить информацию о средней длине паролей.
     */
    private void printAverageLength(Document document, ArrayList<Password> passwords, boolean averageLengthIsSelected){
        if (averageLengthIsSelected){
            double averageLength = round(passwords.stream().mapToInt(Password::getLength).average().orElse(0));
            document.add(new Paragraph("Средняя длина: " + averageLength).setMarginLeft(20));
        }
    }

    /**
     * Выводит информацию о минимальной длине пароля в указанном списке паролей passwords в документ document.
     *
     * @param document              Объект класса Document, представляющий PDF-документ,
     *                              в который будет добавлена информация о минимальной длине пароля.
     * @param passwords             Список паролей, для которых будет определена минимальная длина.
     * @param minLengthIsSelected   Флаг, указывающий, следует ли выводить информацию о минимальной длине пароля.
     */
    private void printMinLength(Document document, ArrayList<Password> passwords, boolean minLengthIsSelected){
        if (minLengthIsSelected){
            int minLength = passwords.stream().mapToInt(Password::getLength).min().orElse(0);
            document.add(new Paragraph("Минимальная длина: " + minLength).setMarginLeft(20));
        }
    }

    /**
     * Выводит информацию о самом распространённом пароле в указанном списке паролей passwords в документ document.
     *
     * @param document   Объект класса Document, представляющий PDF-документ,
     *                   в который будет добавлена информация о самом распространённом пароле.
     * @param passwords  Список паролей, для которых будет определён самый распространённый пароль.
     */
    private void printMostPopularPassword(Document document, ArrayList<Password> passwords) {
        String pass = "";
        int usageCount = 0;
        int totalCount = passwords.size();
        for (Password password : passwords) {
            totalCount += (password.getUsageCount() - 1);
            if (password.getUsageCount() > usageCount) {

                pass = password.getPassword();
                usageCount = password.getUsageCount();

            }
        }
        document.add(new Paragraph("Самый распространённый пароль: " + pass + " Использован: " + usageCount + " (" +
                round((double) usageCount / totalCount * 100) + "%)").setMarginLeft(20));
    }

    /**
     * Выводит информацию о самой популярной группе символов в указанном списке паролей passwords в документ document.
     * Группа символов определяется с помощью параметра groupSymbolLength.
     * Если mostPopularGroupSymbols имеет значение false, метод завершает свою работу без вывода информации.
     *
     * @param document   Объект класса Document, представляющий PDF-документ,
     *                   в который будет добавлена информация о самой популярной группе символов.
     * @param passwords  Список паролей, для которых будет определена самая популярная группа символов.
     */
    private void printMostPopularSymbolGroup(Document document, ArrayList<Password> passwords) {
        if (!this.mostPopularGroupSymbols) {return;}
        Map<String, Integer> occurrences = new HashMap<>();

        for (Password password : passwords) {
            String passwordValue = password.getPassword();
            for (int i = 0; i <= passwordValue.length() - groupSymbolLength; i++) {
                String substring = passwordValue.substring(i, i + groupSymbolLength);
                occurrences.put(substring, occurrences.getOrDefault(substring, 0) + password.getUsageCount());
            }
        }

        document.add(new Paragraph("Самая популярная группа символов (Длины: " + groupSymbolLength + "): "
                                    + searchSubstring(occurrences)).setMarginLeft(20));
        printTopOccurrencesByCategory(occurrences, document);
    }

    /**
     * Выводит информацию о самых популярных группах символов, сортируя их по убыванию количества использований.
     * Группы символов делятся на две категории: числовые и нечисловые.
     *
     * @param occurrences  Map, содержащая информацию о количестве вхождений каждой группы символов.
     * @param document     Объект класса Document, представляющий PDF-документ,
     *                     в который будет добавлена информация о популярных группах символов.
     */
    public void printTopOccurrencesByCategory(Map<String, Integer> occurrences, Document document) {
        List<Map.Entry<String, Integer>> digitOccurrences = new ArrayList<>();
        List<Map.Entry<String, Integer>> nonDigitOccurrences = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : occurrences.entrySet()) {
            String substring = entry.getKey();
            if (substring.matches("\\d+")) {
                digitOccurrences.add(entry);
            } else {
                nonDigitOccurrences.add(entry);
            }
        }

        digitOccurrences.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        nonDigitOccurrences.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        document.add(new Paragraph("Самые популярные группы из цифр:").setItalic());

        printTable(document, digitOccurrences, topPopularGroupSymbolCount, "Password");
        document.add(new Paragraph("Самые популярные группы из не цифр: ").setItalic());
        printTable(document, nonDigitOccurrences, topPopularGroupSymbolCount, "Password");
    }

    /**
     * Выводит информацию о самом популярном символе в списке паролей в указанный PDF-документ.
     *
     * @param document  Объект класса Document, представляющий PDF-документ, в который будет добавлена информация о популярном символе.
     * @param passwords Список паролей, в котором будет производиться подсчет частоты символов.
     */
    private void printTopPopularSymbol(Document document, ArrayList<Password> passwords){
        if (mostPopularSymbolIsSelected) {
            Map<String, Integer> charOccurrences = new HashMap<>();

            for (Password password : passwords) {
                for (String character : password.getPassword().split("")) {
                    charOccurrences.put(character, charOccurrences.getOrDefault(character, 0) + 1);
                }
            }

            document.add(new Paragraph("Самый популярный символ: " + searchSubstring(charOccurrences)));
        }
    }

    /**
     * Выполняет поиск наиболее часто встречающегося символа в словаре charOccurrences.
     *
     * @param charOccurrences Словарь, содержащий символы в качестве ключей и их количество в качестве значений.
     * @return Наиболее часто встречающийся символ.
     */
    private String searchSubstring(Map<String, Integer> charOccurrences) {
        String mostFrequentChar = "";
        int maxOccurrences = 0;
        for (Map.Entry<String, Integer> entry : charOccurrences.entrySet()) {
            if (entry.getValue() > maxOccurrences) {
                mostFrequentChar = entry.getKey();
                maxOccurrences = entry.getValue();
            }
        }

        return mostFrequentChar;
    }

    /**
     * Выводит таблицу с наиболее часто встречающимися символами из списка паролей.
     *
     * @param document  Объект класса Document, представляющий PDF-документ, в который будет добавлена таблица.
     * @param passwords Список паролей, из которых будет производиться подсчет частоты символов.
     */
    public void printTopFrequentCharacters(Document document, List<Password> passwords) {
        if (!topPopularSymbolIsSelected) {return;}

        Map<String, Integer> charOccurrences = new HashMap<>();

        for (Password password : passwords) {
            for (String character : password.getPassword().split("")) {
                charOccurrences.put(character, charOccurrences.getOrDefault(character, 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(charOccurrences.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        printTable(document, sortedEntries, topPopularSymbolCount, "Symbol");
    }

    /**
     * Создает и добавляет таблицу в PDF-документ на основе отсортированных записей (символ-количество),
     * выводя указанное количество строк и используя указанный режим.
     *
     * @param document      Объект класса Document, представляющий PDF-документ, в который будет добавлена таблица.
     * @param sortedEntries Отсортированный список записей (символ-количество).
     * @param countString   Количество строк, которые будут добавлены в таблицу.
     * @param mode          Режим таблицы ("Symbol" или "Password").
     */
    private void printTable(Document document, List<Map.Entry<String, Integer>> sortedEntries, int countString,
                            String mode){
        Table table = new Table(2);
        if (mode.equals("Symbol")){
            table.addCell(new Cell().add(new Paragraph("Символ")));
        } else {
            table.addCell(new Cell().add(new Paragraph("Пароль")));
        }
        table.addCell(new Cell().add(new Paragraph("Количество повторений")));

        int count = 0;

        for (Map.Entry<String, Integer> entry : sortedEntries) {
            if (count >= countString) {
                break;
            }

            String c = entry.getKey();
            int occurrences = entry.getValue();

            Cell charCell = new Cell().add(new Paragraph(String.valueOf(c)));
            Cell countCell = new Cell().add(new Paragraph(String.valueOf(occurrences)));

            table.addCell(charCell);
            table.addCell(countCell);

            count++;
        }

        document.add(table);
    }

    /**
     * Добавляет печать в сгенерированный PDF-документ.
     *
     * @param document Объект класса Document, представляющий PDF-документ, в который будет добавлено изображение.
     * @throws MalformedURLException Если указанный URL изображения недействителен.
     */
    private void printImage(Document document) throws MalformedURLException {
        ImageData imageData = ImageDataFactory.create("src/main/resources/com/treasure_hunter_java/images/Печать.png");
        document.add(new Image(imageData).setHeight(128).setWidth(128).setFixedPosition(400, 50));
    }

}