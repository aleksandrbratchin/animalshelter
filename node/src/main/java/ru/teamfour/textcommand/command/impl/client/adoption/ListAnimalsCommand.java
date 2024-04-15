package ru.teamfour.textcommand.command.impl.client.adoption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.impl.shelter.ShelterServiceImpl;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ListAnimalsCommand extends AbstractCommand {
    @Value("${buttonName.listAnimals}")
    private String buttonName;
    private final ShelterServiceImpl service;

    public ListAnimalsCommand(ShelterServiceImpl service) {
        this.service = service;
    }


    @Override
    public boolean isCommand(String message) {
        return message.equals(buttonName);
    }

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        State state = State.ADOPTION;
        user.setState(state);
        userService.save(user);
        String info = service.findAllAnimalsNotAdoption(user
                .getShelter().getId());
        String answerMessage = Optional.ofNullable(info).orElse("Нет информации");
        SendMessage startTextCommand = messageUtils.generateSendMessageWithText(update, answerMessage);
        List<SendMessage> sendMessages = new ArrayList<>();
        sendMessages.add(addMenu(startTextCommand, state));
        return MessageToTelegram.builder()
                .sendMessages(sendMessages)
                .build();
    }
}
