package ru.teamfour.textcommand.handler.api;

import ru.teamfour.textcommand.command.api.State;

/**
 * Реализация паттерна Цепочка обязанностей
 * Вызывает доступные команды в текущем меню по цепочке
 * {@link Handler} элемент цепи
 */
public interface HandlersState {
    /**
     * Возвращает цепочку из комманд которые можно вызывать в соответствующей меню {@link State}
     */
    Handler getHandler();

    /**
     * Проверяет что цепочка относится к меню по {@link State}
     * @param state {@link State}
     */
    boolean isState(State state);
}
