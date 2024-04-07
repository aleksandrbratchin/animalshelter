package ru.teamfour.textcommand.command;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.drivingDirections.DrivingDirections;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.api.drivingDirections.DrivingDirectionsService;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import transfer.TransferByteObject;

import java.util.ArrayList;
import java.util.List;

@Component
public class DrivingDirectionsCommand extends AbstractCommand {

    @Value("${buttonName.drivingDirections}")
    private String buttonName;

    private final DrivingDirectionsService drivingDirectionsService;

    public DrivingDirectionsCommand(@Qualifier("drivingDirectionsServiceImpl") DrivingDirectionsService drivingDirectionsService) {
        this.drivingDirectionsService = drivingDirectionsService;
    }

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        String chatId = update.getMessage().getChatId().toString();

        DrivingDirections drivingDirections = drivingDirectionsService.findByShelterId(user.getShelter().getId());

        if (drivingDirections.getData() != null && drivingDirections.getData().length > 0) {
            List<TransferByteObject> transferByteObjects = new ArrayList<>();
            transferByteObjects.add(new TransferByteObject(
                    chatId,
                    drivingDirections.getData()
            ));
            return MessageToTelegram.builder()
                    .transferByteObjects(transferByteObjects)
                    .build();
        } else {
            return MessageToTelegram.builder()
                    .sendMessages(new ArrayList<>() {{
                        this.add(new SendMessage(chatId, "Отсутствует изображение для отправки"));
                    }})
                    .build();
        }

    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(buttonName);
    }

}
