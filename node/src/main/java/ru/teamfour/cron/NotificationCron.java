package ru.teamfour.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessStatus;
import ru.teamfour.dao.entity.animal.AdoptionAnimalState;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalCreateDto;
import ru.teamfour.repositories.AdoptionProcessAnimalRepository;
import ru.teamfour.repositories.UserRepository;
import ru.teamfour.service.api.ProducerService;
import ru.teamfour.service.impl.user.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.DAYS;
import static org.apache.commons.lang3.BooleanUtils.forEach;
import static ru.teamfour.dao.entity.animal.AdoptionAnimalState.PROCESS_OF_ADOPTION;
import static ru.teamfour.dao.entity.user.RoleUser.VOLUNTEER;

@Component
public class NotificationCron {
   /* private final ProducerService producerService;
    private final AdoptionProcessAnimalRepository repository;
    private final UserService userService;


    public NotificationCron(ProducerService producerService, AdoptionProcessAnimalRepository repository, UserService userService) {
        this.producerService = producerService;
        this.repository = repository;
        this.userService = userService;


    }

    *//**
     * метод проверяет наличие отчета о усыновлении
     * за текущий день. Если отчета нет, метод отсылает напоминание усыновителю
     *//*

    @Scheduled(cron = "0 0 21 * * *")
    public void doReminderReport() {

        repository.findByAdoptionProcessStatus(AdoptionProcessStatus.PROCESS_ADOPTION).stream()
                .filter(parent -> !(parent.getDailyReports()
                        .stream()
                        .map(DailyReport::getDate_report)
                        .toList()
                        .contains(LocalDate.now())))
                //   .map(parent->parent.getUser().getChatId().toString())
                .forEach(parent -> producerService
                        .producerAnswer(new SendMessage
                                (parent.getUser().getChatId().toString(), "Отправьте, пожалуйста, отчет о усыновлении.")));


    }
   *//* public void doReminderReport1() {

        repository.findByAdoptionProcessStatus(AdoptionProcessStatus.PROCESS_ADOPTION).stream()
                .filter(parent -> !(parent.getDailyReports()
                        .stream()
                        .map(DailyReport::getDate_report)
                        .toList()
                        .contains(LocalDate.now())))
                  .map(parent->parent.getUser().getChatId().toString())
                .forEach(parent ->
                        System.out.println(parent));


    }*//*

     *//**
     * метод отсылает напоминание всем волонтерам
     * о проверке отчетов об усыновлении
     **//*

    @Scheduled(cron = "0 0 21 * * *")
    public void doReminderCheckReport() {
        userService.getUsersByRole(RoleUser.VOLUNTEER)
                .forEach(user ->
                        producerService
                                .producerAnswer(new SendMessage
                                        (user.getChatId().toString(),
                                                "Уважаемый волонтер, настало время проверять отчеты от усыновителей.")));

    }

    *//*public void doReminderCheckReport1() {
        List<String> list = new ArrayList<>();
        userService.getUsersByRole(VOLUNTEER)
                .forEach(user ->
                        System.out.println( user.getChatId().toString()));

    }*//*
     *//**
     * метод проверяет наличие отчетов об усыновлении за два дня,
     * если таковых нет, идет уведомление наиболее свободному волонтеру
     *//*
        @Scheduled(cron = "0 0 22 * * *")
        public void doReminderBadReport () {
            User volunteer = userService.getAvailableVolunteer();
            // List<User> list = new ArrayList<>();
            repository.findByAdoptionProcessStatus(AdoptionProcessStatus.PROCESS_ADOPTION).stream()
                    .filter(parent ->
                            !(parent.getDailyReports().stream()
                                    .map(DailyReport::getDate_report)
                                    .toList()
                                    .containsAll(List.of(LocalDate.now()
                                            .minusDays(1), LocalDate.now()))))

                    .map(parent -> parent.getUser())
                    .forEach(parent -> producerService
                            .producerAnswer(new SendMessage
                                    (volunteer.getChatId().toString(),
                                            "Уважаемый волонтер, обратите внимание," +
                                                    " что усыновитель " + parent.getUserInfo().getFirstName() +
                                                    " " + parent.getUserInfo().getLastName() +
                                                    " уже два дня не сдавал отчет!")));

        }*/
}
