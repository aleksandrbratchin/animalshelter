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
public class DailyReportMenuCommand extends AbstractCommand {

    @Value("${buttonName.sendText}")
    private String buttonName;

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        State state = State.DAILY_REPORT_MENU;
        user.setState(state);
        userService.save(user);
        String answerMessage = "В ежедневный отчет входит следующая информация:\n" +
                "Рацион животного:\n" +
                "Общее самочувствие и привыкание к новому месту:\n" +
                "Изменения в поведении: отказ от старых привычек, приобретение новых. ";
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
