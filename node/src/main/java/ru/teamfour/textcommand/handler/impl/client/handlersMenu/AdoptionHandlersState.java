package ru.teamfour.textcommand.handler.impl.client.handlersMenu;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.HandlersState;

@Component
public class AdoptionHandlersState implements HandlersState {

    public final Handler startHandler;
    public final Handler listAnimalsHandler;
    public final Handler rulesAnimalHandler;
    public final Handler listDocumentsHandler;
    public final Handler recommendationsHandler;
    public final Handler volunteerHandler;
    public AdoptionHandlersState(
            @Qualifier("startHandler") Handler startHandler,
            @Qualifier("listAnimalsHandler") Handler listAnimalsHandler,
            @Qualifier("rulesAnimalHandler") Handler rulesAnimalHandler,
            @Qualifier("listDocumentsHandler") Handler listDocumentsHandler,
            @Qualifier("recommendationsHandler") Handler recommendationsHandler,
            @Qualifier("volunteerHandler")Handler volunteerHandler) {
        this.startHandler = startHandler;
        this.listAnimalsHandler = listAnimalsHandler;
        this.rulesAnimalHandler = rulesAnimalHandler;
        this.listDocumentsHandler = listDocumentsHandler;
        this.recommendationsHandler = recommendationsHandler;
        this.volunteerHandler = volunteerHandler;
    }

    @Override
    public Handler getHandler() {
        startHandler.setNext(listAnimalsHandler);
        listAnimalsHandler.setNext(rulesAnimalHandler);
        rulesAnimalHandler.setNext(listDocumentsHandler);
        listDocumentsHandler.setNext(recommendationsHandler);
        recommendationsHandler.setNext(volunteerHandler);
        return startHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.ADOPTION;
    }
}
