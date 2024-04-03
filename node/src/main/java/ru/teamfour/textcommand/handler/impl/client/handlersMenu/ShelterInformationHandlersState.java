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
public class ShelterInformationHandlersState implements HandlersState {

    public final Handler startHandler;
    public final Handler addressHandler;
    public final Handler workScheduleShelterHandler;
    public final Handler drivingDirectionsHandler;
    public final Handler safetyMeasuresInShelterHandler;
    public final Handler securityDataHandler;
    public final Handler volunteerHandler;
    public final Handler storyOfShelterHandler;


    public ShelterInformationHandlersState(
            @Qualifier("startHandler") Handler startHandler,
            @Qualifier("shelterAddressHandler") Handler addressHandler,
            @Qualifier("workScheduleShelterHandler") Handler workScheduleShelterHandler,
            @Qualifier("drivingDirectionsHandler") Handler drivingDirectionsHandler,
            @Qualifier("safetyMeasuresInShelterHandler") Handler safetyMeasuresInShelterHandler,
            @Qualifier("securityDataHandler") Handler securityDataHandler,
            @Qualifier("volunteerHandler") Handler volunteerHandler,
            @Qualifier("storyOfShelterHandler") Handler storyOfShelterHandler
    ) {
        this.startHandler = startHandler;
        this.addressHandler = addressHandler;
        this.workScheduleShelterHandler = workScheduleShelterHandler;
        this.drivingDirectionsHandler = drivingDirectionsHandler;
        this.safetyMeasuresInShelterHandler = safetyMeasuresInShelterHandler;
        this.securityDataHandler = securityDataHandler;
        this.volunteerHandler = volunteerHandler;
        this.storyOfShelterHandler = storyOfShelterHandler;
    }

    @Override
    public Handler getHandler() {
        startHandler.setNext(addressHandler);
        addressHandler.setNext(workScheduleShelterHandler);
        workScheduleShelterHandler.setNext(drivingDirectionsHandler);
        drivingDirectionsHandler.setNext(safetyMeasuresInShelterHandler);
        safetyMeasuresInShelterHandler.setNext(securityDataHandler);
        securityDataHandler.setNext(storyOfShelterHandler);
        storyOfShelterHandler.setNext(volunteerHandler);
        return startHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.INFO_SHELTER;
    }
}
