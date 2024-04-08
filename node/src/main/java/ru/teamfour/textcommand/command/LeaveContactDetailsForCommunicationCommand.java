package ru.teamfour.textcommand.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Открывает меню изменения контактных данных
 */
@Component
public class LeaveContactDetailsForCommunicationCommand extends AbstractCommand {

    @Value("${buttonName.contactForCommunication}")
    private String buttonName;

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        State state = State.LEAVE_CONTACT_DETAILS_FOR_COMMUNICATION_MENU;
        user.setState(state);
        userService.save(user);
        String phoneNumber = user.getUserInfo().getPhoneNumber();
        String answerMessage = phoneNumber == null ?
                "Чтобы сохранить номер телефона, пришлите его в формате +7-9**-***-**-**" :
                "У вас указан номер для связи " + phoneNumber + " Чтобы изменить номер телефона, пришлите его в формате +7-9**-***-**-**";
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
