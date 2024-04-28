package ru.teamfour.textcommand.command.impl.client.shelterinformation;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.drivingdirections.DrivingDirections;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.dao.entity.user.UserInfo;
import ru.teamfour.repositories.DrivingDirectionsRepository;
import ru.teamfour.service.impl.drivingdirections.DrivingDirectionsServiceImpl;
import ru.teamfour.service.impl.user.UserService;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.MessageToTelegram;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
class DrivingDirectionsCommandTest {

    @Value("${buttonName.drivingDirections}")
    private String buttonName;

    @Autowired
    private DrivingDirectionsCommand testingCommand;

    @MockBean
    private UserService userService;
    @InjectMocks
    private DrivingDirectionsServiceImpl drivingDirectionsService;

    @MockBean
    private DrivingDirectionsRepository drivingDirectionsRepository;

    @Nested
    class Execute {

        @Test
        void photoIsNotPresent() {
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
                    .shelter(
                            Shelter.builder()
                                    .id(UUID.randomUUID())
                                    .build()
                    )
                    .build();
            when(commandContext.getUser()).thenReturn(user);
            when(commandContext.getUpdate()).thenReturn(update);
            when(drivingDirectionsRepository.findByShelterId(any(UUID.class))).thenReturn(
                    Optional.empty()
            );

            // Act
            MessageToTelegram result = testingCommand.execute(commandContext);

            // Assert
            assertThat(result.getSendMessages()).hasSize(1);
            SendMessage first = result.getSendMessages().getFirst();
            assertThat(first.getChatId()).isEqualTo(String.valueOf(chatId));
            assertThat(first.getText()).contains("Отсутствует изображение для отправки");
        }

        @Test
        void photoIsPresent() {
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
                    .shelter(
                            Shelter.builder()
                                    .id(UUID.randomUUID())
                                    .build()
                    )
                    .userInfo(UserInfo.builder().phoneNumber("").build())
                    .build();
            when(commandContext.getUser()).thenReturn(user);
            when(commandContext.getUpdate()).thenReturn(update);
            when(drivingDirectionsRepository.findByShelterId(any(UUID.class))).thenReturn(
                    Optional.of(
                            DrivingDirections.builder()
                                    .data(new byte[]{0, 1})
                                    .build()
                    )
            );

            // Act
            MessageToTelegram result = testingCommand.execute(commandContext);

            // Assert
            assertThat(result.getSendMessages()).isNull();
            assertThat(result.getTransferByteObjects()).isNotNull();
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