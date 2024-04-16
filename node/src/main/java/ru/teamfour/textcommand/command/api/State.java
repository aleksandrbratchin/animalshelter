package ru.teamfour.textcommand.command.api;

/**
 * enum для  храниения названий меню
 */
public enum State {
    INIT_MENU, //Меню выбора приютов
    MAIN_MENU, //Этап 0. Главное меню

    INFO_SHELTER, //Этап 1. Узнать информацию о приюте
    ADOPTION, //Этап 2. Как взять животное из приюта
    CONTACT_VOLUNTEER_MENU, //Выбор способа связи с волонтером
    VOLUNTEER_CHAT, //Чат с волонтером
    VOLUNTEER_START_MENU,
    PET_REPORT, //Этап 3. Прислать отчет о питомце
    RECOMMENDATIONS, //меню  рекомендаций для усыновителей

    LEAVE_CONTACT_DETAILS_FOR_COMMUNICATION_MENU,
    CLIENT_CHAT,
    LIST_ANIMALS_MENU, // Меню, которое выводит список животных для усыновления
    DAILY_REPORT_MENU, //Меню, которое выводит меню с отчётом о питомце

    WAITING_FOR_PHOTOS_FOR_DAILY_REPORT, //ожидание фото для ежедневного отчета
}


