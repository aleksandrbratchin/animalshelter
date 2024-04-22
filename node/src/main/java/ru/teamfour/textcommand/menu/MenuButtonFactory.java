package ru.teamfour.textcommand.menu;

import org.springframework.stereotype.Component;
import ru.teamfour.exception.NotFoundButtonMenuException;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.menu.api.ButtonMenu;

import java.util.List;

@Component
public class MenuButtonFactory {

    private final List<ButtonMenu> buttonMenus;

    public MenuButtonFactory(List<ButtonMenu> buttonMenus) {
        this.buttonMenus = buttonMenus;
    }

    public ButtonMenu getButtonMenu(State state) {
        for (ButtonMenu menu : buttonMenus) {
            if (menu.isMenu(state)) {
                return menu;
            }
        }
        throw new NotFoundButtonMenuException("Не найдено меню для state = " + state.name());
    }
}
