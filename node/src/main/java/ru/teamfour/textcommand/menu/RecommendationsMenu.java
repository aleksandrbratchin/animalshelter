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
public class RecommendationsMenu extends ButtonAbstractMenu {
    //todo функционал заглушка
    @Value("${buttonName.transportation}")
    private String transportation;
    @Value("${buttonName.homeImprovementForPuppy}")
    private String homeImprovementForPuppy;
    @Value("${buttonName.homeImprovementForAdultAnimal}")
    private String homeImprovementForAdultAnimal;
    @Value("${buttonName.homeImprovementForAnimalWithDisabilities}")
    private String homeImprovementForAnimalWithDisabilities;
    @Value("${buttonName.tipsFromDogHandler}")
    private String tipsFromDogHandler;
    @Value("${buttonName.backButton}")
    private String backButton;
    @Value("${buttonName.listDogHandlers}")
    private String listDogHandlers;

    public ReplyKeyboardMarkup getMenu() {
        keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(transportation);
        row1.add(homeImprovementForPuppy);
        keyboard.add(row1);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(homeImprovementForAdultAnimal);
        row2.add(homeImprovementForAnimalWithDisabilities);
        keyboard.add(row2);
        KeyboardRow row3 = new KeyboardRow();
        row3.add(tipsFromDogHandler);
        row3.add(listDogHandlers);
        keyboard.add(row3);
        KeyboardRow row4 = new KeyboardRow();
        row4.add(backButton);
        keyboard.add(row4);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    @Override
    public boolean isMenu(State state) {
        return state == State.RECOMMENDATIONS;
    }

}
