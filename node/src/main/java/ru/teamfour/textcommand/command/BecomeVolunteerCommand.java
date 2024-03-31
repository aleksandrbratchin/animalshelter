package ru.teamfour.textcommand.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.textcommand.command.api.AbstractTextCommand;
import ru.teamfour.textcommand.command.api.State;

/**
 * Меняет роль пользователя на волонтера
 */
@Component
public class BecomeVolunteerCommand extends AbstractTextCommand {

    @Override
    public SendMessage execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        State state = State.VOLUNTEER_START_MENU;
        user.setState(state);
        user.setRole(RoleUser.VOLUNTEER);
        userService.updateInfoAndState(user, update, state);
        String answerMessage = "Вы стали волонтером!";
        SendMessage startTextCommand = messageUtils.generateSendMessageWithText(update, answerMessage);
        startTextCommand.setReplyMarkup(new ReplyKeyboardRemove(true));
        return startTextCommand;
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals("/volunteer");
    }

}
