package ru.teamfour.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.service.api.ConsumerService;
import ru.teamfour.service.api.ProducerService;
import ru.teamfour.textcommand.command.api.State;
import ru.teamfour.textcommand.command.api.TextCommand;

import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.impl.HandlersFactory;
import yamlpropertysourcefactory.YamlPropertySourceFactory;

@Log4j2
@Service
@PropertySource(value = "classpath:rabbitQueueType.yml", factory = YamlPropertySourceFactory.class)
public class ConsumerServiceImpl implements ConsumerService {

    private final ProducerService producerService;
    private final HandlersFactory handlersFactory;

    public ConsumerServiceImpl(ProducerService producerService, HandlersFactory handlersFactory) {
        this.producerService = producerService;
        this.handlersFactory = handlersFactory;
    }

    @Override
    @RabbitListener(queues = "${rabbitQueue.messages.update.TEXT}")
    public void consumerTextMessageUpdates(Update update) {
        var message = update.getMessage().getText();
        //todo Потом будем получать прошлое состояние пользователя из БД
        State state = State.MAIN_MENU;

        Handler handler = handlersFactory.getHandlers(state).getHandler();
        TextCommand command = handler.handleRequest(update);

        log.info(command.getClass());
        producerService.producerAnswer(command.execute(update));

    }

}
