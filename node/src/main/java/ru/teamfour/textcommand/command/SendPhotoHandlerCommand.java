package ru.teamfour.textcommand.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.impl.user.UserService;
import ru.teamfour.textcommand.command.api.AbstractTextCommand;
import ru.teamfour.textcommand.command.api.State;
@Component
public class SendPhotoHandlerCommand extends AbstractTextCommand {
    @Value("${buttonName.sendPhoto}")
    private String buttonName;

    public SendPhotoHandlerCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public SendMessage execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();

        State state = State.PET_REPORT;//todo нужно еще проверок навесить
        user.setState(state);
        userService.save(user);

        String answerMessage = "Answer: " + buttonName;
        SendMessage startTextCommand = messageUtils.generateSendMessageWithText(update, answerMessage);
        return addMenu(startTextCommand, state);
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(buttonName);
    }

}
