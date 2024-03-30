package ru.teamfour.textcommand.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.textcommand.command.api.AbstractTextCommand;
import ru.teamfour.textcommand.command.api.State;

@Component
public class ContactVolunteersByPhoneNumberCommand extends AbstractTextCommand {
    @Value("${buttonName.contactVolunteersByPhoneNumber}")
    private String buttonName;

    @Override
    public SendMessage execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        State state = State.VOLUNTEER_MENU;//todo нужно еще проверок навесить

        user = userService.updateState(user, state);
        //todo какие то действия
        String answerMessage = "contactVolunteersByPhoneNumber";
        SendMessage startTextCommand = messageUtils.generateSendMessageWithText(update, answerMessage);
        return addMenu(startTextCommand, state);
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(buttonName);
    }
}