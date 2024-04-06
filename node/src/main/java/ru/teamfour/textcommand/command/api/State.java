package ru.teamfour.textcommand.command.api;

/**
 * enum для  храниения названий меню
 */
public enum State {
    INIT_MENU, //Меню выбора приютов
    MAIN_MENU, //Этап 0. Главное меню

    INFO_SHELTER, //Этап 1. Узнать информацию о приюте
    ADOPTION, //Этап 2. Как взять животное из приюта
    VOLUNTEER_MENU, //Выбор способа связи с волонтером
    VOLUNTEER_CHAT, //Чат с волонтером
    VOLUNTEER_START_MENU,
    PET_REPORT //Этап 3. Прислать отчет о питомце
}
