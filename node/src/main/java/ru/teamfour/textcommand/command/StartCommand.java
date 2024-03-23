package ru.teamfour.textcommand.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.textcommand.command.api.AbstractTextCommand;
import ru.teamfour.textcommand.command.api.State;

@Component
public class StartCommand extends AbstractTextCommand {

    @Value("${buttonName.mainMenu}")
    private String buttonName;

    @Override
    public SendMessage execute(Update update) {
        //todo какие то действия
        String answerMessage = "Answer: " + buttonName;
        SendMessage startTextCommand = messageUtils.generateSendMessageWithText(update, answerMessage);
        return addMenu(startTextCommand);
    }

    @Override
    public State nextState() {
        return State.MAIN_MENU;
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals("/start") || message.equals(buttonName);
    }

}