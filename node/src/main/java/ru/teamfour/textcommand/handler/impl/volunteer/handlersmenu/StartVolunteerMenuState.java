package ru.teamfour.textcommand.handler.impl.volunteer.handlersmenu;

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
    public final Handler checkReportHandler;

    public StartVolunteerMenuState(
            @Qualifier("startVolunteerHandler") Handler startHandler,
            @Qualifier("checkReportHandler") Handler checkReportHandler) {
        this.startHandler = startHandler;
        this.checkReportHandler = checkReportHandler;
    }

    @Override
    public Handler getHandler() {
        startHandler.setNext(checkReportHandler);
        return startHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.VOLUNTEER_START_MENU;
    }
}
