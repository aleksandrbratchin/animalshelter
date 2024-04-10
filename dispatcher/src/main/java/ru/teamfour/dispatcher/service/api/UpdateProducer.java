package ru.teamfour.dispatcher.service.api;

import org.telegram.telegrambots.meta.api.objects.Update;

/***
 * Для передачи update в rabbitmq
 */
public interface UpdateProducer {
    void produce(String rabbitQueue, Update update);

    void producePhoto(String rabbitQueue, Update update);
}
