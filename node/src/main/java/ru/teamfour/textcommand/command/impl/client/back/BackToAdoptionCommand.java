package ru.teamfour.textcommand.command.impl.client.back;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.impl.client.mainmenu.AdoptionCommand;

/**
 * Возвращает пользователя в главное меню "кнопка назад"
 */
@Component
public class BackToAdoptionCommand extends AbstractCommand {

    @Value("${buttonName.backButton}")
    private String buttonName;

    private final AdoptionCommand command;

    public BackToAdoptionCommand(AdoptionCommand command) {
        this.command = command;
    }

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        MessageToTelegram newMessages = command.execute(commandContext);
        newMessages.getSendMessages().getFirst().setText("Вы вернулись в меню как усыновить животное");
        return newMessages;
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(buttonName);
    }

}
