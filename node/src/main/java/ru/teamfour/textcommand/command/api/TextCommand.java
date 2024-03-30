package ru.teamfour.textcommand.command.api;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.teamfour.textcommand.command.CommandContext;

/***
 * Основной интерфейс для команд
 */
public interface TextCommand {
    /***
     * Проверяет подходит ли сообщение для выполнения команды
     * @param message - сообщение
     */
    boolean isCommand(String message);

    /***
     * Что команда делает
     */
    SendMessage execute(CommandContext commandContext);

    /***
     * Какие команды ожидаются после этой
     * Нужно чтобы менять состояние пользователя
     */
    /*State nextState();*/
}

