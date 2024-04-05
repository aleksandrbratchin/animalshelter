package ru.teamfour.textcommand.handler.api;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.textcommand.command.api.TextCommand;

/**
 * Элемент цепочки обязанностей {@link HandlersState}
 */
public interface Handler {
    TextCommand handleRequest(Update update);

    /**
     * Устанавливает следующий элемент
     * @param nextHandler {@link Handler}
     */
    void setNext(Handler nextHandler);
}
