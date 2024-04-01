package ru.teamfour.textcommand.handler.impl.volunteer.handlerButton;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.textcommand.command.api.TextCommand;
import ru.teamfour.textcommand.handler.api.AbstractHandler;

@Component
public class TalkWithClientHandler extends AbstractHandler {
    public TalkWithClientHandler(@Qualifier("talkWithClientCommand") TextCommand textCommand) {
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
