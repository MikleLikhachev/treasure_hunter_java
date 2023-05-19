package com.treasure_hunter_java.zip;

import com.treasure_hunter_java.Main;
import com.treasure_hunter_java.controllers.ZipController;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

public class DirectoryArchiver {
    private String directoryPath = Main.mainWorkDirectory.toString();
    private String outputFilePath = "";
    private String password;

    public DirectoryArchiver() {
    }

    public DirectoryArchiver(String directoryPath, String outputFilePath) {
        this.directoryPath = directoryPath;
        this.outputFilePath = outputFilePath;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        outputFilePath = name + ".zip";
    }

    public void archiveDirectory() throws IOException {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory path: " + directoryPath);
        }

        FileOutputStream fos = new FileOutputStream(outputFilePath);
        ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(fos);
        if (password != null && !password.isEmpty()) {
            zipOut.setComment(password);
        }

        archiveFiles(directory, directory.getName(), zipOut);
        zipOut.close();
        fos.close();
    }

    private void archiveFiles(File file, String parentPath, ZipArchiveOutputStream zipOut) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    String entryPath = parentPath + "/" + childFile.getName();
                    archiveFiles(childFile, entryPath, zipOut);
                }
            }
        } else {
            ZipArchiveEntry zipEntry = new ZipArchiveEntry(parentPath);
            zipOut.putArchiveEntry(zipEntry);

            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                zipOut.write(buffer, 0, bytesRead);
            }

            fis.close();
            zipOut.closeArchiveEntry();
        }
    }
}
