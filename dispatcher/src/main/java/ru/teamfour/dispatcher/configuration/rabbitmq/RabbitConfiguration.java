package ru.teamfour.dispatcher.configuration.rabbitmq;

import lombok.Getter;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Getter
@Configuration
public class RabbitConfiguration {

    @Value("${rabbitQueue.messages.update.TEXT}")
    private String text;

    @Value("${rabbitQueue.messages.update.PHOTO}")
    private String photo;

    @Value("${rabbitQueue.messages.answer.TEXT}")
    private String answerText;

    @Value("${rabbitQueue.messages.answer.PHOTO}")
    private String answerPhoto;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue textMessageQueue() {
        return new Queue(text);
    }

    @Bean
    public Queue photoMessageQueue() {
        return new Queue(photo);
    }

    @Bean
    public Queue answerMessageQueue() {
        return new Queue(answerText);
    }

    @Bean
    public Queue answerMessagePhotoQueue() {
        return new Queue(answerPhoto);
    }


}
