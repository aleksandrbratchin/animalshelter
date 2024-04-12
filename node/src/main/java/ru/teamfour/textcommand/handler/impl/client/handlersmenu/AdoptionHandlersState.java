package ru.teamfour.textcommand.handler.impl.client.handlersmenu;

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
    public final Handler rulesAnimalsHandler;
    public final Handler listDocumentsHandler;
    public final Handler recommendationsHandler;
    public final Handler volunteerHandler;
    public final Handler backToMainMenuHandler;
    public final Handler reasonsForRefusalOfAdoptionHandler;
    public final Handler leaveContactDetailsForCommunicationHandler;

    public AdoptionHandlersState(
            @Qualifier("mainMenuHandler") Handler mainMenuHandler,
            @Qualifier("listAnimalsHandler") Handler listAnimalsHandler,
            @Qualifier("rulesAnimalsHandler") Handler rulesAnimalsHandler,
            @Qualifier("listDocumentsHandler") Handler listDocumentsHandler,
            @Qualifier("recommendationsHandler") Handler recommendationsHandler,
            @Qualifier("backToMainMenuHandler") Handler backToMainMenuHandler,
            @Qualifier("reasonsForRefusalOfAdoptionHandler") Handler reasonsForRefusalOfAdoptionHandler,
            @Qualifier("leaveContactDetailsForCommunicationHandler") Handler leaveContactDetailsForCommunicationHandler,
            @Qualifier("volunteerHandler")Handler volunteerHandler) {
        this.mainMenuHandler = mainMenuHandler;
        this.listAnimalsHandler = listAnimalsHandler;
        this.rulesAnimalsHandler = rulesAnimalsHandler;
        this.listDocumentsHandler = listDocumentsHandler;
        this.recommendationsHandler = recommendationsHandler;
        this.volunteerHandler = volunteerHandler;
        this.backToMainMenuHandler = backToMainMenuHandler;
        this.reasonsForRefusalOfAdoptionHandler = reasonsForRefusalOfAdoptionHandler;
        this.leaveContactDetailsForCommunicationHandler = leaveContactDetailsForCommunicationHandler;
    }

    @Override
    public Handler getHandler() {
        mainMenuHandler.setNext(listAnimalsHandler);
        listAnimalsHandler.setNext(rulesAnimalsHandler);
        rulesAnimalsHandler.setNext(listDocumentsHandler);
        listDocumentsHandler.setNext(recommendationsHandler);
        recommendationsHandler.setNext(reasonsForRefusalOfAdoptionHandler);
        reasonsForRefusalOfAdoptionHandler.setNext(volunteerHandler);
        volunteerHandler.setNext(backToMainMenuHandler);
        backToMainMenuHandler.setNext(leaveContactDetailsForCommunicationHandler);
        return mainMenuHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.ADOPTION;
    }
}
