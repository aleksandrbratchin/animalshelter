package ru.teamfour.textcommand.handler.impl.client.handlersmenu;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.handler.annotations.RoleUserQualifier;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.HandlersState;

/***
 * Меню сохранения текста для ежедневного отчета
 */
@Component
@RoleUserQualifier(RoleUser.CLIENT)
public class WaitingTextForDailyReportHandlersState implements HandlersState {

    public final Handler mainMenuHandler;
    public final Handler backToPetReportMenuHandler;
    public final Handler saveTextDailyReportHandler;


    public WaitingTextForDailyReportHandlersState(
            @Qualifier("mainMenuHandler") Handler mainMenuHandler,
            @Qualifier("saveTextDailyReportHandler") Handler saveTextDailyReportHandler,
            @Qualifier("backToPetReportMenuHandler") Handler backToPetReportMenuHandler

    ) {
        this.mainMenuHandler = mainMenuHandler;
        this.backToPetReportMenuHandler = backToPetReportMenuHandler;
        this.saveTextDailyReportHandler = saveTextDailyReportHandler;
    }

    @Override
    public Handler getHandler() {
        mainMenuHandler.setNext(backToPetReportMenuHandler);
        backToPetReportMenuHandler.setNext(saveTextDailyReportHandler);
        return mainMenuHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.WAITING_TEXT_FOR_DAILY_REPORT;
    }
}
