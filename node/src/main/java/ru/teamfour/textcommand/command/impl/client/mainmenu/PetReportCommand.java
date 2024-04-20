package ru.teamfour.textcommand.command.impl.client.mainmenu;

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

@Component
public class PetReportCommand extends AbstractCommand {
    @Value("${buttonName.petReport}")
    private String buttonName;

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        List<SendMessage> sendMessages = new ArrayList<>();

        if (user.getActiveAdoptionProcess() == null) {
            String answerMessage = "У вас нет усыновленного питомца";
            SendMessage sendMessage = messageUtils.generateSendMessageWithText(update, answerMessage);
            sendMessages.add(sendMessage);

        } else {
            State state = State.PET_REPORT;
            user.setState(state);
            userService.save(user);
            String answerMessage = "Здесь вы можете прислать отчет о содержании питомца";
            SendMessage sendMessage = messageUtils.generateSendMessageWithText(update, answerMessage);
            sendMessages.add(addMenu(sendMessage, state));
        }
        return MessageToTelegram.builder()
                .sendMessages(sendMessages)
                .build();

    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(buttonName);
    }
}
