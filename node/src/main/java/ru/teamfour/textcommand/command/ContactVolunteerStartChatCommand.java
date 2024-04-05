package ru.teamfour.textcommand.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.Chat;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.dao.entity.user.VolunteerParam;
import ru.teamfour.textcommand.command.api.AbstractTextCommand;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContactVolunteerStartChatCommand extends AbstractTextCommand {
    @Value("${buttonName.startChatWithVolunteer}")
    private String startChatWithVolunteer;

    @Override
    public List<SendMessage> execute(CommandContext commandContext) {
        //todo предусмотреть ситуацию когда доступных волонтеров нет
        User user = commandContext.getUser();
        State state = State.VOLUNTEER_CHAT;
        var volunteer = userService.getAvailableVolunteer();
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
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(startChatWithVolunteer);
    }
}