package ru.teamfour.textcommand.handler.impl.client.handlerbutton.dailyreportmenu;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.textcommand.command.api.Command;
import ru.teamfour.textcommand.handler.api.AbstractHandler;

@Component
public class SaveTextDailyReportHandler extends AbstractHandler {

    public SaveTextDailyReportHandler(@Qualifier("saveTextDailyReportCommand") Command command) {
        this.command = command;
    }

    @Override
    public Command handleRequest(Update update) {
        if (command.isCommand(update.getMessage().getText())) {
            return command;
        } else {
            return super.handleRequest(update);
        }
    }
}
