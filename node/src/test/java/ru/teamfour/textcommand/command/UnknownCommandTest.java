package ru.teamfour.textcommand.command;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.textcommand.command.api.MessageToTelegram;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
class UnknownCommandTest {

    @Value("${buttonName.unknownCommand}")
    private String unknownMessage;

    @Autowired
    private UnknownCommand testingCommand;

    @Test
    void execute() {
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

        // Act
        MessageToTelegram result = testingCommand.execute(commandContext);

        // Assert
        assertThat(result.getSendMessages()).hasSize(1);
        SendMessage first = result.getSendMessages().getFirst();
        assertThat(first.getText()).isEqualTo(unknownMessage);
    }

    @Test
    void isCommand() {

        // Act
        boolean result = testingCommand.isCommand("");

        // Assert
        assertTrue(result);
    }

}