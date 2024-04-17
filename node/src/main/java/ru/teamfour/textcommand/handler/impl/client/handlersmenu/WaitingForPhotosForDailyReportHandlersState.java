package ru.teamfour.textcommand.handler.impl.client.handlersmenu;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.handler.annotations.RoleUserQualifier;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.HandlersState;

/***
 * Меню сохранения фото для ежедневного отчета
 */
@Component
@RoleUserQualifier(RoleUser.CLIENT)
public class WaitingForPhotosForDailyReportHandlersState implements HandlersState {

    public final Handler mainMenuHandler;
    public final Handler backToPetReportMenuHandler;


    public WaitingForPhotosForDailyReportHandlersState(
            @Qualifier("mainMenuHandler") Handler mainMenuHandler,
            @Qualifier("backToPetReportMenuHandler") Handler backToPetReportMenuHandler

    ) {
        this.mainMenuHandler = mainMenuHandler;
        this.backToPetReportMenuHandler = backToPetReportMenuHandler;
    }

    @Override
    public Handler getHandler() {
        mainMenuHandler.setNext(backToPetReportMenuHandler);
        return mainMenuHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.WAITING_FOR_PHOTOS_FOR_DAILY_REPORT;
    }
}
