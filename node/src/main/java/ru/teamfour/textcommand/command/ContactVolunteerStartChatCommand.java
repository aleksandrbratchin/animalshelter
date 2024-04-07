package ru.teamfour.textcommand.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.teamfour.dao.entity.user.Chat;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.textcommand.command.api.AbstractTextCommand;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Кнопка "Начать чат с волонтером" в меню "Позвать волонтера"
 * Если есть свободный волонтер формирует сообщение в чат волонтера и клиента о том что чат начат
 * Если свободных волонтеров нет формирует сообщение об этом
 */
@Component
public class ContactVolunteerStartChatCommand extends AbstractTextCommand {
    @Value("${buttonName.startChatWithVolunteer}")
    private String startChatWithVolunteer;

    @Override
    public List<SendMessage> execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        State state = State.VOLUNTEER_CHAT;
        var volunteer = userService.getAvailableVolunteer();
        if (volunteer != null) {
            volunteer.setChat(new Chat(user.getChatId()));
            volunteer.setState(state);
            volunteer.getVolunteerParam().setWorkload(volunteer.getVolunteerParam().getWorkload() + 1);

            String answerClientMessage = "Вы начали чат с волонтером @" + volunteer.getUserInfo().getNickName();
            String answerVolunteerMessage = "Вы начали чат с клиентом @" + user.getUserInfo().getNickName();

            user.setState(state);
            user.setChat(new Chat(volunteer.getChatId()));
            userService.saveAll(List.of(user, volunteer));
            SendMessage clientSendMessage = messageUtils.generateSendMessageWithText(user.getChatId(), answerClientMessage);
            SendMessage volunteerSendMessage = messageUtils.generateSendMessageWithText(volunteer.getChatId(), answerVolunteerMessage);
            List<SendMessage> sendMessages = new ArrayList<>();
            sendMessages.add(addMenu(volunteerSendMessage, state));
            sendMessages.add(addMenu(clientSendMessage, state));
            return sendMessages;
        } else {
            List<SendMessage> sendMessages = new ArrayList<>();
            sendMessages.add(messageUtils.generateSendMessageWithText(user.getChatId(), "Нет свободных волонтеров!"));
            return sendMessages;
        }
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(startChatWithVolunteer);
    }
}