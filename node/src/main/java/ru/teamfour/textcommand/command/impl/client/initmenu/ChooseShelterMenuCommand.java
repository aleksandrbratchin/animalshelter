package ru.teamfour.textcommand.command.impl.client.initmenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.teamfour.dao.entity.animal.TypeAnimal;
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
 * Меню выбора приютов
 */
@Component
public class ChooseShelterMenuCommand extends AbstractCommand {

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
        String message = commandContext.getUpdate().getMessage().getText();
        State state = State.CHOOSE_SHELTER_MENU;
        user.setState(state);
        userService.save(user);

        List<Shelter> all = new ArrayList<>();
        if(dogShelter.equals(message)){
            all = shelterService.findByTypeAnimal(TypeAnimal.DOG);
        } else if(catShelter.equals(message)) {
            all = shelterService.findByTypeAnimal(TypeAnimal.CAT);
        }

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
            for (int i = 0; i < all.size(); i++) {
                row.add(all.get(i).getName());
                if ((i + 1) % 2 == 0) {
                    keyboard.add(row);
                    row = new KeyboardRow();
                }
            }
            keyboard.add(row);
            keyboardMarkup.setKeyboard(keyboard);
            String answerMessage = "Выберите приют";
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
        return message.equals(dogShelter) || message.equals(catShelter);
    }

}
