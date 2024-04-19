package ru.teamfour.textcommand.handler.impl.client.handlersmenu;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.handler.annotations.RoleUserQualifier;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.HandlersState;

/***
 * Меню изменения номера телефона "Контактных данных для связи"
 */
@Component
@RoleUserQualifier(RoleUser.CLIENT)
public class DailyReportHandlersState implements HandlersState {

    public final Handler petReportHandler;
    public final Handler backToPetMenuHandler;
    public final Handler sendTextHandler;



    public DailyReportHandlersState(
            @Qualifier("petReportHandler") Handler petReportHandler,
            @Qualifier("backToMainMenuHandler") Handler  backToPetMenuHandler,
            @Qualifier("sendTextHandler") Handler sendTextHandler
    ) {
        this.petReportHandler = petReportHandler;
        this.backToPetMenuHandler = backToPetMenuHandler;
        this.sendTextHandler = sendTextHandler;
    }

    @Override
    public Handler getHandler() {
        petReportHandler.setNext(sendTextHandler);
        sendTextHandler.setNext(backToPetMenuHandler);
        return petReportHandler;
    }

    @Override
    public boolean isState(State state) {
        return state == State.DAILY_REPORT_MENU;
    }
}
