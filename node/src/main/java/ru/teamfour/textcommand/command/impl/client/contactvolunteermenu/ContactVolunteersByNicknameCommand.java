package ru.teamfour.textcommand.command.impl.client.contactvolunteermenu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Кнопка "По имени пользователя" в меню "Позвать волонтера"
 * Если есть волонтеры, формирует сообщение с именами пользователей наимение загруженных волонтеров
 */
@Component
public class ContactVolunteersByNicknameCommand extends AbstractCommand {
    @Value("${buttonName.contactVolunteersByNickname}")
    private String buttonName;

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();

        String answerMessage =
                userService.getVolunteersByNickNameIsNotNull().stream()
                .limit(10)
                .map(user1 -> "@" + user1.getUserInfo().getNickName())
                .reduce((str1, str2) -> str1 + ", " + str2)
                .orElse("Попробуйте другой способ связи с волонтером!");
        State state = State.CONTACT_VOLUNTEER_MENU;
        userService.updateState(user, state);
        SendMessage sendMessage = messageUtils.generateSendMessageWithText(update, answerMessage);
        List<SendMessage> sendMessages = new ArrayList<>();
        sendMessages.add(addMenu(sendMessage, state));
        return MessageToTelegram.builder()
                .sendMessages(sendMessages)
                .build();
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(buttonName);
    }
}