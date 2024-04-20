package ru.teamfour.textcommand.command.impl.volunteer.checkreport;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.dao.entity.dailyreport.DailyReportStatus;
import ru.teamfour.dao.entity.photoreport.PhotoReport;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.api.adoptionanimal.AdoptionProcessAnimalServiceApi;
import ru.teamfour.service.api.dailyreport.DailyReportServiceApi;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.api.State;
import transfer.TransferByteObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class CheckReportCommand extends AbstractCommand {

    @Value("${buttonName.checkReport}")
    private String checkReport;

    private final AdoptionProcessAnimalServiceApi adoptionProcessAnimalService;
    private final DailyReportServiceApi dailyReportService;

    public CheckReportCommand(
            @Qualifier("adoptionProcessAnimalService") AdoptionProcessAnimalServiceApi adoptionProcessAnimalService,
            @Qualifier("dailyReportService") DailyReportServiceApi dailyReportService) {
        this.adoptionProcessAnimalService = adoptionProcessAnimalService;
        this.dailyReportService = dailyReportService;
    }


    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        Long chatId = update.getMessage().getChat().getId();
        State state = State.CHECK_REPORT;
        user.setState(state);
        userService.updateInfoAndState(user, update, state);

        String answerMessage = "Нет отчетов которые надо проверить";
        List<SendMessage> sendMessages = new ArrayList<>();
        List<TransferByteObject> transferByteObjects = new ArrayList<>();
        List<DailyReport> byReportStatus = dailyReportService.findByReportStatus(DailyReportStatus.IN_PROCESSING);
        if (!byReportStatus.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(byReportStatus.size());
            DailyReport dailyReport = byReportStatus.get(randomIndex);

            answerMessage = Optional.ofNullable(dailyReport.getReportText()).orElse("Клиент еще не прислал отчет");

            PhotoReport photoReport = dailyReport.getPhotoReport();
            if (photoReport != null) {
                TransferByteObject transferByteObject = new TransferByteObject(chatId.toString(), dailyReport.getPhotoReport().getData());
                transferByteObjects.add(transferByteObject);
            } else {
                SendMessage sendPhotoMessage = messageUtils.generateSendMessageWithText(chatId, "Клиент еще не прислал фото");
                sendMessages.add(sendPhotoMessage);
            }

        }

        SendMessage sendMessage = messageUtils.generateSendMessageWithText(chatId, answerMessage);


        sendMessages.add(addMenu(sendMessage, state));
        return MessageToTelegram.builder()
                .transferByteObjects(transferByteObjects)
                .sendMessages(sendMessages)
                .build();
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(checkReport);
    }
}