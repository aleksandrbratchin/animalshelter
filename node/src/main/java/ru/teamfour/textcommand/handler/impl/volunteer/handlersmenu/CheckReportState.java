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
public class CheckReportState implements HandlersState {

    public final Handler backToVolunteerMainMenuHandler;
    public final Handler startVolunteerHandler;

    public CheckReportState(
            @Qualifier("backToVolunteerMainMenuHandler") Handler backToVolunteerMainMenuHandler,
            @Qualifier("startVolunteerHandler") Handler startVolunteerHandler//,
            //@Qualifier("talkWithClientHandler") Handler talkWithVolunteerHandler
    ) {
        this.startVolunteerHandler = startVolunteerHandler;
        this.backToVolunteerMainMenuHandler = backToVolunteerMainMenuHandler;
    }

    @Override
    public Handler getHandler() {
        backToVolunteerMainMenuHandler.setNext(startVolunteerHandler);
        return backToVolunteerMainMenuHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.CHECK_REPORT;
    }
}
