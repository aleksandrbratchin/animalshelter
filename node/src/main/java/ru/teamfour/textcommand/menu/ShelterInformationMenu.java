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
public class ShelterInformationMenu extends ButtonAbstractMenu {

    @Value("${buttonName.shelterAddress}")
    private String address;
    @Value("${buttonName.workScheduleShelter}")
    private String workScheduleShelter;
    @Value("${buttonName.drivingDirections}")
    private String drivingDirections;
    @Value("${buttonName.securityData}")
    private String securityData;
    @Value("${buttonName.safetyMeasuresInShelter}")
    private String safetyMeasuresInShelter;
    @Value("${buttonName.storyOfShelter}")
    private String storyOfShelter;
    @Value("${buttonName.volunteer}")
    private String volunteer;

    public ReplyKeyboardMarkup getMenu() {
        keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(address);
        row1.add(workScheduleShelter);
        keyboard.add(row1);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(drivingDirections);
        row2.add(securityData);
        keyboard.add(row2);
        KeyboardRow row3 = new KeyboardRow();
        row3.add(safetyMeasuresInShelter);
        row3.add(storyOfShelter);
        keyboard.add(row3);
        KeyboardRow row4 = new KeyboardRow();
        row4.add(volunteer);
        keyboard.add(row4);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    @Override
    public boolean isMenu(State state) {
        return state == State.INFO_SHELTER;
    }

}
