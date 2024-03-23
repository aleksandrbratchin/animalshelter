package ru.teamfour.textcommand.menu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.menu.api.ButtonAbstractMenu;

import java.util.ArrayList;
import java.util.List;

@Component
public class MainMenu extends ButtonAbstractMenu {

    @Value("${buttonName.shelterInformation}")
    private String shelterInformationButton;

    @Value("${buttonName.petReport}")
    private String petReportButton;

    @Value("${buttonName.adoption}")
    private String adoptionButton;

    @Value("${buttonName.volunteer}")
    private String volunteerButton;

    public ReplyKeyboardMarkup getMenu() {
        keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(shelterInformationButton);
        row1.add(adoptionButton);
        keyboard.add(row1);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(petReportButton);
        row2.add(volunteerButton);
        keyboard.add(row2);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    @Override
    public boolean isMenu(State state) {
        return state == State.MAIN_MENU;
    }

}
