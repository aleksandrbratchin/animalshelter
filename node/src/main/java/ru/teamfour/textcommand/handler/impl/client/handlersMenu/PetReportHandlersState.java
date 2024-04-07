package ru.teamfour.textcommand.handler.impl.client.handlersMenu;

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

    public final Handler startHandler;
    public final Handler sendPhotoHandler;
    public final Handler sendTextHandler;
    public final Handler volunteerHandler;

    public PetReportHandlersState(
            @Qualifier("startHandler") Handler startHandler,
            @Qualifier("sendPhotoHandler") Handler sendPhotoHandler,
            @Qualifier("sendTextHandler") Handler sendTextHandler,
            @Qualifier("volunteerHandler") Handler volunteerHandler) {
        this.startHandler = startHandler;
        this.sendPhotoHandler = sendPhotoHandler;
        this.sendTextHandler = sendTextHandler;
        this.volunteerHandler = volunteerHandler;
    }

    /***
     * Кнопка меню позвать валантера
     */

    @Override
    public Handler getHandler() {
        startHandler.setNext(sendPhotoHandler);
        sendPhotoHandler.setNext(sendTextHandler);
        sendTextHandler.setNext(volunteerHandler);
        return startHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.PET_REPORT;
    }
}
