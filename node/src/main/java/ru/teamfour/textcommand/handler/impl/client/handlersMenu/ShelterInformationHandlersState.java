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
    public final Handler workScheduleShelterHandler;
    public final Handler drivingDirectionsHandler;
    public final Handler safetyMeasuresInShelterHandler;
    public final Handler securityDataHandler;
    public final Handler contactForCommunicationHandler;
    public final Handler volunteerHandler;
    public ShelterInformationHandlersState(
            @Qualifier("startHandler") Handler startHandler,
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
        addressHandler.setNext(workScheduleShelterHandler);
        workScheduleShelterHandler.setNext(drivingDirectionsHandler);
        drivingDirectionsHandler.setNext(safetyMeasuresInShelterHandler);
        safetyMeasuresInShelterHandler.setNext(securityDataHandler);
        securityDataHandler.setNext(contactForCommunicationHandler);
        contactForCommunicationHandler.setNext(volunteerHandler);
        return startHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.INFO_SHELTER;
    }
}
