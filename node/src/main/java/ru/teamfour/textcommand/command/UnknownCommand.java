package ru.teamfour.textcommand.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.myutils.MessageUtils;
import ru.teamfour.textcommand.command.api.StateCommand;
import ru.teamfour.textcommand.command.api.TextCommand;

@Component
public class UnknownCommand implements TextCommand {
    private final MessageUtils messageUtils;
    public UnknownCommand(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }
    @Override
    public SendMessage execute(Update update) {
        return messageUtils.generateSendMessageWithText(update, "Неизвестная команда");
    }
    @Override
    public StateCommand nextState() {
        return StateCommand.MENU;
    }
    @Override
    public boolean isCommand(String message) {
        return true;
    }
}
