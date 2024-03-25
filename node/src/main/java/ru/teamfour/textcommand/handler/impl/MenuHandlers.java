package ru.teamfour.textcommand.handler.impl;

import org.springframework.stereotype.Component;
import ru.teamfour.textcommand.handler.api.Handler;

@Component
public class MenuHandlers  {

    public final Handler startHandler;
    public final Handler infoAnimalShelterHandler;

    public MenuHandlers(StartHandler startHandler, InfoAnimalShelterHandler infoAnimalShelterHandler) {
        this.startHandler = startHandler;
        this.infoAnimalShelterHandler = infoAnimalShelterHandler;
    }

    public Handler getHandler(){
         startHandler.setNext(infoAnimalShelterHandler);
         return startHandler;
    }

}
