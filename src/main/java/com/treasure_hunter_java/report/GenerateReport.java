package com.treasure_hunter_java.report;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.treasure_hunter_java.Main;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Math.round;

public class GenerateReport {

    Path directory = Path.of(Main.mainWorkDirectory.toUri());
    private final ArrayList<Password> passwords = new ArrayList<>();
    private final int groupSymbolLength;
    private final int topGroupSymbolsLength;
    private final boolean totalCountPasswords;
    private final boolean uniqueCountPasswords;
    private final boolean passwordMaxLengthIsSelected;
    private final boolean passwordAverageLengthIsSelected;
    private final boolean passwordMinLengthIsSelected;
    private final boolean mostPopularGroupSymbols;
    private final int topPopularGroupSymbolCount;
    private final boolean topPopularSymbolIsSelected;
    private final int topPopularSymbolCount;
    private static final String FONT_FILE_PATH = "src/main/resources/com/treasure_hunter_java/font/Times New Roman.ttf";
    private static final Logger logger = Logger.getLogger(GenerateReport.class.getName());


    public GenerateReport(int groupSymbolLength, int topGroupSymbolsLength, boolean totalCountPasswords,
                          boolean uniqueCountPasswords, boolean passwordMaxLengthIsSelected,
                          boolean passwordAverageLengthIsSelected, boolean passwordMinLengthIsSelected,
                          boolean mostPopularGroupSymbols, int topPopularGroupSymbolCount,
                          boolean topPopularSymbolIsSelected, int topPopularSymbolCount) {
        this.groupSymbolLength = groupSymbolLength;
        this.topGroupSymbolsLength = topGroupSymbolsLength;
        this.totalCountPasswords = totalCountPasswords;
        this.uniqueCountPasswords = uniqueCountPasswords;
        this.passwordMaxLengthIsSelected = passwordMaxLengthIsSelected;
        this.passwordAverageLengthIsSelected = passwordAverageLengthIsSelected;
        this.passwordMinLengthIsSelected = passwordMinLengthIsSelected;
        this.mostPopularGroupSymbols = mostPopularGroupSymbols;
        this.topPopularGroupSymbolCount = topPopularGroupSymbolCount;
        this.topPopularSymbolIsSelected = topPopularSymbolIsSelected;
        this.topPopularSymbolCount = topPopularSymbolCount;
    }

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
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void collectPasswords() throws IOException{
        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
                if (Files.isRegularFile(filePath) && filePath.toString().endsWith("passwords.txt")) {
                    extractPassword(filePath.toFile());
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.err.println("Failed to access file: " + file);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public void rocket() throws IOException {
        collectPasswords();
        generatePDF();
    }

    private void generatePDF() {
        String reportFilePath = Main.mainWorkDirectory + "/report.pdf";
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

            printTopPopularSymbols(document, passwords);

            printTopFrequentCharacters(document, passwords);

            printImage(document);

            logger.info(String.format("Отчет успешно сохранен в файл: %s", reportFilePath));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка при создании отчета", e);
        }

    }
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
        document.add(new Paragraph("Отчёт собран на основе: " + Main.mainWorkDirectory).setMarginLeft(20));
    }

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
    private static void printUniqueCount(Document document, ArrayList<Password> passwords, boolean uniqueCountPasswords) {
        if (uniqueCountPasswords) {
            long uniqueCount = passwords.stream().distinct().count();
            document.add(new Paragraph("Количество уникальных паролей: " + uniqueCount).setMarginLeft(20));
        }

    }

    private void printLengthData(Document document, ArrayList<Password> passwords){
        printMaxLength(document, passwords, passwordMaxLengthIsSelected);
        printAverageLength(document, passwords, passwordAverageLengthIsSelected);
        printMinLength(document, passwords, passwordMinLengthIsSelected);
    }

    private void printMaxLength(Document document, ArrayList<Password> passwords, boolean maxLengthIsSelected){
        if (maxLengthIsSelected){
            int maxLength = passwords.stream().mapToInt(Password::getLength).max().orElse(0);
            document.add(new Paragraph("Максимальная длина найденного пароля: " + maxLength).setMarginLeft(20));
        }
    }

    private void printAverageLength(Document document, ArrayList<Password> passwords, boolean averageLengthIsSelected){
        if (averageLengthIsSelected){
            double averageLength = round(passwords.stream().mapToInt(Password::getLength).average().orElse(0));
            document.add(new Paragraph("Средняя длина: " + averageLength).setMarginLeft(20));
        }
    }

    private void printMinLength(Document document, ArrayList<Password> passwords, boolean minLengthIsSelected){
        if (minLengthIsSelected){
            int minLength = passwords.stream().mapToInt(Password::getLength).min().orElse(0);
            document.add(new Paragraph("Минимальная длина: " + minLength).setMarginLeft(20));
        }
    }

    private void printMostPopularPassword(Document document, ArrayList<Password> passwords) {
        String pass = "";
        int usageCount = 0;
        int totalCount = 0;
        for (Password password : passwords) {
            totalCount += password.getUsageCount();
            if (password.getUsageCount() > usageCount) {

                pass = password.getPassword();
                usageCount = password.getUsageCount();

            }
        }
        document.add(new Paragraph("Самый распространённый пароль: " + pass + " Использован: " + usageCount + " (" +
                round((double) usageCount / totalCount * 100) + "%)").setMarginLeft(20));
    }

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

        String mostCommonSubstring = "";
        int maxOccurrences = 0;
        for (Map.Entry<String, Integer> entry : occurrences.entrySet()) {
            if (entry.getValue() > maxOccurrences) {
                mostCommonSubstring = entry.getKey();
                maxOccurrences = entry.getValue();
            }
        }

        document.add(new Paragraph("Самая популярная группа символов (Длины: " + groupSymbolLength + "): "
                                    + mostCommonSubstring).setMarginLeft(20));
        printTopOccurrencesByCategory(occurrences, document);
    }

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

    private void printTopPopularSymbols(Document document, ArrayList<Password> passwords){
        if (topPopularSymbolIsSelected) {
            Map<String, Integer> charOccurrences = new HashMap<>();

            for (Password password : passwords) {
                for (String character : password.getPassword().split("")) {
                    charOccurrences.put(character, charOccurrences.getOrDefault(character, 0) + 1);
                }
            }

            String mostFrequentChar = "";
            int maxOccurrences = 0;
            for (Map.Entry<String, Integer> entry : charOccurrences.entrySet()) {
                if (entry.getValue() > maxOccurrences) {
                    mostFrequentChar = entry.getKey();
                    maxOccurrences = entry.getValue();
                }
            }

            document.add(new Paragraph("Самый популярный символ: " + mostFrequentChar));
        }
    }

    public void printTopFrequentCharacters(Document document, ArrayList<Password> passwords) {
        if (!topPopularSymbolIsSelected) {
            return;
        }

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

    private void printTable(Document document, List<Map.Entry<String, Integer>> sortedEntries, int countString,
                            String mode){
        Table table = new Table(2);
        if (mode.equals("Password")){
            table.addCell(new Cell().add(new Paragraph("Пароль")));
        } else {
            table.addCell(new Cell().add(new Paragraph("Символ")));
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

    private void printImage(Document document) throws MalformedURLException {
        ImageData imageData = ImageDataFactory.create("src/main/resources/com/treasure_hunter_java/images/Печать.png");
        document.add(new Image(imageData).setHeight(128).setWidth(128).setFixedPosition(400, 50));
    }

}
