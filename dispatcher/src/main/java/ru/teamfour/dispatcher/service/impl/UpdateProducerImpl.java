package ru.teamfour.dispatcher.service.impl;

import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dispatcher.service.api.UpdateProducer;

@Service
@Data
public class UpdateProducerImpl implements UpdateProducer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void produce(String rabbitQueue, Update update) {
        System.out.println(update.getMessage().getText() + 10);
        rabbitTemplate.convertAndSend(rabbitQueue, update);
    }

}
