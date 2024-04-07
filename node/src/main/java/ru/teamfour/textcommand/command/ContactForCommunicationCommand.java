package ru.teamfour.textcommand.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.service.impl.shelter.ShelterServiceImpl;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContactForCommunicationCommand extends AbstractCommand {

    @Value("${buttonName.contactForCommunication}")
    private String buttonName;

    @Autowired
    private ShelterServiceImpl shelterService;

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {

        Update update = commandContext.getUpdate();
        State state = State.INFO_SHELTER;

        String answerMessage = "Контактные данные для связи: "/* todo прием контактных данных +
                shelterService.findAll().get(0).getStoryOfShelter()*/;

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
