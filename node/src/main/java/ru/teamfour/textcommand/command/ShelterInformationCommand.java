package ru.teamfour.textcommand.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.Users;
import ru.teamfour.service.impl.user.UserService;
import ru.teamfour.textcommand.command.api.AbstractTextCommand;
import ru.teamfour.textcommand.command.api.State;

@Component
public class ShelterInformationCommand extends AbstractTextCommand {

    private final UserService userService;

    @Value("${buttonName.shelterInformation}")
    private String buttonName;

    public ShelterInformationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public SendMessage execute(Update update) {
        var chatId = update.getMessage().getChat().getId();
        Users users = userService.findByChatId(chatId);
        users.setState(nextState());
        userService.save(users);

        String answerMessage = "Answer: " + buttonName;
        SendMessage startTextCommand = messageUtils.generateSendMessageWithText(update, answerMessage);
        return addMenu(startTextCommand);
    }

    @Override
    public State nextState() {
        return State.INFO_SHELTER;
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(buttonName);
    }
}
