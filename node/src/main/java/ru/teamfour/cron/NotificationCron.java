package ru.teamfour.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessStatus;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.repositories.AdoptionProcessAnimalRepository;
import ru.teamfour.service.api.ProducerService;
import ru.teamfour.service.impl.user.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.teamfour.dao.entity.user.RoleUser.VOLUNTEER;

@Component
public class NotificationCron {
    private final ProducerService producerService;
    private final AdoptionProcessAnimalRepository repository;
    private final UserService userService;


    public NotificationCron(ProducerService producerService,
                            AdoptionProcessAnimalRepository repository, UserService userService) {
        this.producerService = producerService;
        this.repository = repository;
        this.userService = userService;
    }

    /**
     * метод отсылает напоминание усыновителям, у которых нет отчета
     */
    @Scheduled(cron = "0 0 21 * * *")
    public void doReminderReport() {

        doReminderReportList().forEach(chat -> producerService
                .producerAnswer(new SendMessage
                        (chat, "Отправьте, пожалуйста, отчет о усыновлении.")));

    }

    /**
     * метод проверяет наличие отчета о усыновлении
     * за текущий день.
     */
    public List<String> doReminderReportList() {

        return repository.findByAdoptionProcessStatus(AdoptionProcessStatus.PROCESS_ADOPTION).stream()
                .filter(parent -> !(parent.getDailyReports()
                        .stream()
                        .map(DailyReport::getDate_report)
                        .toList()
                        .contains(LocalDate.now())))
                .map(parent -> parent.getUser().getChatId().toString())
                .collect(Collectors.toList());

    }

    /**
     * метод отсылает напоминание всем волонтерам
     * о проверке отчетов об усыновлении
     **/
    @Scheduled(cron = "0 0 21 * * *")
    public void doReminderCheckReport() {
        doReminderCheckReportList()
                .forEach(chat -> producerService
                        .producerAnswer(new SendMessage
                                (chat, "Уважаемый волонтер, настало время проверять отчеты от усыновителей.")
                        )
                );
    }

    /**
     * метод составляет список всех волонтеров
     */
    public List<String> doReminderCheckReportList() {
        return userService.getUsersByRole(VOLUNTEER).stream()
                .map(user -> user.getChatId().toString())
                .toList();
    }

    /**
     * идет уведомление наиболее свободному волонтеру
     * об усыновителях, у которых нет отчета два дня
     */
    @Scheduled(cron = "0 0 22 * * *")
    public void doReminderBadReport() {
        User volunteer = userService.getAvailableVolunteer();
        doReminderBadReportList()
                .forEach(parent ->
                        producerService.producerAnswer(
                                new SendMessage(
                                        volunteer.getChatId().toString(),
                                        "Уважаемый волонтер, обратите внимание," +
                                                " что усыновитель c чата" + parent +
                                                " уже два дня не сдавал отчет!"
                                )
                        )
                );

    }

    /**
     * метод составляет список усыновителей,
     * у которых нет отчета два дня
     */
    public List<String> doReminderBadReportList() {
        return repository.findByAdoptionProcessStatus(AdoptionProcessStatus.PROCESS_ADOPTION).stream()
                .filter(parent ->
                        !(new HashSet<>(parent.getDailyReports().stream()
                                .map(DailyReport::getDate_report)
                                .toList())
                                .containsAll(List.of(LocalDate.now()
                                        .minusDays(1), LocalDate.now()))))
                .map(parent -> parent.getUser().getChatId().toString())
                .collect(Collectors.toList());
    }

}
