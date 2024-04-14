package ru.teamfour.textcommand.command.impl.client.petreportmenu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.impl.user.UserService;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;

@Component
public class SendTextCommand extends AbstractCommand {
    @Value("${buttonName.sendText}")
    private String buttonName;

    public SendTextCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();

        State state = State.PET_REPORT;
        user.setState(state);
        userService.save(user);

        String answerMessage =  buttonName + "\n" +
                "В ежедневный отчет входит следующая информация:\n" +
                "Рацион животного:\n" +
                "Общее самочувствие и привыкание к новому месту:\n" +
                "Изменения в поведении: отказ от старых привычек, приобретение новых.";
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
