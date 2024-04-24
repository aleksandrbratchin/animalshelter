package ru.teamfour.textcommand.command.impl.volunteer.checkreport;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.dao.entity.dailyreport.DailyReportStatus;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.api.dailyreport.DailyReportServiceApi;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.Command;
import ru.teamfour.textcommand.command.api.MessageToTelegram;

import java.util.UUID;

@Component
public class AcceptReportCommand extends AbstractCommand {

    @Value("${buttonName.acceptReport}")
    private String acceptReport;

    private final DailyReportServiceApi dailyReportService;
    private final CacheManager cacheManager;
    private final Command backToVolunteerMainMenuCommand;

    public AcceptReportCommand(
            @Qualifier("dailyReportService") DailyReportServiceApi dailyReportService,
            CacheManager cacheManager,
            @Qualifier("backToVolunteerMainMenuCommand") Command backToVolunteerMainMenuCommand) {
        this.dailyReportService = dailyReportService;
        this.cacheManager = cacheManager;
        this.backToVolunteerMainMenuCommand = backToVolunteerMainMenuCommand;
    }

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        User user = commandContext.getUser();
        DailyReport dailyReport = null;
        Cache cache = cacheManager.getCache("checkadoption");
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(user.getId());
            if (valueWrapper != null) {
                UUID dailyReportId = (UUID) valueWrapper.get();
                dailyReport = dailyReportService.findById(dailyReportId);
                cache.evict(user.getId());
            }
        }
        MessageToTelegram messageToTelegram;
        //нет отчета в кеше сообщить о проблеме
        if (dailyReport == null) {
            String msg = "Не удалось принять решение.";
            messageToTelegram = backToVolunteerMainMenuCommand.execute(commandContext);
            messageToTelegram.getSendMessages().get(0).setText(msg);
        } else { //принять отчет
            dailyReport.setReportStatus(DailyReportStatus.APPROVED);
            dailyReportService.save(dailyReport);
            String msg = "Отчёт одобрен!";
            messageToTelegram = backToVolunteerMainMenuCommand.execute(commandContext);
            messageToTelegram.getSendMessages().get(0).setText(msg);
        }
        return messageToTelegram;

    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(acceptReport);
    }

}
