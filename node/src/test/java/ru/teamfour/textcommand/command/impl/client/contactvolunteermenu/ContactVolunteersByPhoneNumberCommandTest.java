package ru.teamfour.textcommand.command.impl.client.contactvolunteermenu;

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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.dao.entity.user.UserInfo;
import ru.teamfour.myutils.MessageUtils;
import ru.teamfour.service.impl.user.UserService;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.menu.MenuButtonFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
public class ContactVolunteersByPhoneNumberCommandTest {
    @Value("${buttonName.contactVolunteersByPhoneNumber}")
    private String buttonName;
    @Value("${buttonName.contactVolunteersByNickname}")
    private String checkButton;
    @InjectMocks
    private ContactVolunteersByPhoneNumberCommand testingCommand;

    @MockBean
    private UserService userService;

    @SpyBean
    private MessageUtils messageUtils;

    @SpyBean
    private MenuButtonFactory menuFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(testingCommand, "buttonName", buttonName);
    }

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
        String shelterName = "Тестовое название приюта";
        User user = User.builder()
                .chatId(chatId)
                .userInfo(
                        UserInfo.builder()
                                .firstName("Потап")
                                .lastName("Потапович")
                                .phoneNumber("телефончик1")
                                .nickName("potapushka")
                                .build())
                .build();
        User user1 = User.builder()
                .chatId(chatId)
                .userInfo(
                        UserInfo.builder()
                                .firstName("Тихон")
                                .lastName("Тихонович")
                                .phoneNumber("телефончик2")
                                .nickName("tisha")
                                .build())
                .build();
        List<User> userList = new ArrayList<>(List.of(user, user1));


        when(commandContext.getUser()).thenReturn(user);
        when(commandContext.getUpdate()).thenReturn(update);
        when(userService.getVolunteersByPhoneNumberIsNotNull()).thenReturn(userList);


        // Act
        MessageToTelegram result = testingCommand.execute(commandContext);

        // Assert
        assertThat(result.getSendMessages()).hasSize(1);
        SendMessage first = result.getSendMessages().getFirst();
        assertThat(first.getChatId()).isEqualTo(String.valueOf(chatId));
        assertThat(first.getText()).contains("телефончик1");
        assertThat(first.getText()).contains("телефончик2");
        ReplyKeyboardMarkup replyMarkup = (ReplyKeyboardMarkup) first.getReplyMarkup();
        assertThat(replyMarkup.getKeyboard().size()).isEqualTo(2);
        List<String> nameButtons = replyMarkup.getKeyboard()
                .stream()
                .flatMap(Collection::stream)
                .map(KeyboardButton::getText)
                .toList();
        assertThat(nameButtons).hasSize(3);
        assertThat(nameButtons).contains(checkButton);


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