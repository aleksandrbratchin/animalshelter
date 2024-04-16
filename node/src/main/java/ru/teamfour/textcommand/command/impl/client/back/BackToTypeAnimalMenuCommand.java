package ru.teamfour.textcommand.command.impl.client.back;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.impl.client.initmenu.InitCommand;

/**
 * Возвращает пользователя к выбору типа животного
 * Кнопка "Назад"
 */
@Component
public class BackToTypeAnimalMenuCommand extends AbstractCommand {
    @Value("${buttonName.backButton}")
    private String buttonName;

    private final InitCommand command;

    public BackToTypeAnimalMenuCommand(InitCommand command) {
        this.command = command;
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(buttonName);
    }

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        return command.execute(commandContext);
    }
}
