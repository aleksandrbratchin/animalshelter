package ru.teamfour.textcommand.command.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.teamfour.myutils.MessageUtils;
import ru.teamfour.service.impl.user.UserService;
import ru.teamfour.textcommand.menu.MenuButtonFactory;

@Component
public abstract class AbstractTextCommand implements TextCommand {
    @Autowired
    protected MessageUtils messageUtils;
    @Autowired
    protected MenuButtonFactory menuFactory;
    @Autowired
    protected UserService userService;

    protected SendMessage addMenu(SendMessage sendMessage, State state) {
        sendMessage.setReplyMarkup(menuFactory.getButtonMenu(state).getMenu());
        return sendMessage;
    }
}
