package ru.teamfour.textcommand.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.api.shelter.ShelterService;
import ru.teamfour.textcommand.command.api.AbstractTextCommand;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Выбор приюта
 */
@Component
public class ChooseShelter extends AbstractTextCommand {

    @Autowired
    private ShelterService shelterService;

    @Override
    public List<SendMessage> execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        String name = update.getMessage().getText();
        State state = State.MAIN_MENU;
        user.setShelter(shelterService.findByName(name));
        user.setState(state);
        userService.save(user);

        SendMessage sendMessage = messageUtils.generateSendMessageWithText(update, "Вы выбрали приют \"" + name + "\"");
        List<SendMessage> sendMessages = new ArrayList<>();
        sendMessages.add(addMenu(sendMessage, state));
        return sendMessages;
    }

    /**
     * @param message сообщение
     * @return true если в БД есть приют с названием message
     */
    @Override
    public boolean isCommand(String message) {
        return shelterService.findAll()
                .stream()
                .map(Shelter::getName)
                .anyMatch(s -> s.equals(message));
    }

}
