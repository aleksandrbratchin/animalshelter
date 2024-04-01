package ru.teamfour.textcommand.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.textcommand.command.api.AbstractTextCommand;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Меняет роль пользователя на волонтера
 */
@Component
public class BecomeVolunteerCommand extends AbstractTextCommand {

    @Override
    public List<SendMessage> execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        State state = State.VOLUNTEER_START_MENU;
        user.setState(state);
        user.setRole(RoleUser.VOLUNTEER);
        userService.updateInfoAndState(user, update, state);
        String answerMessage = "Вы стали волонтером!";
        SendMessage sendMessage = messageUtils.generateSendMessageWithText(update, answerMessage);
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        List<SendMessage> sendMessages = new ArrayList<>();
        sendMessages.add(addMenu(sendMessage, state));
        return sendMessages;
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals("/volunteer");
    }

}