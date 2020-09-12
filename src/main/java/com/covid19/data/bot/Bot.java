package com.covid19.data.bot;

import com.covid19.data.Covid19Application;
import com.covid19.data.entity.Covid19Data;
import com.covid19.data.service.DataResponse;
import lombok.Data;
import org.glassfish.hk2.api.messaging.SubscribeTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private final DataResponse dataResponse;

    public Bot(DataResponse dataResponse) {
        this.dataResponse = dataResponse;
    }

    public void startBot(){
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try{
            telegramBotsApi.registerBot(new Bot(dataResponse));
        }catch (TelegramApiRequestException e){
            e.printStackTrace();
        }
    }

    public void sndMsg(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try{
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sndDataFromDB(Message message, List<Covid19Data> list){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        try{
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow firstKeyBoardRow = new KeyboardRow();
        firstKeyBoardRow.add(new KeyboardButton("/help"));
        firstKeyBoardRow.add(new KeyboardButton("/settings"));
        keyboardRowList.add(firstKeyBoardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    //метод для прийому повідомлень
    @Override
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();
        if(message != null && message.hasText()){

            switch (message.getText()){

                case "/help":
                    sndMsg(message, "How can i help you?");
                    break;
                case "/settings":
                    sndMsg(message, "What we will configure?");
                    break;
                default:
                    sndDataFromDB(message, dataResponse.getData(message.getText()));
            }
        }
    }


    @Override
    public String getBotUsername() {
        return "Liubomyr_bot";
    }

    @Override
    public String getBotToken() {
        return "1109038611:AAEHFZLGPuvj4e6UzRaeE6rKy-Hng1Et3gA";
    }
}
