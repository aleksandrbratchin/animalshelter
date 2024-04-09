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
public class RecommendationsHandlersState implements HandlersState {

    public final Handler adoptionHandler;
    public final Handler transportationHandler;
    public final Handler homeImprovementForPuppyHandler;
    public final Handler homeImprovementForAdultAnimalHandler;
    public final Handler homeImprovementForAnimalWithDisabilitiesHandler;
    public final Handler tipsFromDogHandlerHandler;
    //public final Handler backToAdoptionHandler;
    public final Handler listDogHandlersHandler;
    public RecommendationsHandlersState(
            @Qualifier("adoptionHandler") Handler adoptionHandler,
            @Qualifier("transportationHandler") Handler transportationHandler,
            @Qualifier("homeImprovementForPuppyHandler") Handler homeImprovementForPuppyHandler,
            @Qualifier("homeImprovementForAdultAnimalHandler") Handler homeImprovementForAdultAnimalHandler,
            @Qualifier("homeImprovementForAnimalWithDisabilitiesHandler") Handler homeImprovementForAnimalWithDisabilitiesHandler,
           // @Qualifier("backToAdoptionHandler") Handler backToAdoptionHandler,
            @Qualifier("tipsFromDogHandlerHandler")Handler tipsFromDogHandlerHandler, 
            @Qualifier("listDogHandlersHandler")Handler listDogHandlersHandler) {
        this.adoptionHandler = adoptionHandler;
        this.transportationHandler =transportationHandler;
        this.homeImprovementForPuppyHandler = homeImprovementForPuppyHandler;
        this.homeImprovementForAdultAnimalHandler = homeImprovementForAdultAnimalHandler;
        this.homeImprovementForAnimalWithDisabilitiesHandler = homeImprovementForAnimalWithDisabilitiesHandler;
        this.tipsFromDogHandlerHandler = tipsFromDogHandlerHandler;
       // this.backToAdoptionHandler = backToAdoptionHandler;
        this.listDogHandlersHandler = listDogHandlersHandler;
    }

    @Override
    public Handler getHandler() {
        adoptionHandler.setNext(transportationHandler);
        transportationHandler.setNext(homeImprovementForPuppyHandler);
        homeImprovementForPuppyHandler.setNext(homeImprovementForAdultAnimalHandler);
        homeImprovementForAdultAnimalHandler.setNext(homeImprovementForAnimalWithDisabilitiesHandler);
        homeImprovementForAnimalWithDisabilitiesHandler.setNext(tipsFromDogHandlerHandler);
        tipsFromDogHandlerHandler.setNext(listDogHandlersHandler);
        return adoptionHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.RECOMMENDATIONS;
    }
}
