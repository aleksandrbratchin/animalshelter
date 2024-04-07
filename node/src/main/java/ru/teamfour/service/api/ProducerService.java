package ru.teamfour.service.api;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import transfer.TransferByteObject;

/***
 * Для отправки ответов с node в брокер
 */
public interface ProducerService {
    void producerAnswer(SendMessage sendMessage);

    void producerAnswer(TransferByteObject transferByteObject);
}
