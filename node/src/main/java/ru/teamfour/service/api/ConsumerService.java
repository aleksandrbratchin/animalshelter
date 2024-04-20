package ru.teamfour.service.api;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.telegram.telegrambots.meta.api.objects.Update;
import transfer.TransferByteObject;

/***
 * Для считывания сообщений из брокера
 */
public interface ConsumerService {

    /**
     * Прием и обработка текстовых сообщений
     * @param update {@link Update}
     */
    void consumerTextMessageUpdates(Update update);

    /**
     * Прием и обработка фото сообщений
     *
     * @param transferByteObject {@link TransferByteObject}
     */
    //void consumerPhotoMessageUpdates(TransferByteObject transferByteObject);

    //todo не лучшая реализация. просто чтобы быстро работало. будет время переделать
/*    @Override
    @RabbitListener(queues = "${rabbitQueue.messages.update.PHOTO}")
    public void consumerPhotoMessageUpdates(TransferByteObject transferByteObject) {
        User user = userService.findByChatId(Long.valueOf(transferByteObject.getChatId()));
        MessageToTelegram messageToTelegram = null;
        if (user.getState().equals(State.WAITING_FOR_PHOTOS_FOR_DAILY_REPORT)) {
            messageToTelegram = savePhotoDailyReportCommand.execute(new CommandPhotoContext(
                    transferByteObject,
                    user
            ));
        }
        sendMessage(messageToTelegram);
    }  */

    void consumerPhotoMessageUpdates(String json);
}
