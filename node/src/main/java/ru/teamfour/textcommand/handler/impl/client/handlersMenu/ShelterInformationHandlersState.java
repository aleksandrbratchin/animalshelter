package ru.teamfour.textcommand.handler.impl.client.handlersMenu;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.HandlersState;

@Component
public class ShelterInformationHandlersState implements HandlersState {

    public final Handler startHandler;
    public final Handler addressHandler;

    public ShelterInformationHandlersState(
            @Qualifier("startHandler") Handler startHandler,
            @Qualifier("shelterAddressHandler") Handler addressHandler) {
        this.startHandler = startHandler;
        this.addressHandler = addressHandler;
    }

    @Override
    public Handler getHandler() {
        startHandler.setNext(addressHandler);
        return startHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.INFO_SHELTER;
    }
}
