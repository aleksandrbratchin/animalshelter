package ru.teamfour.textcommand.handler.api;

import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.textcommand.command.api.State;

public interface HandlersStateFactory {
    HandlersState getHandlers(State state);
    boolean isRole(RoleUser roleUser);
}

