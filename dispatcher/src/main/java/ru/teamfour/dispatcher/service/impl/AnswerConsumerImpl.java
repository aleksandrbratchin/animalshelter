package ru.teamfour.dispatcher.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.teamfour.dispatcher.controllers.UpdateController;
import ru.teamfour.dispatcher.service.api.AnswerConsumer;

@Service
public class AnswerConsumerImpl implements AnswerConsumer {

    private final UpdateController updateController;

    public AnswerConsumerImpl(UpdateController updateController) {
        this.updateController = updateController;
    }

    @Override
    @RabbitListener(queues = "${rabbitQueue.messages.answer}")
    public void consumer(SendMessage sendMessage) {
        updateController.sendToTelegram(sendMessage);
    }
}
