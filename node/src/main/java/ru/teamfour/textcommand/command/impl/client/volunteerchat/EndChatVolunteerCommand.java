package ru.teamfour.textcommand.command.impl.client.volunteerchat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import ru.teamfour.dao.entity.user.Chat;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Кнопка "Завершить чат с волонтером" в меню чата волонтера
 * Формирует сообщение в чат клиента и волонтера о том что чат завершен
 */
@Component
public class EndChatVolunteerCommand extends AbstractCommand {
    @Value("${buttonName.endChatWithVolunteer}")
    private String endChatWithVolunteer;

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        var client = commandContext.getUser();
        long idChatClient = client.getChatId();
        long idChatVolunteer = client.getChat().getActiveChat();
        client.setChat(new Chat(null));
        client.setState(State.MAIN_MENU);

        String answerClientMessage = "Вы завершили чат!";
        String answerVolunteerMessage = "Клиент завершил чат!";

        var volunteer = userService.findByChatId(idChatVolunteer);
        volunteer.setState(State.VOLUNTEER_START_MENU);
        volunteer.setChat(new Chat(null));
        userService.saveAll(List.of(volunteer, client));
        SendMessage clientSendMessage = messageUtils.generateSendMessageWithText(idChatClient, answerClientMessage);
        SendMessage volunteerSendMessage = messageUtils.generateSendMessageWithText(idChatVolunteer, answerVolunteerMessage);
        List<SendMessage> sendMessages = new ArrayList<>();
        sendMessages.add(addMenu(clientSendMessage, client.getState()));
        volunteerSendMessage.setReplyMarkup(new ReplyKeyboardRemove(true)); //удаляет все кнопки
        sendMessages.add(volunteerSendMessage);
        return MessageToTelegram.builder()
                .sendMessages(sendMessages)
                .build();

    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(endChatWithVolunteer);
    }
}