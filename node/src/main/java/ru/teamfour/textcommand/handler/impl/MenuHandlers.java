package ru.teamfour.textcommand.handler.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.Handlers;

@Component
public class MenuHandlers implements Handlers {

    public final Handler startHandler;
    public final Handler shelterInformationHandler;
    public final Handler petReportHandler;
    public final Handler adoptionHandler;
    public final Handler volunteerHandler;

    public MenuHandlers(
            @Qualifier("startHandler") Handler startHandler,
            @Qualifier("shelterInformationHandler") Handler shelterInformationHandler,
            @Qualifier("petReportHandler") Handler petReportHandler,
            @Qualifier("adoptionHandler") Handler adoptionHandler,
            @Qualifier("volunteerHandler") Handler volunteerHandler) {
        this.startHandler = startHandler;
        this.shelterInformationHandler = shelterInformationHandler;
        this.petReportHandler = petReportHandler;
        this.adoptionHandler = adoptionHandler;
        this.volunteerHandler = volunteerHandler;
    }

    @Override
    public Handler getHandler() {
        startHandler.setNext(shelterInformationHandler);
        shelterInformationHandler.setNext(petReportHandler);
        petReportHandler.setNext(adoptionHandler);
        adoptionHandler.setNext(volunteerHandler);
        return startHandler;
    }

    @Override
    public boolean isHandlers(State state) {
        return state == State.MAIN_MENU;
    }
}
