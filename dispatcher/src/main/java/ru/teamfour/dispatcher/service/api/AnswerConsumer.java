package ru.teamfour.dispatcher.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AnswerConsumer {
    void consumer(SendMessage sendMessage);

    void consumer(String sendByte) throws JsonProcessingException;
}
