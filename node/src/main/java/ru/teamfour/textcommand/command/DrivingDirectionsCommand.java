package ru.teamfour.textcommand.command;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.textcommand.command.api.AbstractTextCommand;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;

@Component
public class DrivingDirectionsCommand extends AbstractTextCommand {

    @Value("${buttonName.drivingDirections}")
    private String buttonName;

    @Override
    public List<SendMessage> execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        State state = State.INFO_SHELTER;
        user.setState(state);
        userService.save(user);
        String answerMessage = "Answer: " + buttonName;
        SendMessage sendMessage = messageUtils.generateSendMessageWithText(update, answerMessage);
        List<SendMessage> sendMessages = new ArrayList<>();
        sendMessages.add(addMenu(sendMessage, state));
        return sendMessages;
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(buttonName);
    }

}
