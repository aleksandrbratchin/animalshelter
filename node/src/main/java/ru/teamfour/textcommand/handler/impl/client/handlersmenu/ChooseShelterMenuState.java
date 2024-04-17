package ru.teamfour.textcommand.handler.impl.client.handlersmenu;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.handler.annotations.RoleUserQualifier;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.HandlersState;

@Component
@RoleUserQualifier(RoleUser.CLIENT)
public class ChooseShelterMenuState implements HandlersState {

    public final Handler initHandler;
    public final Handler chooseShelterHandler;
    public final Handler backToTypeAnimalMenuHandler;

    public ChooseShelterMenuState(
            @Qualifier("initHandler") Handler initHandler,
            @Qualifier("chooseShelterHandler") Handler chooseShelterHandler,
            @Qualifier("backToTypeAnimalMenuHandler") Handler backToTypeAnimalMenuHandler) {
        this.initHandler = initHandler;
        this.chooseShelterHandler = chooseShelterHandler;
        this.backToTypeAnimalMenuHandler = backToTypeAnimalMenuHandler;
    }

    @Override
    public Handler getHandler() {
        initHandler.setNext(chooseShelterHandler);
        chooseShelterHandler.setNext(backToTypeAnimalMenuHandler);
        return initHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.CHOOSE_SHELTER_MENU;
    }
}
