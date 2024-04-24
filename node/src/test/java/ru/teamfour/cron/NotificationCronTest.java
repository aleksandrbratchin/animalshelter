package ru.teamfour.cron;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessStatus;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.repositories.AdoptionProcessAnimalRepository;
import ru.teamfour.service.api.ProducerService;
import ru.teamfour.service.impl.user.UserService;

import javax.annotation.processing.Generated;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NotificationCronTest {

    @MockBean
    private AdoptionProcessAnimalRepository repository;

    @MockBean
    private UserService userService;


    @InjectMocks
    private NotificationCron cron;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(cron, "repository", repository);
        ReflectionTestUtils.setField(cron, "userService", userService);
    }


    @Test
    public void doReminderCheckReportListTest() {
        User user1 = User.builder().role(RoleUser.VOLUNTEER).chatId(6164002556L).build();
        User user2 = User.builder().role(RoleUser.VOLUNTEER).chatId(6164002557L).build();
        List<User> list = new ArrayList<>(List.of(user1, user2));
        when(userService.getUsersByRole(any(RoleUser.class))).thenReturn(list);
        assertTrue(cron.doReminderCheckReportList().contains(user1.getChatId().toString()));
        assertTrue(cron.doReminderCheckReportList().contains(user2.getChatId().toString()));
        verify(userService, times(2)).getUsersByRole(any(RoleUser.class));

    }

    @Test
    public void doReminderReportListTest() {
        DailyReport report1 = DailyReport.builder().build();
        report1.setDate_report(LocalDate.now().minusDays(2L));
        DailyReport report2 = DailyReport.builder().build();
        report2.setDate_report(LocalDate.now().minusDays(3L));
        DailyReport report3 = DailyReport.builder().build();
        report3.setDate_report(LocalDate.now());

        AdoptionProcessAnimal processAnimal = AdoptionProcessAnimal.builder()
                .dailyReports(new ArrayList<>(List.of(report1, report2)))
                .user(User.builder().chatId(6164002556L).build())
                .build();
        List<AdoptionProcessAnimal> listProcess = new ArrayList<>(List.of(processAnimal));
        when(repository.findByAdoptionProcessStatus(any(AdoptionProcessStatus.class))).thenReturn(listProcess);
        assertTrue(cron.doReminderReportList().contains(processAnimal.getUser().getChatId().toString()));
        AdoptionProcessAnimal processAnimal1 = AdoptionProcessAnimal.builder()
                .dailyReports(new ArrayList<>(List.of(report3)))
                .user(User.builder().chatId(6164002557L).build())
                .build();
        List<AdoptionProcessAnimal> listProcess1 = new ArrayList<>(List.of(processAnimal1));
        assertFalse(cron.doReminderReportList().contains(processAnimal1.getUser().getChatId().toString()));

    }

    @Test
    public void doReminderBadReportListTest() {
        DailyReport report1 = DailyReport.builder().build();
        report1.setDate_report(LocalDate.now().minusDays(2L));
        DailyReport report2 = DailyReport.builder().build();
        report2.setDate_report(LocalDate.now().minusDays(3L));
        DailyReport report3 = DailyReport.builder().build();
        report3.setDate_report(LocalDate.now());
        AdoptionProcessAnimal processAnimal = AdoptionProcessAnimal.builder()
                .dailyReports(new ArrayList<>(List.of(report1, report2)))
                .user(User.builder().chatId(6164002556L).build())
                .build();
        List<AdoptionProcessAnimal> listProcess = new ArrayList<>(List.of(processAnimal));
        when(repository.findByAdoptionProcessStatus(any(AdoptionProcessStatus.class))).thenReturn(listProcess);
        assertTrue(cron.doReminderBadReportList().contains((processAnimal.getUser().getChatId().toString())));


        DailyReport report5 = DailyReport.builder().build();
        report5.setDate_report(LocalDate.now().minusDays(1L));
        DailyReport report4 = DailyReport.builder().build();
        report4.setDate_report(LocalDate.now());

        AdoptionProcessAnimal processAnimal1 = AdoptionProcessAnimal.builder()
                .dailyReports(new ArrayList<>(List.of(report4, report5)))
                .user(User.builder().chatId(6164002557L).build())
                .build();
        List<AdoptionProcessAnimal> listProcess1 = new ArrayList<>(List.of(processAnimal1));
        when(repository.findByAdoptionProcessStatus(any(AdoptionProcessStatus.class))).thenReturn(listProcess1);
        assertFalse(cron.doReminderBadReportList().contains((processAnimal1.getUser().getChatId().toString())));
    }
}