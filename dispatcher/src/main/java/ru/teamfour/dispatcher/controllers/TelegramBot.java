package ru.teamfour.dispatcher.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.teamfour.dispatcher.configuration.telegram.BotConfig;

@Log4j2
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private UpdateController updateController;

    public TelegramBot(BotConfig config) {
        super(config.getToken());

        this.config = config;

    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    public void register(UpdateController updateController) {
        if (this.updateController == null) {
            this.updateController = updateController;
        }
    }

    /***
     * Сюда падают все сообщения
     */
    @Override
    public void onUpdateReceived(Update update) {
        updateController.processUpdate(update);
    }

    /***
     * Отправить ответ в телегу
     */
    public void sendAnswerMessage(SendMessage sendMessage) {
        if (sendMessage != null) {
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /***
     * Отправить фото в телегу
     */
    public void sendAnswerPhotoMessage(SendPhoto sendPhoto) {
        if (sendPhoto != null) {
            try {
                execute(sendPhoto);
            } catch (TelegramApiException e) {
                log.error("Не удалось принять фото(");
                sendAnswerMessage(new SendMessage(sendPhoto.getChatId(), "Не удалось принять фото("));
            }
        }
    }

}
