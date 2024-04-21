package ru.teamfour.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.photocommand.CommandPhotoContext;
import ru.teamfour.photocommand.impl.dailyreport.SavePhotoDailyReportCommand;
import ru.teamfour.service.api.ConsumerService;
import ru.teamfour.service.api.ProducerService;
import ru.teamfour.service.impl.user.UserService;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.Command;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.HandlersState;
import ru.teamfour.textcommand.handler.api.HandlersStateFactory;
import ru.teamfour.textcommand.handler.impl.HandlersRoleFactory;
import transfer.TransferByteObject;

/**
 * Принамает сообщения из брокера и выполняет действия в зависимости от входящего сообщения
 */
@Log4j2
@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final ProducerService producerService;
    private final HandlersRoleFactory handlersRoleFactory;
    private final UserService userService;
    private final SavePhotoDailyReportCommand savePhotoDailyReportCommand;

    public ConsumerServiceImpl(ProducerService producerService, HandlersRoleFactory handlersRoleFactory, UserService userService, SavePhotoDailyReportCommand savePhotoDailyReportCommand) {
        this.producerService = producerService;
        this.handlersRoleFactory = handlersRoleFactory;
        this.userService = userService;
        this.savePhotoDailyReportCommand = savePhotoDailyReportCommand;
    }

    /**
     * Обрабатывает сообщения из очереди текстовых сообщений
     * @param update {@link Update}
     */
    @Override
    @RabbitListener(queues = "${rabbitQueue.messages.update.TEXT}")
    public void consumerTextMessageUpdates(Update update) {
        User user = userService.findByUserByChatIdOrCreateUser(update);

        HandlersStateFactory handlersStateFactory = handlersRoleFactory.getHandlers(user.getRole());
        HandlersState handlers = handlersStateFactory.getHandlers(user.getState());
        Handler handler = handlers.getHandler();
        Command command = handler.handleRequest(update);

        MessageToTelegram messageToTelegram = command.execute(new CommandContext(update, user));
        sendMessage(messageToTelegram);
    }

    /**
     * Обрабатывает сообщения из очереди фото сообщений
     *
     * @param json представление объекта {@link TransferByteObject}
     */
    @Override
    @RabbitListener(queues = "${rabbitQueue.messages.update.PHOTO}")
    public void consumerPhotoMessageUpdates(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TransferByteObject transferByteObject = objectMapper.readValue(json, TransferByteObject.class);

            User user = userService.findByChatId(Long.valueOf(transferByteObject.getChatId()));
            MessageToTelegram messageToTelegram = null;
            if (user.getState().equals(State.WAITING_FOR_PHOTOS_FOR_DAILY_REPORT)) {
                messageToTelegram = savePhotoDailyReportCommand.execute(new CommandPhotoContext(
                        transferByteObject,
                        user
                ));
            }
            sendMessage(messageToTelegram);
        } catch (Exception e) {
            log.error("Не удалось десериализовать");
        }
    }


    private void sendMessage(MessageToTelegram messageToTelegram) {
        if (messageToTelegram != null) {
            if (messageToTelegram.getSendMessages() != null) {
                messageToTelegram.getSendMessages().forEach(producerService::producerAnswer);
            }
            if (messageToTelegram.getTransferByteObjects() != null) {
                messageToTelegram.getTransferByteObjects().forEach(producerService::producerAnswer);
            }
        }
    }


}
