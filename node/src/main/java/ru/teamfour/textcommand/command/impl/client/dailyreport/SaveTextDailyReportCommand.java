package ru.teamfour.textcommand.command.impl.client.dailyreport;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.dao.entity.dailyreport.DailyReportStatus;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.api.dailyreport.DailyReportService;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.api.State;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * сохранить отчет о содержании
 */
@Component
public class SaveTextDailyReportCommand extends AbstractCommand {

    private final DailyReportService dailyReportService;

    public SaveTextDailyReportCommand(DailyReportService dailyReportService) {
        this.dailyReportService = dailyReportService;
    }

    @Override
    @Transactional
    public MessageToTelegram execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        Update update = commandContext.getUpdate();
        State state = State.PET_REPORT;
        user.setState(state);


        AdoptionProcessAnimal activeAdoptionProcess = user.getActiveAdoptionProcess();
        DailyReport lastDailyReport = activeAdoptionProcess.getLastDailyReport(LocalDate.now());
        DailyReport dailyReport = Optional.ofNullable(lastDailyReport)
                .orElseGet(() -> DailyReport.builder()
                        .adoptionProcessAnimal(activeAdoptionProcess)
                        .build());
        dailyReport.setReportText(update.getMessage().getText());
        dailyReportService.save(dailyReport);
        userService.save(user);
        //todo сохранить текст отчета

        String answerMessage = "Отчет отправлен!";
        SendMessage sendMessage = messageUtils.generateSendMessageWithText(update, answerMessage);
        List<SendMessage> sendMessages = new ArrayList<>();
        sendMessages.add(addMenu(sendMessage, state));
        return MessageToTelegram.builder()
                .sendMessages(sendMessages)
                .build();

    }

    @Override
    public boolean isCommand(String message) {
        return true;
    }

}
