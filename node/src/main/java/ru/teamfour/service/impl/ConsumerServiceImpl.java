package ru.teamfour.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.myutils.MessageUtils;
import ru.teamfour.service.api.ConsumerService;
import ru.teamfour.service.api.ProducerService;
import ru.teamfour.textcommand.command.api.StateCommand;
import ru.teamfour.textcommand.command.api.TextCommand;
import ru.teamfour.textcommand3.level.CommandFactory;
import ru.teamfour.textcommand.handler.api.Handler;
import ru.teamfour.textcommand.handler.impl.MenuHandlers;
import yamlpropertysourcefactory.YamlPropertySourceFactory;

@Log4j2
@Service
@PropertySource(value = "classpath:rabbitQueueType.yml", factory = YamlPropertySourceFactory.class)
public class ConsumerServiceImpl implements ConsumerService {

    private final ProducerService producerService;
    private final CommandFactory commandFactory;
    private final MessageUtils messageUtils;
    private final MenuHandlers menuHandlers;

    public ConsumerServiceImpl(ProducerService producerService, CommandFactory commandFactory, MessageUtils messageUtils, MenuHandlers menuHandlers) {
        this.producerService = producerService;
        this.commandFactory = commandFactory;
        this.messageUtils = messageUtils;
        this.menuHandlers = menuHandlers;
    }

    @Override
    @RabbitListener(queues = "${rabbitQueue.messages.update.TEXT}")
    public void consumerTextMessageUpdates(Update update) {
        var message = update.getMessage().getText();
        //todo Потом будем получать прошлое состояние пользователя из БД
        StateCommand stateCommand = StateCommand.MENU;

        Handler handler = menuHandlers.getHandler();
        TextCommand command = handler.handleRequest(update);
        log.info(command.getClass());
        producerService.producerAnswer(command.execute(update));
/*        try {
            command = commandFactory.getCommand(stateCommand, message);
        } catch (Exception e) {

        }
        if(command == null){
            producerService.producerAnswer(
                    messageUtils.generateSendMessageWithText(update,"Неизвестная команда: \"" + message + "\"")
            );
        } else {
            producerService.producerAnswer(command.execute(update));
        }*/
    }

}
