package com.treasure_hunter_java.zip;

import com.treasure_hunter_java.Main;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.*;

public class DirectoryArchiver {
    private String directoryPath;
    private String outputFilePath = "";

    public DirectoryArchiver() {
        if (Main.mainWorkDirectory != null) {
            directoryPath = Main.mainWorkDirectory.toString();
            outputFilePath = directoryPath + File.separator + "data.zip";
        }
    }

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
