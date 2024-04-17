package ru.teamfour.textcommand.handler.impl.client.handlersmenu;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.handler.annotations.RoleUserQualifier;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.HandlersState;
/***
 * Обработка меню "Прислать отчет о питомце"
 */
@Component
@RoleUserQualifier(RoleUser.CLIENT)
public class PetReportHandlersState implements HandlersState {

    public final Handler mainMenuHandler;
    public final Handler sendPhotoHandler;
    public final Handler sendTextHandler;
    public final Handler volunteerHandler;
    public final Handler backToMainMenuHandler;

    public PetReportHandlersState(
            @Qualifier("mainMenuHandler") Handler mainMenuHandler,
            @Qualifier("sendPhotoHandler") Handler sendPhotoHandler,
            @Qualifier("sendTextHandler") Handler sendTextHandler,
            @Qualifier("volunteerHandler") Handler volunteerHandler,
            @Qualifier("backToMainMenuHandler") Handler backToMainMenuHandler) {
        this.mainMenuHandler = mainMenuHandler;
        this.sendPhotoHandler = sendPhotoHandler;
        this.sendTextHandler = sendTextHandler;
        this.volunteerHandler = volunteerHandler;
        this.backToMainMenuHandler = backToMainMenuHandler;
    }

    /***
     * Кнопка меню позвать валантера
     */

    @Override
    public Handler getHandler() {
        mainMenuHandler.setNext(sendPhotoHandler);
        sendPhotoHandler.setNext(sendTextHandler);
        sendTextHandler.setNext(volunteerHandler);
        volunteerHandler.setNext(backToMainMenuHandler);
        return mainMenuHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.PET_REPORT;
    }
}
