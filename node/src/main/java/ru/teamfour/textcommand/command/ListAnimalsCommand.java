package ru.teamfour.textcommand.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.api.animal.AnimalService;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListAnimalsCommand extends AbstractCommand {
    @Autowired
    private AnimalService animalService;
    @Value("${buttonName.listAnimals}")
    private String buttonName;


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
        //todo какие то действия
        String answerMessage = "Список животных " + animalService.findAll().stream()
                .map(animal -> animal.getName()).reduce((str1, str2) -> str1 + ", " + str2)
                .orElse("Животных нет!");;
        SendMessage startTextCommand = messageUtils.generateSendMessageWithText(update, answerMessage);
        List<SendMessage> sendMessages = new ArrayList<>();
        sendMessages.add(addMenu(startTextCommand, state));
        return MessageToTelegram.builder()
                .sendMessages(sendMessages)
                .build();
    }
}
