package ru.teamfour.textcommand.handler.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.textcommand.command.api.TextCommand;
import ru.teamfour.textcommand.command.UnknownCommand;

@Component
public class AbstractHandler implements Handler {
    private Handler nextHandler;

    @Autowired
    private UnknownCommand unknownCommand;

    @Override
    public void setNext(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }
    @Override
    public TextCommand handleRequest(Update update) {
        if (nextHandler != null) {
            return nextHandler.handleRequest(update);
        }
        return unknownCommand;
    }
}
