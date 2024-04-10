package ru.teamfour.textcommand.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.infoForAdoption.InfoForAdoption;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.impl.infoForAdoption.InfoForAdoptionServiceImpl;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;

@Component
public class HomeImprovementForAdultAnimalCommand  extends AbstractCommand {
    @Value("${buttonName.homeImprovementForAdultAnimal}")
    private String buttonName;

    public HomeImprovementForAdultAnimalCommand(InfoForAdoptionServiceImpl service) {
        this.service = service;
    }

    private InfoForAdoptionServiceImpl service;

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        State state = State.RECOMMENDATIONS;
        user.setState(state);
        userService.save(user);

        String answerMessage = service.findInfoForAdoptionForDog().getHomeImprovementForAdultAnimal();
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

