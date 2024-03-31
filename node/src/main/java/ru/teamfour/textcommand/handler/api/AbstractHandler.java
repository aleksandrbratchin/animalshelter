package ru.teamfour.textcommand.handler.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.textcommand.command.UnknownCommand;
import ru.teamfour.textcommand.command.api.TextCommand;

@Component
public abstract class AbstractHandler implements Handler{
    private Handler nextHandler;
    @Autowired
    private UnknownCommand unknownCommand;
    protected TextCommand textCommand;

    /**
     * Если цепочка завершена ({@code nextHandler == null}) возврацает {@link UnknownCommand}
     * @param update
     * @return {@link TextCommand}
     */
    @Override
    public TextCommand handleRequest(Update update) {
        if (nextHandler != null) {
            return nextHandler.handleRequest(update);
        }
        return unknownCommand;
    }


    @Override
    public void setNext(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
