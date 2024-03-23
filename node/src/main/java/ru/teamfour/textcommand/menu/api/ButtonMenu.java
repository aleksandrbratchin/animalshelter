package ru.teamfour.textcommand.menu.api;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.teamfour.textcommand.command.api.State;

public interface ButtonMenu {
    ReplyKeyboardMarkup getMenu();

    boolean isMenu(State state);
}
