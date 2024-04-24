package ru.teamfour.service.api;

import org.telegram.telegrambots.meta.api.objects.Update;
import transfer.TransferByteObject;

/***
 * Для считывания сообщений из брокера
 */
public interface ConsumerService {

    /**
     * Прием и обработка текстовых сообщений
     *
     * @param update {@link Update}
     */
    void consumerTextMessageUpdates(Update update);

    /**
     * Прием и обработка фото сообщений
     *
     * @param json {@link TransferByteObject}
     */
    void consumerPhotoMessageUpdates(String json);
}
