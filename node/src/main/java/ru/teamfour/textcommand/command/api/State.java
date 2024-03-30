package ru.teamfour.textcommand.command.api;

public enum State {
    MAIN_MENU, //Этап 0. Главное меню
    INFO_SHELTER, //Этап 1. Узнать информацию о приюте
    ADOPTION, //Этап 2. Как взять животное из приюта
    VOLUNTEER_MENU,
    PET_REPORT //Этап 3. Прислать отчет о питомце
}
