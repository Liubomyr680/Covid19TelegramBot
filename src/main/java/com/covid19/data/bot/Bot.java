package com.covid19.data.bot;

import com.covid19.data.service.DataResponse;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
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

    public void sndDataFromDB(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try{
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static SendMessage sendInlineKeyBoardMessage(long chatId) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();

        inlineKeyboardButton3.setText("Нажміть для введення");
        inlineKeyboardButton3.setCallbackData("Введіть дані");

        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();

        keyboardButtonsRow3.add(inlineKeyboardButton3);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow3);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return new SendMessage().setChatId(chatId).setText("Введіть назву області та села через пробіл").setReplyMarkup(inlineKeyboardMarkup);
    }

    //метод для прийому повідомлень
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        Data data = new Data(dataResponse);

        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().equals("/start")) {
                    try {
                        execute(sendInlineKeyBoardMessage(update.getMessage().getChatId()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    String[] areaAndSettlement = update.getMessage().getText().split(" ");
                    sndDataFromDB(update.getMessage(), data.result(areaAndSettlement[0], areaAndSettlement[1]));
                }
            }
        } else if (update.hasCallbackQuery()) {
            try {
                execute(new SendMessage().setText(
                        update.getCallbackQuery().getData())
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
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
