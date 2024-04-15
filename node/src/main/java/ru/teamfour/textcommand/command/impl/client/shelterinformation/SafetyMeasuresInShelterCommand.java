package ru.teamfour.textcommand.command.impl.client.shelterinformation;

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
import java.util.Optional;

@Component
public class SafetyMeasuresInShelterCommand extends AbstractCommand {

    @Value("${buttonName.safetyMeasuresInShelter}")
    private String buttonName;

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        State state = State.INFO_SHELTER;

        String info = "Техника безопасности на территории приюта: \n" +
                user.getShelter().getSafetyMeasures();
        String answerMessage = Optional.ofNullable(info).orElse("Нет информации");

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
