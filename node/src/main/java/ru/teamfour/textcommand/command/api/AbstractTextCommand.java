package ru.teamfour.textcommand.command.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.teamfour.myutils.MessageUtils;
import ru.teamfour.service.api.user.UserServiceApi;
import ru.teamfour.textcommand.menu.api.ButtonMenu;
import ru.teamfour.textcommand.menu.MenuButtonFactory;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
public abstract class AbstractTextCommand implements TextCommand {
    @Autowired
    protected MessageUtils messageUtils;
    @Autowired
    protected MenuButtonFactory menuFactory;
    @Autowired
    @Qualifier("userService")
    protected UserServiceApi userService;

    /**
     * Метод который добавляет к {@link SendMessage} меню {@link ButtonMenu} в зависимости от {@link State}
     * @param sendMessage сообшение пользователю
     * @param state название меню {@link State}
     * @return {@link SendMessage} с меню {@link ReplyKeyboardMarkup}
     */
    protected SendMessage addMenu(SendMessage sendMessage, State state) {
        sendMessage.setReplyMarkup(menuFactory.getButtonMenu(state).getMenu());
        return sendMessage;
    }
}
