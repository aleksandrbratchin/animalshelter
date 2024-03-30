package ru.teamfour.dispatcher.configuration.telegram;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import yamlpropertysourcefactory.YamlPropertySourceFactory;


@Data
@Component
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "bot")
public class BotConfig {
    private String name;
    private String token;
}
