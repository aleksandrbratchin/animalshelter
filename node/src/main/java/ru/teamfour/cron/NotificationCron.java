package ru.teamfour.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessStatus;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.repositories.AdoptionProcessAnimalRepository;
import ru.teamfour.service.api.ProducerService;
import ru.teamfour.service.impl.user.UserService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Component
public class NotificationCron {
    private final ProducerService producerService;
    private final AdoptionProcessAnimalRepository repository;
    private final UserService userService;

    public NotificationCron(ProducerService producerService, AdoptionProcessAnimalRepository repository, UserService userService) {
        this.producerService = producerService;
        this.repository = repository;
        this.userService = userService;
    }

    /**
     * метод проверяет наличие отчета о усыновлении
     * за текущий день. Если отчета нет, метод отсылает напоминание усыновителю
     */
    @Scheduled(cron = "0 0 21 * * *")
    public void doReminderReport() {
        repository.findByAdoptionProcessStatus(AdoptionProcessStatus.PROCESS_ADOPTION).stream()
                .filter(
                        parent -> !parent.getDailyReports().getLast().getDate_report().isEqual(LocalDate.now())
                )
                .map(parent -> parent.getUser().getChat().toString())
                .forEach(
                        chat -> producerService.producerAnswer(
                                new SendMessage(chat, "Отправьте, пожалуйста, отчет о усыновлении.")
                        )
                );

    }

    /**
     * метод отсылает напоминание всем волонтерам
     * о проверке отчетов об усыновлении
     **/
    @Scheduled(cron = "0 0 21 * * *")
    public void doReminderCheckReport() {
        userService.getUsersByRole(RoleUser.VOLUNTEER)
                .forEach(
                        user ->
                                producerService.producerAnswer(
                                        new SendMessage(
                                                user.getChatId().toString(),
                                                "Уважаемый волонтер, настало время проверять отчеты от усыновителей.")
                                )
                );

    }

    /**
     * метод проверяет наличие отчетов об усыновлении за два дня,
     * если таковых нет, идет уведомление наиболее свободному волонтеру
     */
    @Scheduled(cron = "0 0 22 * * *")
    public void doReminderBadReport() {
        User volunteer = userService.getAvailableVolunteer();
        repository.findByAdoptionProcessStatus(AdoptionProcessStatus.PROCESS_ADOPTION).stream()
                .filter(parent ->
                        !(new HashSet<>(parent.getDailyReports().stream()
                                .map(DailyReport::getDate_report)
                                .toList())
                                .containsAll(List.of(LocalDate.now()
                                        .minusDays(1), LocalDate.now()))))
                .map(AdoptionProcessAnimal::getUser)
                .forEach(parent -> producerService
                        .producerAnswer(new SendMessage
                                (volunteer.getChatId().toString(),
                                        "Уважаемый волонтер, обратите внимание," +
                                                " что усыновитель " + parent.getUserInfo().getFirstName() +
                                                " " + parent.getUserInfo().getLastName() +
                                                " уже два дня не сдавал отчет!")));

    }
}
