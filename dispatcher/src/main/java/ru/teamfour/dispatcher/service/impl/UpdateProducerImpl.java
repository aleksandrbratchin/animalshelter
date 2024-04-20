package ru.teamfour.dispatcher.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;
import ru.teamfour.dispatcher.configuration.telegram.BotConfig;
import ru.teamfour.dispatcher.model.FileResponse;
import ru.teamfour.dispatcher.service.api.UpdateProducer;
import transfer.TransferByteObject;

@Service
@Getter
@Setter
@Log4j2
public class UpdateProducerImpl implements UpdateProducer {

    private final RabbitTemplate rabbitTemplate;
    private final BotConfig botConfig;
    private final WebClient webClient;

    public UpdateProducerImpl(RabbitTemplate rabbitTemplate, BotConfig botConfig, WebClient.Builder webClientBuilder) {
        this.rabbitTemplate = rabbitTemplate;
        this.botConfig = botConfig;
        this.webClient = webClientBuilder
                .baseUrl("https://api.telegram.org")
                .build();
    }

    @Override
    public void produce(String rabbitQueue, Update update) {
        rabbitTemplate.convertAndSend(rabbitQueue, update);
    }

    @Override
    public void producePhoto(String rabbitQueue, Update update) {
        // Получаем информацию о фотографии из объекта Update
        // Сортируем по размеру
        PhotoSize photo = update.getMessage().getPhoto().stream().min((p1, p2) -> Integer.compare(p2.getFileSize(), p1.getFileSize()))
                .orElse(null);
        byte[] photoBytes = null;
        if (photo != null) {
            // Получаем URL файла фотографии
            String fileId = photo.getFileId();
            try {
                // Получаем массив байтов из URL файла
                photoBytes = downloadFileBytes(fileId);//readBytesFromUrl(fileUrl);
                // Теперь photoBytes содержит массив байтов изображения
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        TransferByteObject transferByteObject = new TransferByteObject(
                update.getMessage().getChatId().toString(),
                photoBytes
        );
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            rabbitTemplate.convertAndSend(rabbitQueue, objectMapper.writeValueAsString(transferByteObject));
        } catch (JsonProcessingException e) {
            log.error("Ошибка при сериализации");
        }
    }


    private byte[] downloadFileBytes(String fileId) {
        // Формируем URL для получения информации о файле
        String getFileUrl = "/bot" + botConfig.getToken() + "/getFile?file_id=" + fileId;

        return webClient.get()
                .uri(getFileUrl)
                .retrieve()
                .bodyToMono(FileResponse.class)
                .flatMap(fileResponse -> {
                    if (fileResponse != null && fileResponse.isOk() && fileResponse.getResult() != null) {
                        String fileUrl = "/file/bot" + botConfig.getToken() + "/" + fileResponse.getResult().getFilePath();

                        // Выполняем GET-запрос для загрузки файла в виде массива байтов
                        return webClient.get()
                                .uri(fileUrl)
                                .retrieve()
                                .bodyToMono(byte[].class);
                    } else {
                        return Mono.empty(); // Возвращаем пустой Mono в случае ошибки
                    }
                })
                .block();
    }

}
