package ru.teamfour.photocommand.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.teamfour.myutils.MessageUtils;
import ru.teamfour.service.api.user.UserServiceApi;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.menu.MenuButtonFactory;
import ru.teamfour.textcommand.menu.api.ButtonMenu;

@Component
public abstract class AbstractPhotoCommand implements PhotoCommand {

    protected MessageUtils messageUtils;

    protected MenuButtonFactory menuFactory;

    protected UserServiceApi userService;

    /**
     * Метод который добавляет к {@link SendMessage} меню {@link ButtonMenu} в зависимости от {@link State}
     *
     * @param sendMessage сообшение пользователю
     * @param state       название меню {@link State}
     * @return {@link SendMessage} с меню {@link ReplyKeyboardMarkup}
     */
    protected SendMessage addMenu(SendMessage sendMessage, State state) {
        sendMessage.setReplyMarkup(menuFactory.getButtonMenu(state).getMenu());
        return sendMessage;
    }

    @Autowired
    public void setMessageUtils(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Autowired
    public void setMenuFactory(MenuButtonFactory menuFactory) {
        this.menuFactory = menuFactory;
    }

    @Autowired
    public void setUserService(@Qualifier("userService") UserServiceApi userService) {
        this.userService = userService;
    }
}