package ru.teamfour.textcommand.handler.api;

import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.textcommand.command.api.State;

/**
 * Фабрика которая получает меню (цепочку из доступных команд) {@link HandlersState} в зависимости от {@link State}
 */
public interface HandlersStateFactory {

    HandlersState getHandlers(State state);

    boolean isRole(RoleUser roleUser);
}

