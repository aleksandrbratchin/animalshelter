package ru.teamfour.textcommand.command.api;

public enum StateCommand {
    MENU, //Этап 0. Определение запроса
    PICK_UP_THE_ANIMAL, //Этап 2. Консультация с потенциальным хозяином животного из приюта
    START, PET_REPORT //Этап 3. Усыновление питомца
}
