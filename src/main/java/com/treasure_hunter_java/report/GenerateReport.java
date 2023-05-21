package com.treasure_hunter_java.report;

import com.itextpdf.layout.properties.TextAlignment;
import com.sun.jna.platform.unix.X11;
import com.treasure_hunter_java.Main;
import com.treasure_hunter_java.dictionary.Password;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.w3c.dom.Document;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class GenerateReport {

    Path directory = Path.of(Main.mainWorkDirectory.toUri());
    ArrayList<Password> passwords = new ArrayList<>();
    private boolean totalCountPasswords = true;
    private boolean uniqueCountPasswords;

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
                    passwords.add(new Password(password)); /// Разобраться, почему не работает без public
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void qwerty() throws IOException{
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
        qwerty();
        System.out.println(passwords);
        generatePDF();
    }

    private void generatePDF() {
        String reportFilePath = Main.mainWorkDirectory + "/report.pdf";
        try {
            // Создание PDF-документа
            PdfWriter pdfWriter = new PdfWriter(reportFilePath);
            PdfDocument pdfDoc = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDoc);

            PdfFont font = PdfFontFactory.createFont("src/main/resources/com/treasure_hunter_java/font/Times New Roman.ttf");

            document.setFont(font);

            writeReportInfo(document, font);

            writeGeneralInfo(document, passwords);

            writeUniqueCount(document, passwords);

            writeLengthData(document, passwords);

            writeMostPopularPassword(document, passwords);

            document.close();

            System.out.println("Отчет успешно сохранен в файл: " + reportFilePath);
        } catch (IOException e) {
            System.out.println("Ошибка при создании отчета: " + e.getMessage());
        }

    }
    private static void writeReportInfo(Document document, PdfFont font) {
        Paragraph paragraph = new Paragraph("Отчет")
                .setFont(font)
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER)
                .setCharacterSpacing(1);
        document.add(paragraph);
        String date = "Дата создания: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        //String reportInfoText = "Данный отчёт был создан автоматической утилитой Treasure Hunter.";

        //document.add(new Paragraph(reportInfoText).setBold());
        document.add(new Paragraph(date));
    }

    private static void writeGeneralInfo(Document document, ArrayList<Password> passwords) {
        int totalCount = 0;
        for (Password pas : passwords) {totalCount += pas.getUsageCount();}
        document.add(new Paragraph("Общая информация:"));
        document.add(new Paragraph("Total count password: " + totalCount));
    }
    private static void writeUniqueCount(Document document, ArrayList<Password> passwords) throws IOException {
        long uniqueCount = passwords.stream().distinct().count();
        document.add(new Paragraph("Unique passwords count: " + uniqueCount));

    }

    private void writeLengthData(Document document, ArrayList<Password> passwords){
        int maxLength = passwords.stream().mapToInt(Password::getLength).max().orElse(0);
        double averageLength = passwords.stream().mapToInt(Password::getLength).average().orElse(0);
        int minLength = passwords.stream().mapToInt(Password::getLength).min().orElse(0);

        document.add(new Paragraph("Максимальная длина найденного пароля: " + maxLength));
        document.add(new Paragraph("Средняя длина: " + averageLength));
        document.add(new Paragraph("Минимальная длина: " + minLength));
    }

    private void writeMostPopularPassword(Document document, ArrayList<Password> passwords) {
        String pass = "";
        int usageCount = 0;
        for (Password password : passwords) {
            if (password.getUsageCount() > usageCount) {

                pass = password.getPassword();
                usageCount = password.getUsageCount();

            }
        }
        document.add(new Paragraph("Самый распространённый пароль: " + pass +
                " Использован: " + usageCount));
    }

}
