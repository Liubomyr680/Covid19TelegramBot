package com.covid19.data.bot;

import com.covid19.data.service.DataResponse;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.HashSet;
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

    public void sndDataFromDB(Message message, String text){
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

    public void setButtons(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow firstKeyBoardRow = new KeyboardRow();
        firstKeyBoardRow.add(new KeyboardButton("/start"));
        keyboardRowList.add(firstKeyBoardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public static SendMessage sendInlineKeyBoardMessage(long chatId) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
//        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();


//        inlineKeyboardButton1.setText("Область");
//        inlineKeyboardButton1.setCallbackData("Введіть область");
//
//        inlineKeyboardButton2.setText("Район");
//        inlineKeyboardButton2.setCallbackData("Введіть район");

        inlineKeyboardButton3.setText("Село");
        inlineKeyboardButton3.setCallbackData("Введіть село");

//        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();

//        keyboardButtonsRow1.add(inlineKeyboardButton1);
//        keyboardButtonsRow2.add(inlineKeyboardButton2);
        keyboardButtonsRow3.add(inlineKeyboardButton3);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
//        rowList.add(keyboardButtonsRow1);
//        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return new SendMessage().setChatId(chatId).setText("Категорії по яких можна здійснити пошук").setReplyMarkup(inlineKeyboardMarkup);
    }

    //метод для прийому повідомлень
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();
//        HashSet<String> area = dataResponse.getAllAreaFromTable();
        Data data = new Data(dataResponse);

            if (update.hasMessage()) {
                if (update.getMessage().hasText()) {
                    if (update.getMessage().getText().equals("Hello")) {
                        try {
                            execute(sendInlineKeyBoardMessage(update.getMessage().getChatId()));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    } else {
                        sndDataFromDB(update.getMessage(), data.result(message.getText()));
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
