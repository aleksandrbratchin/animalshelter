package ru.teamfour.textcommand.command.impl.volunteer.volunteerchat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.impl.client.StartVolunteerCommand;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
class BackToVolunteerMainMenuCommandTest {

    @Value("${buttonName.backButton}")
    private String buttonName;

    @Value("${buttonName.sendText}")
    private String checkButton;

    @Autowired
    private BackToVolunteerMainMenuCommand testingCommand;

    @MockBean
    private StartVolunteerCommand startVolunteerCommand;

    @Test
    void SheltersIsPresent() {
        // Arrange
        CommandContext commandContext = mock(CommandContext.class);
        SendMessage sendMessage = new SendMessage("1", "test");
        when(startVolunteerCommand.execute(any(CommandContext.class))).thenReturn(
                new MessageToTelegram(
                        List.of(sendMessage),
                        null
                )
        );

        // Act
        MessageToTelegram result = testingCommand.execute(commandContext);

        // Assert
        assertThat(result.getSendMessages()).hasSize(1);
        verify(startVolunteerCommand).execute(commandContext);
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