package ru.teamfour.photocommand.impl.dailyreport;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.photocommand.CommandPhotoContext;
import ru.teamfour.photocommand.api.AbstractPhotoCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Сохранить фато животного в отчет
 */
@Component
public class SavePhotoDailyReportCommand extends AbstractPhotoCommand {

    @Override
    public MessageToTelegram execute(CommandPhotoContext commandContext) {

        User user = commandContext.getUser();
        State state = State.PET_REPORT;
        user.setState(state);
        userService.save(user);


        //todo обработать и сохранить фото

        SendMessage sendMessage = messageUtils.generateSendMessageWithText(
                Long.valueOf(commandContext.getTransferByteObject().getChatId()),
                "Фото отправлено!");
        List<SendMessage> sendMessages = new ArrayList<>();
        sendMessages.add(addMenu(sendMessage, state));
        return MessageToTelegram.builder()
                .sendMessages(sendMessages)
                .build();
    }
}
