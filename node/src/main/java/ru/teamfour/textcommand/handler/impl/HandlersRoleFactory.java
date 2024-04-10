package ru.teamfour.textcommand.handler.impl;

import org.springframework.stereotype.Component;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.textcommand.handler.api.HandlersStateFactory;

import java.util.List;

/**
 * Фабрика которая получает доступные меню {@link HandlersStateFactory}  для пользователя в зависимости от его роли {@link RoleUser}
 */
@Component
public class HandlersRoleFactory {

    private final List<HandlersStateFactory> handlersStateFactories;

    public HandlersRoleFactory(List<HandlersStateFactory> handlersStateFactories) {
        this.handlersStateFactories = handlersStateFactories;
    }

    public HandlersStateFactory getHandlers(RoleUser roleUser) {
        for (HandlersStateFactory stateFactory : handlersStateFactories) {
            if (stateFactory.isRole(roleUser)) {
                return stateFactory;
            }
        }
        return null; //todo поведение пока непонятно
    }
}
