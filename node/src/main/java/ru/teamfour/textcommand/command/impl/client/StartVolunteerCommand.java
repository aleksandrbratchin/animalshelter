package ru.teamfour.textcommand.command.impl.client;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;

/**
 * /start в меню волонтера
 * обновляет информацию о волонтере
 */
@Component
public class StartVolunteerCommand extends AbstractCommand {

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        State state = State.VOLUNTEER_START_MENU;
        user.setState(state);
        userService.updateInfoAndState(user, update, state);
        String answerMessage = "Главное меню";
        SendMessage sendMessage = messageUtils.generateSendMessageWithText(update, answerMessage);
        List<SendMessage> sendMessages = new ArrayList<>();
        sendMessages.add(addMenu(sendMessage, state));

        return MessageToTelegram.builder()
                .sendMessages(sendMessages)
                .build();
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals("/main_menu");
    }

}
