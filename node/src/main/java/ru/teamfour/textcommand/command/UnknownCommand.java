package ru.teamfour.textcommand.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.textcommand.command.api.AbstractTextCommand;

@Component
public class UnknownCommand extends AbstractTextCommand {

    @Value("${buttonName.unknownCommand}")
    private String message;

    @Override
    public SendMessage execute(CommandContext commandContext) {
        Update update = commandContext.getUpdate();
        return messageUtils.generateSendMessageWithText(update, message);
    }

    @Override
    public boolean isCommand(String message) {
        return true;
    }
}
