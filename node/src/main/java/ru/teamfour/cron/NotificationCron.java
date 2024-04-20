package ru.teamfour.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.teamfour.dao.entity.user.Chat;
import ru.teamfour.repositories.AdoptionProcessAnimalRepository;
import ru.teamfour.service.api.ProducerService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationCron {
    private final ProducerService producerService;
    private final AdoptionProcessAnimalRepository repository;

    public NotificationCron(ProducerService producerService, AdoptionProcessAnimalRepository repository) {
        this.producerService = producerService;
        this.repository = repository;
    }

    @Scheduled(cron = "* * 21 * * *")
    public void doReminder() {
        List<Chat> list = new ArrayList<>();
        list = repository.findAll().stream()
                .filter(volunteer -> volunteer.getDate() != LocalDate.now())
                .map(vol -> vol.getUser().getChat())
                .collect(Collectors.toList());
        list.stream().forEach(chat -> producerService
                .producerAnswer(chat, "Отправьте, пожалуйста отчет о усыновлении."));

    }

}
