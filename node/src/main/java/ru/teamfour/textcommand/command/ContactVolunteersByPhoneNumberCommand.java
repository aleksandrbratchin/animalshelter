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
public class ContactVolunteersByPhoneNumberCommand extends AbstractTextCommand {
    @Value("${buttonName.contactVolunteersByPhoneNumber}")
    private String buttonName;

    @Override
    public List<SendMessage> execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();

        String answerMessage = "Номера телефонов: " +
                userService.getVolunteersByPhoneNumberIsNotNull().stream()
                        .limit(10)
                        .map(user1 -> user1.getUserInfo().getPhoneNumber())
                        .reduce((str1, str2) -> str1 + ", " + str2)
                        .orElse("Попробуйте другой способ связи с волонтером!");
        State state = State.VOLUNTEER_MENU;
        userService.updateState(user, state);
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