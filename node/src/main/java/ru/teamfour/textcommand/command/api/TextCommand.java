package ru.teamfour.textcommand.command.api;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;

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
    SendMessage execute(Update update, User user);

    /***
     * Какие команды ожидаются после этой
     * Нужно чтобы менять состояние пользователя
     */
    /*State nextState();*/
}

