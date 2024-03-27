package ru.teamfour.textcommand.handler.api;

import ru.teamfour.textcommand.command.api.State;

public interface HandlersState {
    Handler getHandler();

    boolean isState(State state);
}
