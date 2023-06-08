package com.treasure_hunter_java.zip;

import com.treasure_hunter_java.Main;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import java.io.*;

/**
 * DirectoryArchiver - класс, предназначенный для архивации директории в формате ZIP.
 */
public class DirectoryArchiver {
    private String directoryPath;
    private String outputFilePath = "";

    /**
     * Конструктор класса DirectoryArchiver.
     * Определяет путь к директории и путь к архивному файлу на основе значения переменной mainWorkDirectory в классе Main.
     */
    public DirectoryArchiver() {
        if (Main.getMainWorkDirectory() != null) {
            directoryPath = Main.getMainWorkDirectory().toString();
            outputFilePath = directoryPath + File.separator + "data.zip";
        }
    }

    /**
     * Архивирует директорию в формате ZIP.
     *
     * @throws IOException              Если возникают ошибки ввода-вывода при архивации.
     * @throws IllegalArgumentException Если указанный путь к директории недействителен.
     */
    public void archiveDirectory() throws IOException {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory path: " + directoryPath);
        }

        try (FileOutputStream fos = new FileOutputStream(outputFilePath);
             ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(fos)) {
            archiveFiles(directory, directory.getName(), zipOut);
        }
    }

    /**
     * Рекурсивно архивирует файлы и директории внутри указанной директории.
     *
     * @param file        Файл или директория для архивации.
     * @param parentPath  Родительский путь, используется для создания относительных путей в архиве.
     * @param zipOut      Выходной поток архива.
     * @throws IOException Если возникают ошибки ввода-вывода при архивации.
     */
    private void archiveFiles(File file, String parentPath, ZipArchiveOutputStream zipOut) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    String entryPath = parentPath + File.separator + childFile.getName();
                    archiveFiles(childFile, entryPath, zipOut);
                }
            }
        } else {
            if (!file.getAbsolutePath().equals(outputFilePath)) {
                ZipArchiveEntry zipEntry = new ZipArchiveEntry(parentPath);
                zipOut.putArchiveEntry(zipEntry);

                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        zipOut.write(buffer, 0, bytesRead);
                    }
                }

                zipOut.closeArchiveEntry();
            }
        }
    }
}