package ru.teamfour.textcommand.handler.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.textcommand.command.api.TextCommand;
import ru.teamfour.textcommand.handler.api.AbstractHandler;

@Component
public class InfoAnimalShelterHandler extends AbstractHandler {

    private final TextCommand textCommand;

    public InfoAnimalShelterHandler(@Qualifier("infoAnimalShelterCommand") TextCommand textCommand) {
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
