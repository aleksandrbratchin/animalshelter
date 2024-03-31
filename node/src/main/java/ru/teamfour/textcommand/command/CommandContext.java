package ru.teamfour.textcommand.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.textcommand.command.api.TextCommand;

/**
 * Параметры необходимые для выполнения команд {@link TextCommand}
 */
@Getter
@Setter
@AllArgsConstructor
public class CommandContext {
    private Update update;

    private User user;
}
