package com.treasure_hunter_java.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

/**
 * TelegramBotFileSender - класс, представляющий Telegram-бота для отправки файлов и текстовых сообщений.
 * Он наследуется от класса TelegramLongPollingBot из библиотеки TelegramBots.
 */
public class TelegramBotFileSender extends TelegramLongPollingBot {
    private final String token;
    private final String chatId;

    /**
     * Конструктор класса TelegramBotFileSender.
     *
     * @param token  Токен бота Telegram.
     * @param chatId Идентификатор чата, в который будут отправляться сообщения.
     */
    public TelegramBotFileSender(String token, String chatId) {
        this.token = token;
        this.chatId = chatId;
    }

    /**
     * Метод onUpdateReceived не используется и реализован только из-за требований наследования.
     *
     * @param update Обновление, полученное от Telegram.
     */
    @Override
    public void onUpdateReceived(Update update) {
        // Метод не используется
    }

    /**
     * Отправляет файл в указанный чат.
     *
     * @param filePath Путь к файлу, который нужно отправить.
     */
    public void sendFile(String filePath) {
        try {
            InputFile inputFile = new InputFile(new File(filePath));
            SendDocument sendDocument = new SendDocument(chatId, inputFile);

            execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отправляет текстовое сообщение.
     *
     * @param text Текст сообщения.
     */
    public void sendText(String text) {
        try {
            SendMessage sendMessage = new SendMessage(chatId, text);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает имя бота.
     *
     * @return Имя бота.
     */
    @Override
    public String getBotUsername() {
        return null;
    }

    /**
     * Возвращает токен бота.
     *
     * @return Токен бота.
     */
    @Override
    public String getBotToken() {
        return token;
    }
}