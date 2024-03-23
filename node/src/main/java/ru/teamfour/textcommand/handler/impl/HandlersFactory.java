package ru.teamfour.textcommand.handler.impl;

import org.springframework.stereotype.Component;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.handler.api.Handlers;

import java.util.List;

@Component
public class HandlersFactory {

    private final List<Handlers> handlersList;

    public HandlersFactory(List<Handlers> handlersList) {
        this.handlersList = handlersList;
    }

    public Handlers getHandlers(State state) {
        for (Handlers handlers : handlersList) {
            if (handlers.isHandlers(state)) {
                return handlers;
            }
        }
        return null; //todo поведение пока непонятно
    }
}
