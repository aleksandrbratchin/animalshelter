package ru.teamfour.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.teamfour.service.api.ProducerService;
import transfer.TransferByteObject;

/***
 * Отправляет сообщения в брокер
 */
@Log4j2
@Service
public class ProducerServiceImpl implements ProducerService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitQueue.messages.answer.TEXT}")
    private String answerMessageQueue;

    @Value("${rabbitQueue.messages.answer.PHOTO}")
    private String answerPhotoMessageQueue;

    public ProducerServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void producerAnswer(SendMessage sendMessage) {
        rabbitTemplate.convertAndSend(answerMessageQueue, sendMessage);
    }

    @Override
    public void producerAnswer(TransferByteObject transferByteObject) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            rabbitTemplate.convertAndSend(answerPhotoMessageQueue, objectMapper.writeValueAsString(transferByteObject));
        } catch (JsonProcessingException e){
            log.error("Ошибка при сериализации");
        }
    }

}
