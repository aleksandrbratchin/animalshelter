package ru.teamfour.dispatcher.controllers;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dispatcher.configuration.rabbitmq.RabbitConfiguration;
import ru.teamfour.dispatcher.myutils.MessageUtils;
import ru.teamfour.dispatcher.service.api.UpdateProducer;

@Log4j2
@Controller
public class UpdateController {
    private final TelegramBot telegramBot;
    private final MessageUtils messageUtils;
    private final RabbitConfiguration rabbitConfiguration;
    private final UpdateProducer updateProducer;

    public UpdateController(TelegramBot telegramBot, MessageUtils messageUtils, RabbitConfiguration rabbitConfiguration, UpdateProducer updateProducer) {
        this.telegramBot = telegramBot;
        this.messageUtils = messageUtils;
        this.rabbitConfiguration = rabbitConfiguration;
        this.updateProducer = updateProducer;
    }

    @PostConstruct
    private void init() {
        telegramBot.register(this);
    }

    /***
     * Первичная валидация входящих данных
     */
    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Update is null");
            return;
        }
        if (!update.hasMessage()) {
            log.error("Error update.getMessage()");
            return;
        }
        distributeMessageByType(update);
    }

    /***
     * Распределение сообщений по типу
     */
    private void distributeMessageByType(Update update) {
        var message = update.getMessage();
        if (message.hasText()) {
            processTextMessage(update);
        } else if (message.hasPhoto()) {
            processPhotoMessage(update);
        } else {
            String msq = "Неизвестный тип сообщения!";
            log.error(msq);
            sendToTelegram(messageUtils.generateSendMessageWithText(update, msq));
        }
    }

    /***
     * Публичный метод отправки ответов в телеграм
     */
    public void sendToTelegram(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

    /***
     * Отправить текстовое сообщение в брокер
     */
    private void processTextMessage(Update update) {
        log.info("В брокер отправлено текстовое сообщение из чата: \"" + update.getMessage().getChatId() + "\" с текстом: \"" + update.getMessage().getText() + "\"");
        updateProducer.produce(rabbitConfiguration.getText(), update);
    }

    /***
     * Отправить фото в брокер
     */
    private void processPhotoMessage(Update update) {
        log.info("В брокер отправлено фото сообщение из чата: \"" + update.getMessage().getChatId());
        updateProducer.produce(rabbitConfiguration.getPhoto(), update);
    }

}
