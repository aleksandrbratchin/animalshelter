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
    public final Handler homeImprovementForYoungAnimalHandler;
    public final Handler homeImprovementForAdultAnimalHandler;
    public final Handler homeImprovementForAnimalWithDisabilitiesHandler;
    public final Handler tipsFromSpecialistHandler;
    public final Handler backToAdoptionHandler;
    public final Handler listSpecialistsHandler;
    public RecommendationsHandlersState(
            @Qualifier("adoptionHandler") Handler adoptionHandler,
            @Qualifier("transportationHandler") Handler transportationHandler,
            @Qualifier("homeImprovementForYoungAnimalHandler") Handler homeImprovementForYoungAnimalHandler,
            @Qualifier("homeImprovementForAdultAnimalHandler") Handler homeImprovementForAdultAnimalHandler,
            @Qualifier("homeImprovementForAnimalWithDisabilitiesHandler") Handler homeImprovementForAnimalWithDisabilitiesHandler,
            @Qualifier("backToAdoptionHandler") Handler backToAdoptionHandler,
            @Qualifier("tipsFromSpecialistHandler")Handler tipsFromSpecialistHandler,
            @Qualifier("listSpecialistsHandler")Handler listSpecialistsHandler) {
        this.adoptionHandler = adoptionHandler;
        this.transportationHandler =transportationHandler;
        this.homeImprovementForYoungAnimalHandler = homeImprovementForYoungAnimalHandler;
        this.homeImprovementForAdultAnimalHandler = homeImprovementForAdultAnimalHandler;
        this.homeImprovementForAnimalWithDisabilitiesHandler = homeImprovementForAnimalWithDisabilitiesHandler;
        this.tipsFromSpecialistHandler = tipsFromSpecialistHandler;
        this.backToAdoptionHandler = backToAdoptionHandler;
        this.listSpecialistsHandler = listSpecialistsHandler;
    }

    @Override
    public Handler getHandler() {
        adoptionHandler.setNext(transportationHandler);
        transportationHandler.setNext(homeImprovementForYoungAnimalHandler);
        homeImprovementForYoungAnimalHandler.setNext(homeImprovementForAdultAnimalHandler);
        homeImprovementForAdultAnimalHandler.setNext(homeImprovementForAnimalWithDisabilitiesHandler);
        homeImprovementForAnimalWithDisabilitiesHandler.setNext(tipsFromSpecialistHandler);
        tipsFromSpecialistHandler.setNext(listSpecialistsHandler);
        listSpecialistsHandler.setNext(backToAdoptionHandler);
        return adoptionHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.RECOMMENDATIONS;
    }
}
