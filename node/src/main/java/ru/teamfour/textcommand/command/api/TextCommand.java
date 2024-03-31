package ru.teamfour.textcommand.command.api;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.teamfour.textcommand.command.CommandContext;

/***
 * Основной интерфейс для команд
 */
public interface TextCommand {
    /***
     * Проверяет подходит ли сообщение для выполнения команды
     * @param message сообщение
     * @return boolean true - подходит, false - не подходит
     */
    boolean isCommand(String message);

    /**
     * Выполнение команды
     * @param commandContext {@link CommandContext}
     * @return {@link SendMessage} текст сообщение с добавленным по необходимости меню
     */
    SendMessage execute(CommandContext commandContext);

}

