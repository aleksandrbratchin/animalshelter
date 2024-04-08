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

    public final Handler mainMenuHandler;
    public final Handler addressHandler;
    public final Handler workScheduleShelterHandler;
    public final Handler drivingDirectionsHandler;
    public final Handler safetyMeasuresInShelterHandler;
    public final Handler securityDataHandler;
    public final Handler volunteerHandler;
    public final Handler storyOfShelterHandler;
    public final Handler backToMainMenuHandler;


    public ShelterInformationHandlersState(
            @Qualifier("mainMenuHandler") Handler mainMenuHandler,
            @Qualifier("shelterAddressHandler") Handler addressHandler,
            @Qualifier("workScheduleShelterHandler") Handler workScheduleShelterHandler,
            @Qualifier("drivingDirectionsHandler") Handler drivingDirectionsHandler,
            @Qualifier("safetyMeasuresInShelterHandler") Handler safetyMeasuresInShelterHandler,
            @Qualifier("securityDataHandler") Handler securityDataHandler,
            @Qualifier("volunteerHandler") Handler volunteerHandler,
            @Qualifier("backToMainMenuHandler") Handler backToMainMenuHandler,
            @Qualifier("storyOfShelterHandler") Handler storyOfShelterHandler
    ) {
        this.mainMenuHandler = mainMenuHandler;
        this.addressHandler = addressHandler;
        this.workScheduleShelterHandler = workScheduleShelterHandler;
        this.drivingDirectionsHandler = drivingDirectionsHandler;
        this.safetyMeasuresInShelterHandler = safetyMeasuresInShelterHandler;
        this.securityDataHandler = securityDataHandler;
        this.volunteerHandler = volunteerHandler;
        this.storyOfShelterHandler = storyOfShelterHandler;
        this.backToMainMenuHandler = backToMainMenuHandler;
    }

    @Override
    public Handler getHandler() {
        mainMenuHandler.setNext(addressHandler);
        addressHandler.setNext(workScheduleShelterHandler);
        workScheduleShelterHandler.setNext(drivingDirectionsHandler);
        drivingDirectionsHandler.setNext(safetyMeasuresInShelterHandler);
        safetyMeasuresInShelterHandler.setNext(securityDataHandler);
        securityDataHandler.setNext(storyOfShelterHandler);
        storyOfShelterHandler.setNext(volunteerHandler);
        volunteerHandler.setNext(backToMainMenuHandler);
        return mainMenuHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.INFO_SHELTER;
    }
}
