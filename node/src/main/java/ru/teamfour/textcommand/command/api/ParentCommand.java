package ru.teamfour.textcommand.command.api;

/***
 * Основной интерфейс для команд
 */
public interface ParentCommand<C, P> {

    /***
     * Выполнение команды
     */
    P execute(C command);

}
