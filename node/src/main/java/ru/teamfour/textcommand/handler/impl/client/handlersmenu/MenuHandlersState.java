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
public class MenuHandlersState implements HandlersState {

    public final Handler mainMenuHandler;
    public final Handler initHandler;
    public final Handler shelterInformationHandler;
    public final Handler petReportHandler;
    public final Handler adoptionHandler;
    public final Handler volunteerHandler;
    public final Handler becomeVolunteerHandler;

    public MenuHandlersState(
            @Qualifier("mainMenuHandler") Handler mainMenuHandler,
            @Qualifier("initHandler") Handler initHandler,
            @Qualifier("shelterInformationHandler") Handler shelterInformationHandler,
            @Qualifier("petReportHandler") Handler petReportHandler,
            @Qualifier("adoptionHandler") Handler adoptionHandler,
            @Qualifier("volunteerHandler") Handler volunteerHandler,
            @Qualifier("becomeVolunteerHandler") Handler becomeVolunteerHandler) {
        this.mainMenuHandler = mainMenuHandler;
        this.initHandler = initHandler;
        this.shelterInformationHandler = shelterInformationHandler;
        this.petReportHandler = petReportHandler;
        this.adoptionHandler = adoptionHandler;
        this.volunteerHandler = volunteerHandler;
        this.becomeVolunteerHandler = becomeVolunteerHandler;
    }

    @Override
    public Handler getHandler() {
        mainMenuHandler.setNext(shelterInformationHandler);
        shelterInformationHandler.setNext(petReportHandler);
        petReportHandler.setNext(adoptionHandler);
        adoptionHandler.setNext(volunteerHandler);
        volunteerHandler.setNext(becomeVolunteerHandler);
        becomeVolunteerHandler.setNext(initHandler);
        return mainMenuHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.MAIN_MENU;
    }
}
