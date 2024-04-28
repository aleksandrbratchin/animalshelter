package ru.teamfour.textcommand.command.impl.volunteer.checkreport;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.impl.dailyreport.DailyReportService;
import ru.teamfour.service.impl.user.UserService;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.impl.volunteer.volunteerchat.BackToVolunteerMainMenuCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
class RejectReportCommandTest {

    @Value("${buttonName.rejectReport}")
    private String buttonName;

    @Autowired
    private RejectReportCommand testingCommand;

    @MockBean
    private UserService userService;

    @MockBean
    private CacheManager cacheManager;

    @MockBean
    private BackToVolunteerMainMenuCommand backToVolunteerMainMenuCommand;

    @MockBean
    private DailyReportService dailyReportService;

    @Mock
    private Cache cache;

    @Nested
    class Execute {

        @Test
        void cacheIsNotNull() {
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
            when(cacheManager.getCache(any())).thenReturn(cache);
            UUID dailyReportId = UUID.randomUUID();
            DailyReport dailyReport = new DailyReport();
            dailyReport.setId(dailyReportId);
            when(dailyReportService.findById(dailyReportId)).thenReturn(dailyReport);
            when(cache.get(user.getId())).thenReturn(() -> dailyReportId);
            SendMessage sendMessage = new SendMessage();
            when(backToVolunteerMainMenuCommand.execute(any(CommandContext.class))).thenReturn(
                    MessageToTelegram.builder()
                            .sendMessages(
                                    new ArrayList<>() {{
                                        add(sendMessage);
                                    }}
                            )
                            .build()
            );
            when(dailyReportService.getClientChat(dailyReportId)).thenReturn(String.valueOf(UUID.randomUUID()));

            // Act
            MessageToTelegram result = testingCommand.execute(commandContext);

            // Assert
            assertThat(result.getSendMessages()).hasSize(2);
            List<String> list = result.getSendMessages().stream().map(SendMessage::getText).toList();
            assertTrue(list.stream().anyMatch(str -> str.contains("не одобрен")));
            assertTrue(list.stream().anyMatch(str -> str.contains("Дорогой усыновитель")));
        }

        @Test
        void cacheIsNull() {
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
            when(cacheManager.getCache(any())).thenReturn(null);
            SendMessage sendMessage = new SendMessage();
            when(backToVolunteerMainMenuCommand.execute(any(CommandContext.class))).thenReturn(
                    MessageToTelegram.builder()
                            .sendMessages(
                                    List.of(
                                            sendMessage
                                    )
                            )
                            .build()
            );


            // Act
            MessageToTelegram result = testingCommand.execute(commandContext);

            // Assert
            assertThat(result.getSendMessages()).hasSize(1);
            SendMessage first = result.getSendMessages().getFirst();
            assertThat(first.getText()).contains("Не удалось принять решение");
        }
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