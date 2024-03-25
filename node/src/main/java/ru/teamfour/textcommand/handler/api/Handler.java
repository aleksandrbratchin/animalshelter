package ru.teamfour.textcommand.handler.api;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.textcommand.command.api.TextCommand;

public interface Handler {
    TextCommand handleRequest(Update update);
    void setNext(Handler nextHandler);
}
