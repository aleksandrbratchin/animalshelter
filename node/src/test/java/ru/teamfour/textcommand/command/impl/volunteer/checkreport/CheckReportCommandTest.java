package ru.teamfour.textcommand.command.impl.volunteer.checkreport;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.dao.entity.dailyreport.DailyReportStatus;
import ru.teamfour.dao.entity.photoreport.PhotoReport;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.impl.dailyreport.DailyReportService;
import ru.teamfour.service.impl.user.UserService;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.MessageToTelegram;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
class CheckReportCommandTest {

    @Value("${buttonName.checkReport}")
    private String buttonName;

    @Autowired
    private CheckReportCommand testingCommand;

    @MockBean
    private UserService userService;

    @MockBean
    private DailyReportService dailyReportService;

    @MockBean
    private CacheManager cacheManager;

    @MockBean
    private Cache cache;

    @Test
    public void noReportsToCheck() {
        // Arrange
        CommandContext commandContext = mock(CommandContext.class);
        long chatId = 1_000_000L;
        Chat chat = new Chat();
        chat.setId(chatId);
        Message message = new Message();
        message.setChat(chat);
        Update update = new Update();
        update.setMessage(message);
        User user = User.builder()
                .chatId(chatId)
                .build();
        when(commandContext.getUser()).thenReturn(user);
        when(commandContext.getUpdate()).thenReturn(update);
        when(dailyReportService.findByReportStatus(DailyReportStatus.IN_PROCESSING)).thenReturn(Collections.emptyList());
        when(userService.updateInfoAndState(any(), any(), any())).thenReturn(User.builder().build());

        // Act
        MessageToTelegram execute = testingCommand.execute(commandContext);

        // Assert
        assertThat(execute.getSendMessages()).hasSize(1);
        assertThat(execute.getSendMessages().get(0).getText()).isEqualTo("Нет отчетов которые надо проверить");
        verify(cacheManager, never()).getCache("checkadoption");
    }

    @Test
    public void reportsToCheck() {
        // Arrange
        CommandContext commandContext = mock(CommandContext.class);
        long chatId = 1_000_000L;
        Chat chat = new Chat();
        chat.setId(chatId);
        Message message = new Message();
        message.setChat(chat);
        Update update = new Update();
        update.setMessage(message);
        User user = User.builder()
                .chatId(chatId)
                .build();
        when(commandContext.getUser()).thenReturn(user);
        when(commandContext.getUpdate()).thenReturn(update);
        DailyReport dailyReport = new DailyReport();
        dailyReport.setId(UUID.randomUUID());
        dailyReport.setReportText("Текст отчета");
        PhotoReport photoReport = new PhotoReport();
        photoReport.setData(new byte[]{1, 2, 3});
        dailyReport.setPhotoReport(photoReport);
        when(dailyReportService.findByReportStatus(DailyReportStatus.IN_PROCESSING)).thenReturn(Collections.singletonList(dailyReport));
        when(cacheManager.getCache("checkadoption")).thenReturn(cache);
        when(userService.updateInfoAndState(any(), any(), any())).thenReturn(User.builder().build());

        // Act
        MessageToTelegram execute = testingCommand.execute(commandContext);

        // Assert
        assertThat(execute.getSendMessages()).hasSize(1);
        assertThat(execute.getSendMessages().get(0).getText()).isEqualTo("Текст отчета");
        assertThat(execute.getTransferByteObjects()).hasSize(1);
        assertThat(execute.getTransferByteObjects().get(0).getChatId()).isEqualTo("1000000");
        assertThat(execute.getTransferByteObjects().get(0).getData()).containsExactly((byte) 1, (byte) 2, (byte) 3);
        assertThat(cache).isNotNull();
        verify(cache, times(1)).put(user.getId(), dailyReport.getId());
    }

    @Nested
    class isCommand {
        @Nested
        class Correct {
            @Test
            void buttonName() {
                // Arrange
                String command = buttonName;

                // Act
                boolean result = testingCommand.isCommand(command);

                // Assert
                assertTrue(result);
            }
        }

        @Nested
        class Incorrect {
            @Test
            void incorrectCommand() {
                // Arrange
                String command = "test";

                // Act
                boolean result = testingCommand.isCommand(command);

                // Assert
                assertFalse(result);
            }
        }
    }

}