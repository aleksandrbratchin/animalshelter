package ru.teamfour.textcommand.handler.impl.client;

import org.springframework.stereotype.Component;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.textcommand.handler.api.AbstractHandlersStateFactory;
import ru.teamfour.textcommand.handler.api.HandlersState;

import java.util.List;

@Component
public class ClientHandlersMenuFactory extends AbstractHandlersStateFactory {

    public ClientHandlersMenuFactory(List<HandlersState> handlersList) {
        super(handlersList);
    }

    @Override
    public boolean isRole(RoleUser roleUser){
        return roleUser == RoleUser.CLIENT;
    }

}
