package ru.teamfour.dispatcher.service.impl;

import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dispatcher.configuration.telegram.BotConfig;
import ru.teamfour.dispatcher.service.api.UpdateProducer;
import transfer.TransferByteObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
@Data
public class UpdateProducerImpl implements UpdateProducer {

    private final RabbitTemplate rabbitTemplate;
    private final BotConfig botConfig;

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
            String fileUrl = "https://api.telegram.org/bot" + botConfig.getToken() + "/getFile?file_id=" + fileId;

            try {
                // Получаем массив байтов из URL файла
                photoBytes = readBytesFromUrl(fileUrl);
                // Теперь photoBytes содержит массив байтов изображения
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        TransferByteObject transferByteObject = new TransferByteObject(
                update.getMessage().getChatId().toString(),
                photoBytes
        );
        rabbitTemplate.convertAndSend(rabbitQueue, transferByteObject);
    }

    /**
     * Метод для чтения массива байтов из URL
     *
     * @param fileUrl
     * @return
     * @throws IOException
     */
    private static byte[] readBytesFromUrl(String fileUrl) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = new URL(fileUrl).openStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        return outputStream.toByteArray();
    }
}
