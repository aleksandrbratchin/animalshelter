package ru.teamfour.textcommand.handler.impl.volunteer.handlersMenu;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.handler.annotations.RoleUserQualifier;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.HandlersState;

@Component
@RoleUserQualifier(RoleUser.VOLUNTEER)
public class StartVolunteerMenuState implements HandlersState {

    public final Handler startHandler;

    public StartVolunteerMenuState(
            @Qualifier("startVolunteerHandler") Handler startHandler) {
        this.startHandler = startHandler;
    }

    @Override
    public Handler getHandler() {
        return startHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.VOLUNTEER_START_MENU;
    }
}
