package ru.teamfour.textcommand.handler.api;

import ru.teamfour.textcommand.command.api.State;

public interface Handlers {
    Handler getHandler();

    boolean isHandlers(State state);
}
