package ru.teamfour.textcommand.menu.api;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public abstract class ButtonAbstractMenu implements ButtonMenu {
    protected ReplyKeyboardMarkup keyboardMarkup;
}
