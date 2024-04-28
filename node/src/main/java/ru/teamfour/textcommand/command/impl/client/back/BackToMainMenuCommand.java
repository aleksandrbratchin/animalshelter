package ru.teamfour.textcommand.command.impl.client.back;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.command.impl.client.mainmenu.MainMenuCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Возвращает пользователя в главное меню "кнопка назад"
 */
@Component
public class BackToMainMenuCommand extends AbstractCommand {

    @Value("${buttonName.backButton}")
    private String backButton;

    @Value("${buttonName.backToMainMenuButton}")
    private String backToMainMenuButton;

    private final MainMenuCommand mainMenuCommand;

    public BackToMainMenuCommand(MainMenuCommand mainMenuCommand) {
        this.mainMenuCommand = mainMenuCommand;
    }

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        MessageToTelegram newMessages = mainMenuCommand.execute(commandContext);
        newMessages.getSendMessages().getFirst().setText("Вы вернулись в главное меню");
        return newMessages;
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(backButton) || message.equals(backToMainMenuButton);
    }

}

