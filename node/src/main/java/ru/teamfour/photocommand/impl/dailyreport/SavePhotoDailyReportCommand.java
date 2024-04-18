package ru.teamfour.photocommand.impl.dailyreport;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.dao.entity.photoreport.PhotoReport;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.photocommand.CommandPhotoContext;
import ru.teamfour.photocommand.api.AbstractPhotoCommand;
import ru.teamfour.service.api.dailyreport.DailyReportService;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.api.State;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сохранить фато животного в отчет
 */
@Component
public class SavePhotoDailyReportCommand extends AbstractPhotoCommand {

    private final DailyReportService dailyReportService;

    public SavePhotoDailyReportCommand(DailyReportService dailyReportService) {
        this.dailyReportService = dailyReportService;
    }

    @Override
    public MessageToTelegram execute(CommandPhotoContext commandContext) {

        User user = commandContext.getUser();
        State state = State.PET_REPORT;
        user.setState(state);
        userService.save(user);

        AdoptionProcessAnimal activeAdoptionProcess = user.getActiveAdoptionProcess();
        DailyReport lastDailyReport = activeAdoptionProcess.getLastDailyReport(LocalDate.now());
        DailyReport dailyReport = Optional.ofNullable(lastDailyReport).orElseGet(() -> DailyReport.builder().adoptionProcessAnimal(activeAdoptionProcess).build());
        PhotoReport photoReport = new PhotoReport(commandContext.getTransferByteObject().getData());
        dailyReport.setPhotoReport(photoReport);
        dailyReportService.save(dailyReport);

        //todo обработать и сохранить фото

        SendMessage sendMessage = messageUtils.generateSendMessageWithText(
                Long.valueOf(commandContext.getTransferByteObject().getChatId()),
                "Фото отправлено!");
        List<SendMessage> sendMessages = new ArrayList<>();
        sendMessages.add(addMenu(sendMessage, state));
        return MessageToTelegram.builder()
                .sendMessages(sendMessages)
                .build();
    }
}
