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
public class AdoptionHandlersState implements HandlersState {

    public final Handler mainMenuHandler;
    public final Handler listAnimalsHandler;
    public final Handler rulesAnimalHandler;
    public final Handler listDocumentsHandler;
    public final Handler recommendationsHandler;
    public final Handler volunteerHandler;
    public final Handler backToMainMenuHandler;
    public final Handler reasonsForRefusalOfAdoptionHandler;

    public AdoptionHandlersState(
            @Qualifier("mainMenuHandler") Handler mainMenuHandler,
            @Qualifier("listAnimalsHandler") Handler listAnimalsHandler,
            @Qualifier("rulesAnimalHandler") Handler rulesAnimalHandler,
            @Qualifier("listDocumentsHandler") Handler listDocumentsHandler,
            @Qualifier("recommendationsHandler") Handler recommendationsHandler,
            @Qualifier("backToMainMenuHandler") Handler backToMainMenuHandler,
            @Qualifier("volunteerHandler") Handler volunteerHandler,
            @Qualifier("reasonsForRefusalOfAdoptionHandler") Handler reasonsForRefusalOfAdoptionHandler) {
        this.mainMenuHandler = mainMenuHandler;
        this.listAnimalsHandler = listAnimalsHandler;
        this.rulesAnimalHandler = rulesAnimalHandler;
        this.listDocumentsHandler = listDocumentsHandler;
        this.recommendationsHandler = recommendationsHandler;
        this.volunteerHandler = volunteerHandler;
        this.backToMainMenuHandler = backToMainMenuHandler;
        this.reasonsForRefusalOfAdoptionHandler = reasonsForRefusalOfAdoptionHandler;
    }

    @Override
    public Handler getHandler() {
        mainMenuHandler.setNext(listAnimalsHandler);
        listAnimalsHandler.setNext(rulesAnimalHandler);
        rulesAnimalHandler.setNext(listDocumentsHandler);
        listDocumentsHandler.setNext(recommendationsHandler);
        recommendationsHandler.setNext(reasonsForRefusalOfAdoptionHandler);
        reasonsForRefusalOfAdoptionHandler.setNext(volunteerHandler);
        volunteerHandler.setNext(backToMainMenuHandler);
        return mainMenuHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.ADOPTION;
    }
}
