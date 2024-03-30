package ru.teamfour.textcommand.handler.impl.client.handlersMenu;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.HandlersState;

@Component
public class PetReportHandlersState implements HandlersState {

    public final Handler startHandler;
    public final Handler volunteerHandler;

    public PetReportHandlersState(
            @Qualifier("startHandler") Handler startHandler,
            @Qualifier("volunteerHandler") Handler volunteerHandler) {
        this.startHandler = startHandler;
        this.volunteerHandler = volunteerHandler;
    }

    @Override
    public Handler getHandler() {
        startHandler.setNext(volunteerHandler);
        return startHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.VOLUNTEER_MENU;
    }
}
