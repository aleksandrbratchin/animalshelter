package ru.teamfour.textcommand.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.textcommand.command.api.AbstractTextCommand;
import ru.teamfour.textcommand.command.api.State;

@Component
public class WorkScheduleShelterCommand extends AbstractTextCommand {

    @Value("${buttonName.workScheduleShelter}")
    private String buttonName;

    @Override
    public SendMessage execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        State state = State.INFO_SHELTER;//todo нужно еще проверок навесить
        user.setState(state);
        userService.save(user);
        //todo какие то действия
        String answerMessage = "Answer: " + buttonName;
        SendMessage startTextCommand = messageUtils.generateSendMessageWithText(update, answerMessage);
        return addMenu(startTextCommand, state);
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(buttonName);
    }

}