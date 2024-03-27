package ru.teamfour.textcommand.handler.api;

import org.springframework.stereotype.Component;
import ru.teamfour.textcommand.command.api.State;

import java.util.List;

@Component
public abstract class AbstractHandlersStateFactory implements HandlersStateFactory {

    private final List<HandlersState> handlersList;

    protected AbstractHandlersStateFactory(List<HandlersState> handlersList) {
        this.handlersList = handlersList;
    }
    @Override
    public HandlersState getHandlers(State state) {
        for (HandlersState handlers : handlersList) {
            if (handlers.isState(state)) {
                return handlers;
            }
        }
        return null; //todo поведение пока непонятно
    }

}

