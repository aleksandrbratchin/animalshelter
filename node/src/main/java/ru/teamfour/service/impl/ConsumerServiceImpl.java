package ru.teamfour.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.api.ConsumerService;
import ru.teamfour.service.api.ProducerService;
import ru.teamfour.service.impl.user.UserService;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.Command;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.HandlersState;
import ru.teamfour.textcommand.handler.api.HandlersStateFactory;
import ru.teamfour.textcommand.handler.impl.HandlersRoleFactory;
import transfer.TransferByteObject;

@Log4j2
@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final ProducerService producerService;
    private final HandlersRoleFactory handlersRoleFactory;
    private final UserService userService;

    public ConsumerServiceImpl(ProducerService producerService, HandlersRoleFactory handlersRoleFactory, UserService userService) {
        this.producerService = producerService;
        this.handlersRoleFactory = handlersRoleFactory;
        this.userService = userService;
    }

    @Override
    @RabbitListener(queues = "${rabbitQueue.messages.update.TEXT}")
    public void consumerTextMessageUpdates(Update update) {
        User user = userService.findByUserByChatIdOrCreateUser(update);

        HandlersStateFactory handlersStateFactory = handlersRoleFactory.getHandlers(user.getRole());
        HandlersState handlers = handlersStateFactory.getHandlers(user.getState());
        if (handlers == null) {
            log.error("Не удалось получить меню " + user.getState());
            return;
        }
        Handler handler = handlers.getHandler();
        Command command = handler.handleRequest(update);

        MessageToTelegram messageToTelegram = command.execute(new CommandContext(update, user));
        if (messageToTelegram.getSendMessages() != null) {
            messageToTelegram.getSendMessages().forEach(producerService::producerAnswer);
        }
        if (messageToTelegram.getTransferByteObjects() != null) {
            messageToTelegram.getTransferByteObjects().forEach(producerService::producerAnswer);
        }
    }

    @Override
    @RabbitListener(queues = "${rabbitQueue.messages.update.PHOTO}")
    public void consumerPhotoMessageUpdates(TransferByteObject transferByteObject) {
        User user = userService.findByChatId(Long.valueOf(transferByteObject.getChatId()));
        //todo обработка фото
        log.info("Принято фото в node");
    }


}
