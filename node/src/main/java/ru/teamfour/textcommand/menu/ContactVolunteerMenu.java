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
public class ContactVolunteerMenu extends ButtonAbstractMenu {

    @Value("${buttonName.contactVolunteersByNickname}")
    private String contactVolunteersByNickname;

    @Value("${buttonName.contactVolunteersByPhoneNumber}")
    private String contactVolunteersByPhoneNumber;

    @Value("${buttonName.startChatWithVolunteer}")
    private String startChatWithVolunteer;

    public ReplyKeyboardMarkup getMenu() {
        keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(contactVolunteersByNickname);
        row1.add(contactVolunteersByPhoneNumber);
        keyboard.add(row1);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(startChatWithVolunteer);
        keyboard.add(row2);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    @Override
    public boolean isMenu(State state) {
        return state == State.CONTACT_VOLUNTEER_MENU;
    }

}
