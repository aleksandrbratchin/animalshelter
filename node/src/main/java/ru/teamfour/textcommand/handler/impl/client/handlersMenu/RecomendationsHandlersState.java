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
public class RecomendationsHandlersState implements HandlersState {

    public final Handler mainMenuHandler;
    public final Handler transportationHandler;
    public final Handler homeImprovementForPuppyHandler;
    public final Handler homeImprovementForAdultAnimalHandler;
    public final Handler homeImprovementForAnimalWithDisabilitiesHandler;
    public final Handler tipsFromDogHandlerHandler;
    public final Handler backToMainMenuHandler;
    public final Handler listDogHandlersHandler;
    public  RecomendationsHandlersState(
            @Qualifier("mainMenuHandler") Handler mainMenuHandler,
            @Qualifier("transportationHandler") Handler transportationHandler,
            @Qualifier("homeImprovementForPuppyHandler") Handler homeImprovementForPuppyHandler,
            @Qualifier("homeImprovementForAdultAnimalHandler") Handler homeImprovementForAdultAnimalHandler,
            @Qualifier("homeImprovementForAnimalWithDisabilitiesHandler") Handler homeImprovementForAnimalWithDisabilitiesHandler,
            @Qualifier("backToMainMenuHandler") Handler backToMainMenuHandler,
            @Qualifier("tipsFromDogHandlerHandler")Handler tipsFromDogHandlerHandler, 
            @Qualifier("listDogHandlersHandler")Handler listDogHandlersHandler) {
        this.mainMenuHandler = mainMenuHandler;
        this.transportationHandler =transportationHandler;
        this.homeImprovementForPuppyHandler = homeImprovementForPuppyHandler;
        this.homeImprovementForAdultAnimalHandler = homeImprovementForAdultAnimalHandler;
        this.homeImprovementForAnimalWithDisabilitiesHandler = homeImprovementForAnimalWithDisabilitiesHandler;
        this.tipsFromDogHandlerHandler = tipsFromDogHandlerHandler;
        this.backToMainMenuHandler = backToMainMenuHandler;
        this.listDogHandlersHandler = listDogHandlersHandler;
    }

    @Override
    public Handler getHandler() {
        mainMenuHandler.setNext(transportationHandler);
        transportationHandler.setNext(homeImprovementForPuppyHandler);
        homeImprovementForPuppyHandler.setNext(homeImprovementForAdultAnimalHandler);
        homeImprovementForAdultAnimalHandler.setNext(homeImprovementForAnimalWithDisabilitiesHandler);
        homeImprovementForAnimalWithDisabilitiesHandler.setNext(tipsFromDogHandlerHandler);
        tipsFromDogHandlerHandler.setNext(listDogHandlersHandler);
        listDogHandlersHandler.setNext(backToMainMenuHandler);
        return mainMenuHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.RECOMMENDATIONS;
    }
}
