package ru.teamfour.textcommand.menu.volunteer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.menu.api.ButtonAbstractMenu;

import java.util.ArrayList;
import java.util.List;

@Component
public class VolunteerCheckReportMenuChat extends ButtonAbstractMenu {

    @Value("${buttonName.rejectReport}")
    private String rejectReport;

    @Value("${buttonName.acceptReport}")
    private String acceptReport;

    @Value("${buttonName.backButton}")
    private String backButton;

    public ReplyKeyboardMarkup getMenu() {
        keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(rejectReport);
        row1.add(acceptReport);
        keyboard.add(row1);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(backButton);
        keyboard.add(row2);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    @Override
    public boolean isMenu(State state) {
        return state == State.CHECK_REPORT;
    }

}
