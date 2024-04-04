package ru.teamfour.textcommand.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.api.shelter.ShelterService;
import ru.teamfour.textcommand.command.api.AbstractTextCommand;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Меню выбора приютов
 */
@Component
public class InitCommand extends AbstractTextCommand {

    @Autowired
    private ShelterService shelterService;

    @Override
    public List<SendMessage> execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        State state = State.INIT_MENU;
        user.setState(state);
        userService.save(user);
        List<Shelter> all = shelterService.findAll();

        //делаем меню
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        for (int i = 0; i < all.size(); i++) {
            row.add(all.get(i).getStoryOfShelter());
            if ((i + 1) % 2 == 0) {
                keyboard.add(row);
                row = new KeyboardRow();
            }
        }
        keyboardMarkup.setKeyboard(keyboard);

        String answerMessage = "Выберите приют";
        SendMessage sendMessage = messageUtils.generateSendMessageWithText(update, answerMessage);
        sendMessage.setReplyMarkup(keyboardMarkup);
        List<SendMessage> sendMessages = new ArrayList<>();
        sendMessages.add(sendMessage);
        return sendMessages;
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals("/init");
    }

}
