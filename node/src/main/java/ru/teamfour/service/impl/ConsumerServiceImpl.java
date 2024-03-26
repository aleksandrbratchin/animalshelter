package ru.teamfour.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.dao.entity.user.Users;
import ru.teamfour.service.api.ConsumerService;
import ru.teamfour.service.api.ProducerService;
import ru.teamfour.service.impl.user.UserService;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.command.api.TextCommand;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.api.Handlers;
import ru.teamfour.textcommand.handler.impl.HandlersFactory;
import yamlpropertysourcefactory.YamlPropertySourceFactory;

@Log4j2
@Service
@PropertySource(value = "classpath:rabbitQueueType.yml", factory = YamlPropertySourceFactory.class)
public class ConsumerServiceImpl implements ConsumerService {

    private final ProducerService producerService;
    private final HandlersFactory handlersFactory;
    private final UserService userService;

    public ConsumerServiceImpl(ProducerService producerService, HandlersFactory handlersFactory, UserService userService) {
        this.producerService = producerService;
        this.handlersFactory = handlersFactory;
        this.userService = userService;
    }

    @Override
    @RabbitListener(queues = "${rabbitQueue.messages.update.TEXT}")
    public void consumerTextMessageUpdates(Update update) {
        var message = update.getMessage().getText();
        var chatId = update.getMessage().getChat().getId();

        Users users = userService.findByChatId(chatId); //todo почему orElse некорректно отрабатывает?
        if(users == null){
            userService.save(
                    Users.builder()
                            .chatId(chatId)
                            .state(State.MAIN_MENU)
                            .role(RoleUser.CLIENT)
                            .build()
            );
        }

        State state = users.getState();

        Handlers handlers = handlersFactory.getHandlers(state);
        Handler handler = handlers.getHandler();
        TextCommand command = handler.handleRequest(update);

        log.info(command.getClass());
        producerService.producerAnswer(command.execute(update));

    }

}
