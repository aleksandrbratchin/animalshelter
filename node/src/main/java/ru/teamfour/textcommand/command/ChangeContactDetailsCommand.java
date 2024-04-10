package ru.teamfour.textcommand.command;

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
 * изменить номер телефона пользователя
 */
@Component
public class ChangeContactDetailsCommand extends AbstractCommand {

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        String phoneNumber = update.getMessage().getText();
        if(phoneNumber.matches("^\\+7-9\\d\\d-\\d\\d\\d-\\d\\d-\\d\\d$")){
            State state = State.MAIN_MENU;
            user.setState(state);
            user.getUserInfo().setPhoneNumber(phoneNumber);
            userService.save(user);
            String answerMessage = "Номер телефона успешно изменен!";
            SendMessage sendMessage = messageUtils.generateSendMessageWithText(update, answerMessage);
            List<SendMessage> sendMessages = new ArrayList<>();
            sendMessages.add(addMenu(sendMessage, state));
            return MessageToTelegram.builder()
                    .sendMessages(sendMessages)
                    .build();
        } else {
            String answerMessage = "Номер телефона должен быть в формате +7-9**-***-**-**";
            SendMessage sendMessage = messageUtils.generateSendMessageWithText(update, answerMessage);
            List<SendMessage> sendMessages = new ArrayList<>();
            sendMessages.add(sendMessage);
            return MessageToTelegram.builder()
                    .sendMessages(sendMessages)
                    .build();
        }

    }

    @Override
    public boolean isCommand(String message) {
        return true;
    }

}
