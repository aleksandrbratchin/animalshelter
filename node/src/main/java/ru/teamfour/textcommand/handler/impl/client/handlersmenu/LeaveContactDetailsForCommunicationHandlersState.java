package ru.teamfour.textcommand.handler.impl.client.handlersmenu;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.handler.annotations.RoleUserQualifier;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.HandlersState;

/***
 * Меню изменения номера телефона "Контактных данных для связи"
 */
@Component
@RoleUserQualifier(RoleUser.CLIENT)
public class LeaveContactDetailsForCommunicationHandlersState implements HandlersState {

    public final Handler mainMenuHandler;
    public final Handler backToMainMenuHandler;
    public final Handler changeContactDetailsHandler;


    public LeaveContactDetailsForCommunicationHandlersState(
            @Qualifier("mainMenuHandler") Handler mainMenuHandler,
            @Qualifier("changeContactDetailsHandler") Handler changeContactDetailsHandler,
            @Qualifier("backToMainMenuHandler") Handler backToMainMenuHandler
    ) {
        this.mainMenuHandler = mainMenuHandler;
        this.backToMainMenuHandler = backToMainMenuHandler;
        this.changeContactDetailsHandler = changeContactDetailsHandler;
    }

    @Override
    public Handler getHandler() {
        mainMenuHandler.setNext(backToMainMenuHandler);
        backToMainMenuHandler.setNext(changeContactDetailsHandler);
        return mainMenuHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.LEAVE_CONTACT_DETAILS_FOR_COMMUNICATION_MENU;
    }
}
