package ru.teamfour.textcommand.command.impl.volunteer.volunteerchat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.myutils.MessageUtils;
import ru.teamfour.service.impl.user.UserService;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.menu.MenuButtonFactory;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
class EndChatClientCommandTest {

    @Value("${buttonName.adoption}")
    private String checkButtonClient;

    @Value("${buttonName.endChatWithClient}")
    private String buttonName;
    @InjectMocks
    private EndChatClientCommand testingCommand;

    @MockBean
    private UserService userService;

    @SpyBean
    private MessageUtils messageUtils;

    @SpyBean
    private MenuButtonFactory menuFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(testingCommand, "endChatWithClient", buttonName);
    }

    @Test
    void execute() {
        // Arrange
        CommandContext commandContext = mock(CommandContext.class);
        long chatId = 1_000_000L;
        long activeChat = 1_000_001L;
        Chat chat = new Chat();
        chat.setId(chatId);
        Message message = new Message();
        message.setChat(chat);
        Update update = new Update();
        update.setMessage(message);
        User volunteer = User.builder()
                .chatId(chatId)
                .chat(ru.teamfour.dao.entity.user.Chat.builder()
                        .activeChat(activeChat)
                        .build())
                .build();
        User client = User.builder()
                .chatId(activeChat)
                .chat(ru.teamfour.dao.entity.user.Chat.builder()
                        .activeChat(chatId)
                        .build())
                .build();
        when(commandContext.getUser()).thenReturn(volunteer);
        when(commandContext.getUpdate()).thenReturn(update);
        when(userService.findByChatId(any())).thenReturn(client);

        // Act
        MessageToTelegram result = testingCommand.execute(commandContext);

        // Assert
        assertThat(result.getSendMessages()).hasSize(2);

        SendMessage clientSendMessage = result.getSendMessages().stream()
                .filter(sendMessage -> sendMessage.getChatId().equals(String.valueOf(activeChat)))
                .findFirst().orElse(null);
        SendMessage volunteerSendMessage = result.getSendMessages().stream()
                .filter(sendMessage -> sendMessage.getChatId().equals(String.valueOf(chatId)))
                .findFirst().orElse(null);
        assertThat(clientSendMessage).isNotNull();
        assertThat(volunteerSendMessage).isNotNull();
        assertThat(volunteerSendMessage.getText()).contains("Вы завершили чат");
        assertThat(clientSendMessage.getText()).contains("Волонтер завершил чат");

        ReplyKeyboardMarkup replyMarkupClient = (ReplyKeyboardMarkup) clientSendMessage.getReplyMarkup();
        assertThat(replyMarkupClient.getKeyboard().size()).isEqualTo(3);
        List<String> nameButtons = replyMarkupClient.getKeyboard()
                .stream()
                .flatMap(Collection::stream)
                .map(KeyboardButton::getText)
                .toList();
        assertThat(nameButtons).hasSize(5);
        assertThat(nameButtons).contains(checkButtonClient);

        ReplyKeyboardRemove volunteerVolunteer = (ReplyKeyboardRemove) volunteerSendMessage.getReplyMarkup();
        assertTrue(volunteerVolunteer.getRemoveKeyboard());
        verify(userService).saveAll(any());
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