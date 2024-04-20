package ru.teamfour.textcommand.handler.impl.volunteer.handlerbutton;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.textcommand.command.api.Command;
import ru.teamfour.textcommand.handler.api.AbstractHandler;

/**
 * Возвращает в главное меню волонтера
 */
@Component
public class BackToVolunteerMainMenuHandler extends AbstractHandler {

    public BackToVolunteerMainMenuHandler(@Qualifier("backToVolunteerMainMenuCommand") Command command) {
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
