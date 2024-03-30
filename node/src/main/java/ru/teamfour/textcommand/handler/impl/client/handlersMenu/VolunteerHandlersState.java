package ru.teamfour.textcommand.handler.impl.client.handlersMenu;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.HandlersState;

@Component
public class VolunteerHandlersState implements HandlersState {

    public final Handler startHandler;
    public final Handler contactVolunteersByPhoneNumberHandler;
    public final Handler contactVolunteersByNicknameHandler;

    public VolunteerHandlersState(
            @Qualifier("startHandler") Handler startHandler,
            @Qualifier("contactVolunteersByPhoneNumberHandler") Handler contactVolunteersByPhoneNumberHandler,
            @Qualifier("contactVolunteersByNicknameHandler") Handler contactVolunteersByNicknameHandler) {
        this.startHandler = startHandler;
        this.contactVolunteersByPhoneNumberHandler = contactVolunteersByPhoneNumberHandler;
        this.contactVolunteersByNicknameHandler = contactVolunteersByNicknameHandler;
    }

    @Override
    public Handler getHandler() {
        startHandler.setNext(contactVolunteersByPhoneNumberHandler);
        contactVolunteersByPhoneNumberHandler.setNext(contactVolunteersByNicknameHandler);
        return startHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.VOLUNTEER_MENU;
    }
}
