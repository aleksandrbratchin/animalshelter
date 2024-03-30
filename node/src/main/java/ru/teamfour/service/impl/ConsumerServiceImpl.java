package ru.teamfour.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.api.ConsumerService;
import ru.teamfour.service.api.ProducerService;
import ru.teamfour.service.impl.user.UserService;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.TextCommand;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.HandlersState;
import ru.teamfour.textcommand.handler.api.HandlersStateFactory;
import ru.teamfour.textcommand.handler.impl.HandlersRoleFactory;
import yamlpropertysourcefactory.YamlPropertySourceFactory;

@Log4j2
@Service
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
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
        Handler handler = handlers.getHandler();
        TextCommand command = handler.handleRequest(update);

        log.info(command.getClass());
        producerService.producerAnswer(command.execute(new CommandContext(update, user)));

    }

}
