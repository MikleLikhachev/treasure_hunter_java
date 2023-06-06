package com.treasure_hunter_java.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

public class TelegramBotFileSender extends TelegramLongPollingBot {
    private final String token;
    private final String chatId;

    public TelegramBotFileSender(String token, String chatId){
        this.token = token;
        this.chatId = chatId;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    public void sendFile(String filePath) {
        try {
            InputFile inputFile = new InputFile(new File(filePath));
            SendDocument sendDocument = new SendDocument(chatId, inputFile);

            execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendText(String text){
        try{
            SendMessage sendMessage = new SendMessage(chatId, text);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}

