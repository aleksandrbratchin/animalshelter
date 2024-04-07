package ru.teamfour.dispatcher.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.teamfour.dispatcher.controllers.UpdateController;
import ru.teamfour.dispatcher.service.api.AnswerConsumer;
import transfer.TransferByteObject;

import java.io.ByteArrayInputStream;

@Log4j2
@Service
public class AnswerConsumerImpl implements AnswerConsumer {

    private final UpdateController updateController;

    public AnswerConsumerImpl(UpdateController updateController) {
        this.updateController = updateController;
    }

    @Override
    @RabbitListener(queues = "${rabbitQueue.messages.answer.TEXT}")
    public void consumer(SendMessage sendMessage) {
        updateController.sendToTelegram(sendMessage);
    }

    @Override
    @RabbitListener(queues = "${rabbitQueue.messages.answer.PHOTO}")
    public void consumer(String json) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            TransferByteObject transferByteObject = objectMapper.readValue(json, TransferByteObject.class);
            InputFile photo = new InputFile(new ByteArrayInputStream(transferByteObject.getData()), "file");
            updateController.sendToTelegramPhoto(new SendPhoto(transferByteObject.getChatId(), photo));
        } catch (Exception e){
            log.error("Не удалось десериализовать");
        }
    }


}
