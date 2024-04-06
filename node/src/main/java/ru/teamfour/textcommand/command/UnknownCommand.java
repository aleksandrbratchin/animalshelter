package ru.teamfour.textcommand.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.textcommand.command.api.AbstractTextCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Формирует сообщение о том что введена неизвестная команда
 */
@Component
public class UnknownCommand extends AbstractTextCommand {

    @Value("${buttonName.unknownCommand}")
    private String message;

    @Override
    public List<SendMessage> execute(CommandContext commandContext) {
        Update update = commandContext.getUpdate();
        List<SendMessage> sendMessages = new ArrayList<>();
        sendMessages.add(messageUtils.generateSendMessageWithText(update, message));
        return sendMessages;
    }

    @Override
    public boolean isCommand(String message) {
        return true;
    }
}
