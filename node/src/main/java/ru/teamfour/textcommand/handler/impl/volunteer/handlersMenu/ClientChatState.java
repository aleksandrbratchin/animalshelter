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
public class ClientChatState implements HandlersState {

    public final Handler startHandler;
    public final Handler contactVolunteerEndChatHandler;
    public final Handler talkWithVolunteerHandler;

    public ClientChatState(
            @Qualifier("startVolunteerHandler") Handler startHandler,
            @Qualifier("endChatClientHandler") Handler contactVolunteerEndChatHandler,
            @Qualifier("talkWithClientHandler") Handler talkWithVolunteerHandler) {
        this.startHandler = startHandler;
        this.contactVolunteerEndChatHandler = contactVolunteerEndChatHandler;
        this.talkWithVolunteerHandler = talkWithVolunteerHandler;
    }

    @Override
    public Handler getHandler() {
        startHandler.setNext(contactVolunteerEndChatHandler);
        contactVolunteerEndChatHandler.setNext(talkWithVolunteerHandler);
        return startHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.VOLUNTEER_CHAT;
    }
}
