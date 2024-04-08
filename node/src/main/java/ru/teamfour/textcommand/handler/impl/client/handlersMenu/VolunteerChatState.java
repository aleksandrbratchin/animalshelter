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
public class VolunteerChatState implements HandlersState {

    public final Handler mainMenuHandler;
    public final Handler endChatVolunteerHandler;
    public final Handler talkWithVolunteerHandler;

    public VolunteerChatState(
            @Qualifier("mainMenuHandler") Handler mainMenuHandler,
            @Qualifier("endChatVolunteerHandler") Handler endChatVolunteerHandler,
            @Qualifier("talkWithVolunteerHandler") Handler talkWithVolunteerHandler) {
        this.mainMenuHandler = mainMenuHandler;
        this.endChatVolunteerHandler = endChatVolunteerHandler;
        this.talkWithVolunteerHandler = talkWithVolunteerHandler;
    }

    @Override
    public Handler getHandler() {
        mainMenuHandler.setNext(endChatVolunteerHandler);
        endChatVolunteerHandler.setNext(talkWithVolunteerHandler);
        return mainMenuHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.VOLUNTEER_CHAT;
    }
}
