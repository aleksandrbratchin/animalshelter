package ru.teamfour.textcommand.command.api;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.teamfour.textcommand.command.CommandContext;

import java.util.List;

/***
 * Основной интерфейс для команд
 */
public interface Command {
    /***
     * Проверяет подходит ли сообщение для выполнения команды
     * @param message сообщение
     * @return boolean true - подходит, false - не подходит
     */
    boolean isCommand(String message);

    /**
     * Выполнение команды
     *
     * @param commandContext {@link CommandContext}
     * @return {@link List<SendMessage>} список собщений для отправки в телеграм
     */
    MessageToTelegram execute(CommandContext commandContext);

}
