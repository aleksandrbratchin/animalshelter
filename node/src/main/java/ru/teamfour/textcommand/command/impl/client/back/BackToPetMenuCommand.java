package ru.teamfour.textcommand.command.impl.client.back;

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
 * Возвращает пользователя в главное меню "кнопка назад"
 */
@Component
public class BackToPetMenuCommand extends AbstractCommand {

    @Value("${buttonName.backButton}")
    private String backButton;

    @Value("${buttonName.backToPetMenuButton}")
    private String backToPetMenuButton;

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        State state = State.PET_REPORT;
        user.setState(state);
        userService.save(user);
        String answerMessage = "Вы вернулись в меню 'Прислать отчёт о питомце'";
        SendMessage sendMessage = messageUtils.generateSendMessageWithText(update, answerMessage);
        List<SendMessage> sendMessages = new ArrayList<>();
        sendMessages.add(addMenu(sendMessage, state));
        return MessageToTelegram.builder()
                .sendMessages(sendMessages)
                .build();
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(backButton) || message.equals(backToPetMenuButton);
    }

}
