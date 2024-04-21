package ru.teamfour.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalCreateDto;
import ru.teamfour.repositories.AdoptionProcessAnimalRepository;
import ru.teamfour.repositories.UserRepository;
import ru.teamfour.service.api.ProducerService;
import ru.teamfour.service.impl.user.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationCron {
    private final ProducerService producerService;
    private final AdoptionProcessAnimalRepository repository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final AdoptionProcessAnimalCreateDto process;

    public NotificationCron(ProducerService producerService, AdoptionProcessAnimalRepository repository, UserService userService, UserRepository userRepository, AdoptionProcessAnimalCreateDto process) {
        this.producerService = producerService;
        this.repository = repository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.process = process;
    }

    @Scheduled(cron = "0 0 21 * * *")
    public void doReminderReport() {



        /* repository.findAll().stream()
                .filter(parent ->( parent.getDailyReports()
                        .getLast().getDate_report().getDayOfMonth()
                        != LocalDate.now().getDayOfMonth())||parent.getDailyReports().isEmpty())
                .map(parent -> parent.getUser().getChat().toString())
                .forEach(chat -> producerService
                        .producerAnswer(new SendMessage
                                (chat, "Отправьте, пожалуйста, отчет о усыновлении.")));

*/
    }

    @Scheduled(cron = "0 29 * * * *")
    public void doReminderMe() {
        LocalDate date = LocalDate.now();
        User user = userRepository.findAll().getLast();
        if (date.equals(LocalDate.now())) {
            producerService.producerAnswer(new SendMessage(user.getChatId().toString(), date.toString()));
        }
    }

    @Scheduled(cron = "0 0 22 * * *")
    public void doReminderCheckReport() {
        User user = userService.getAvailableVolunteer();
        producerService
                .producerAnswer(new SendMessage
                        (user.getChatId().toString(),
                                "Уважаемый волонтер, настало время проверять отчеты от усыновителей."));


    }

    @Scheduled(cron = "0 0 23 * * *")
    public void doReminderBadReport() {
        User volunteer = userService.getAvailableVolunteer();
        List<User> list = new ArrayList<>();
        repository.findAll().stream()
                .filter(parent -> parent.getDailyReports()
                        .getLast().getDate_report().getDayOfMonth()
                        < (LocalDate.now().getDayOfMonth() - 1))
                .map(parent -> parent.getUser())
                .forEach(parent -> producerService
                        .producerAnswer(new SendMessage
                                (volunteer.getChatId().toString(),
                                        "Уважаемый волонтер, обратите внимание," +
                                                " что усыновитель " + parent.getUserInfo().getFirstName() +
                                                " " + parent.getUserInfo().getLastName() +
                                                " уже два дня не сдавал отчет!")));


    }
}
