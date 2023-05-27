package com.treasure_hunter_java.report;

import com.treasure_hunter_java.Main;
import com.treasure_hunter_java.dictionary.Password;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;

public class GeneratePDF {

    ArrayList<Password> passwords = new ArrayList<>();

    GeneratePDF(ArrayList<Password> passwords) {
        this.passwords = passwords;
    }

    public void generate() {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDType1Font font = PDType1Font.HELVETICA_BOLD;

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(font, 12);

                printTotalCount(contentStream, passwords);
                printUniqueCount(contentStream, passwords);
            }

            document.save(Main.mainWorkDirectory + "/password_report.pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printTotalCount(PDPageContentStream contentStream, ArrayList<Password> passwords) throws IOException {
        long totalCount = passwords.size();
        String totalCountText = "Total count password: " + totalCount;
        contentStream.beginText();
        contentStream.newLineAtOffset(100, 700);
        contentStream.showText(totalCountText);
        contentStream.endText();
    }

    private static void printUniqueCount(PDPageContentStream contentStream, ArrayList<Password> passwords) throws IOException {
        long uniqueCount = passwords.stream().distinct().count();
        String uniqueCountText = "Unique passwords count: " + uniqueCount;
        contentStream.beginText();
        contentStream.newLineAtOffset(100, 680);
        contentStream.showText(uniqueCountText);
        contentStream.endText();
    }
}


