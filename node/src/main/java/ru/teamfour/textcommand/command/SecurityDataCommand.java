package ru.teamfour.textcommand.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.impl.shelter.ShelterServiceImpl;
import ru.teamfour.textcommand.command.api.AbstractTextCommand;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;

@Component

public class SecurityDataCommand extends AbstractTextCommand{

    @Value("${buttonName.securityData}")
    private String buttonName;

    @Autowired
    private ShelterServiceImpl shelterService;

    @Override
    public List<SendMessage> execute(CommandContext commandContext) {

        Update update = commandContext.getUpdate();
        State state = State.INFO_SHELTER;//todo нужно еще проверок навесить


        //todo какие то действия
        String answerMessage = "Данные охраны для оформлени пропуска на машину: " +
                shelterService.findAll().get(0).getSecurityData();

        SendMessage sendMessage = messageUtils.generateSendMessageWithText(update, answerMessage);
        List<SendMessage> sendMessages = new ArrayList<>();
        sendMessages.add(addMenu(sendMessage, state));
        return sendMessages;

    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(buttonName);
    }

}