package ru.teamfour.textcommand.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.textcommand.command.api.AbstractTextCommand;
import ru.teamfour.textcommand.command.api.StateCommand;

@Component
public class InfoAnimalShelterCommand extends AbstractTextCommand {
    @Override
    public SendMessage execute(Update update) {
        return messageUtils.generateSendMessageWithText(update, "InfoAnimalShelterTextCommand");
    }
    @Override
    public StateCommand nextState() {
        return StateCommand.MENU;
    }
    @Override
    public boolean isCommand(String message) {
        return message.equals("/info");
    }
}
