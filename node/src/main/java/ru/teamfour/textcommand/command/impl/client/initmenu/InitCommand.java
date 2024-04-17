package ru.teamfour.textcommand.command.impl.client.initmenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.api.shelter.ShelterService;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Меню выбора типа животного
 */
@Component
public class InitCommand extends AbstractCommand {

    @Value("${buttonName.initCommand}")
    private String buttonName;

    @Value("${buttonName.dogShelter}")
    private String dogShelter;

    @Value("${buttonName.catShelter}")
    private String catShelter;

    @Autowired
    private ShelterService shelterService;

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        State state = State.INIT_MENU;
        user.setState(state);
        userService.save(user);
        List<Shelter> all = shelterService.findAll();

        if(all.size() == 0){
            String answerMessage =  "Нет приютов(";
            SendMessage sendMessage = messageUtils.generateSendMessageWithText(update, answerMessage);
            sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
            List<SendMessage> sendMessages = new ArrayList<>();
            sendMessages.add(sendMessage);
            return MessageToTelegram.builder()
                    .sendMessages(sendMessages)
                    .build();
        } else {

            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            keyboardMarkup.setSelective(true);
            keyboardMarkup.setResizeKeyboard(true);
            keyboardMarkup.setOneTimeKeyboard(false);

            List<KeyboardRow> keyboard = new ArrayList<>();
            KeyboardRow row = new KeyboardRow();
            row.add(dogShelter);
            row.add(catShelter);
            keyboard.add(row);
            keyboardMarkup.setKeyboard(keyboard);
            String answerMessage = "Приют для каких животных вас интересует?";
            SendMessage sendMessage = messageUtils.generateSendMessageWithText(update, answerMessage);
            sendMessage.setReplyMarkup(keyboardMarkup);
            List<SendMessage> sendMessages = new ArrayList<>();
            sendMessages.add(sendMessage);
            return MessageToTelegram.builder()
                    .sendMessages(sendMessages)
                    .build();
        }

    }

    @Override
    public boolean isCommand(String message) {
        return message.equals("/start") || message.equals(buttonName);
    }

}
