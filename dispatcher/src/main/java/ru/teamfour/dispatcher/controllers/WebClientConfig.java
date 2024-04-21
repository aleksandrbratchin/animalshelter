package ru.teamfour.dispatcher.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
        // Настройте базовый URL (необязательно)
        //.baseUrl("https://api.telegram.org");
        // Другие настройки, например, таймауты, заголовки и т. д.
    }
}
