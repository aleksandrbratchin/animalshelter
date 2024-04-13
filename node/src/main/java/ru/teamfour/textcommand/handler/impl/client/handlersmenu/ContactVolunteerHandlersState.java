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
public class ContactVolunteerHandlersState implements HandlersState {

    public final Handler mainMenuHandler;
    public final Handler contactVolunteersByPhoneNumberHandler;
    public final Handler contactVolunteersByNicknameHandler;
    public final Handler contactVolunteerStartChatHandler;

    public ContactVolunteerHandlersState(
            @Qualifier("mainMenuHandler") Handler mainMenuHandler,
            @Qualifier("contactVolunteersByPhoneNumberHandler") Handler contactVolunteersByPhoneNumberHandler,
            @Qualifier("contactVolunteersByNicknameHandler") Handler contactVolunteersByNicknameHandler,
            @Qualifier("contactVolunteerStartChatHandler") Handler contactVolunteerStartChatHandler) {
        this.mainMenuHandler = mainMenuHandler;
        this.contactVolunteersByPhoneNumberHandler = contactVolunteersByPhoneNumberHandler;
        this.contactVolunteersByNicknameHandler = contactVolunteersByNicknameHandler;
        this.contactVolunteerStartChatHandler = contactVolunteerStartChatHandler;
    }

    @Override
    public Handler getHandler() {
        mainMenuHandler.setNext(contactVolunteersByPhoneNumberHandler);
        contactVolunteersByPhoneNumberHandler.setNext(contactVolunteersByNicknameHandler);
        contactVolunteersByNicknameHandler.setNext(contactVolunteerStartChatHandler);
        return mainMenuHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.CONTACT_VOLUNTEER_MENU;
    }
}
