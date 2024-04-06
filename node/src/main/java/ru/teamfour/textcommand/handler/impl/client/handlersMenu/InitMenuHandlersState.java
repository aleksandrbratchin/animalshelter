package ru.teamfour.textcommand.handler.impl.client.handlersMenu;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.handler.annotations.RoleUserQualifier;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.HandlersState;

@Component
@RoleUserQualifier(RoleUser.CLIENT)
public class InitMenuHandlersState implements HandlersState {

    public final Handler initHandler;
    public final Handler chooseShelterHandler;

    public InitMenuHandlersState(
            @Qualifier("initHandler") Handler initHandler,
            @Qualifier("chooseShelterHandler") Handler chooseShelterHandler) {
        this.initHandler = initHandler;
        this.chooseShelterHandler = chooseShelterHandler;
    }

    @Override
    public Handler getHandler() {
        initHandler.setNext(chooseShelterHandler);
        return initHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.INIT_MENU;
    }
}
