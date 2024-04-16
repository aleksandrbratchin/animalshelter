package ru.teamfour.textcommand.command.impl.client.back;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.impl.client.initmenu.InitCommand;
import ru.teamfour.textcommand.command.impl.client.mainmenu.PetReportCommand;

/**
 * Возвращает пользователя в отчёт о питомце
 * Кнопка "Назад"
 */
@Component
public class BackToPetReportCommand extends AbstractCommand {
    @Value("${buttonName.backButton}")
    private String buttonName;

    private final PetReportCommand command;

    public BackToPetReportCommand(PetReportCommand command) {
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
