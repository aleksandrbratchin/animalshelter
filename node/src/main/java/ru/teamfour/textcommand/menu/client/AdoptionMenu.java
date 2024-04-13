package ru.teamfour.textcommand.menu.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.menu.api.ButtonAbstractMenu;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdoptionMenu extends ButtonAbstractMenu {

    @Value("${buttonName.listAnimals}")
    private String listAnimals;
    @Value("${buttonName.rulesAnimals}")
    private String rulesAnimals;
    @Value("${buttonName.listDocuments}")
    private String listDocuments;
    @Value("${buttonName.recommendations}")
    private String recommendations;
    @Value("${buttonName.volunteer}")
    private String volunteerButton;
    @Value("${buttonName.backButton}")
    private String backButton;
    @Value("${buttonName.reasonsForRefusalOfAdoption}")
    private String reasonsForRefusalOfAdoption;
    @Value("${buttonName.contactForCommunication}")
    private String contactForCommunication;

    public ReplyKeyboardMarkup getMenu() {
        keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(listAnimals);
        row1.add(rulesAnimals);
        keyboard.add(row1);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(listDocuments);
        row2.add(recommendations);
        keyboard.add(row2);
        KeyboardRow row3 = new KeyboardRow();
        row3.add(reasonsForRefusalOfAdoption);
        row3.add(contactForCommunication);
        keyboard.add(row3);
        KeyboardRow row4 = new KeyboardRow();
        row4.add(volunteerButton);
        row4.add(backButton);
        keyboard.add(row4);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    @Override
    public boolean isMenu(State state) {
        return state == State.ADOPTION;
    }

}
