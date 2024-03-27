package ru.teamfour.textcommand.handler.impl.handlers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.Handlers;

@Component
public class ShelterInformationHandlers implements Handlers {

    public final Handler startHandler;
    public final Handler addressHandler;
    public final Handler workScheduleShelterHandler;
    public final Handler drivingDirectionsHandler;
    public final Handler safetyMeasuresInShelterHandler;
    public final Handler securityDataHandler;
    public final Handler contactForCommunicationHandler;
    public final Handler volunteerHandler;

    public ShelterInformationHandlers(
            Handler startHandler,
            @Qualifier("shelterAddressHandler") Handler addressHandler,
            @Qualifier("workScheduleShelterHandler") Handler workScheduleShelterHandler,
            @Qualifier("drivingDirectionsHandler") Handler drivingDirectionsHandler,
            @Qualifier("safetyMeasuresInShelterHandler") Handler safetyMeasuresInShelterHandler,
            @Qualifier("securityDataHandler") Handler securityDataHandler,
            @Qualifier("contactForCommunicationHandler")Handler contactForCommunicationHandler,
            @Qualifier("volunteerHandler")Handler volunteerHandler) {
        this.startHandler = startHandler;
        this.addressHandler = addressHandler;
        this.workScheduleShelterHandler = workScheduleShelterHandler;
        this.drivingDirectionsHandler = drivingDirectionsHandler;
        this.safetyMeasuresInShelterHandler = safetyMeasuresInShelterHandler;
        this.securityDataHandler = securityDataHandler;
        this.contactForCommunicationHandler = contactForCommunicationHandler;
        this.volunteerHandler = volunteerHandler;
    }

    @Override
    public Handler getHandler() {
        startHandler.setNext(addressHandler);
        startHandler.setNext(workScheduleShelterHandler);
        startHandler.setNext(drivingDirectionsHandler);
        startHandler.setNext(safetyMeasuresInShelterHandler);
        startHandler.setNext(securityDataHandler);
        startHandler.setNext(contactForCommunicationHandler);
        startHandler.setNext(volunteerHandler);

        return startHandler;
    }

    @Override
    public boolean isHandlers(State state) {
        return state == State.INFO_SHELTER;
    }
}
