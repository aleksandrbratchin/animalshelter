package ru.teamfour.textcommand.handler.impl.volunteer.handlerButton;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.AbstractTextCommand;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.command.api.TextCommand;
import ru.teamfour.textcommand.handler.api.AbstractHandler;

@Component
public class StartVolunteerHandler extends AbstractHandler {

    public StartVolunteerHandler(@Qualifier("startVolunteerCommand") TextCommand textCommand) {
        this.textCommand = textCommand;
    }

    @Override
    public TextCommand handleRequest(Update update) {
        if (textCommand.isCommand(update.getMessage().getText())) {
            return textCommand;
        } else {
            return super.handleRequest(update);
        }
    }

}
