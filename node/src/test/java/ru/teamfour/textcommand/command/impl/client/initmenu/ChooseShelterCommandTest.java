package ru.teamfour.textcommand.command.impl.client.initmenu;

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
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.dao.entity.user.UserInfo;
import ru.teamfour.myutils.MessageUtils;
import ru.teamfour.service.impl.shelter.ShelterServiceImpl;
import ru.teamfour.service.impl.user.UserService;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.menu.MenuButtonFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
class ChooseShelterCommandTest {

    @Value("${buttonName.adoption}")
    private String checkButton;
    @InjectMocks
    private ChooseShelterCommand testingCommand;

    @MockBean
    private UserService userService;

    @MockBean
    private ShelterServiceImpl shelterService;

    @SpyBean
    private MessageUtils messageUtils;

    @SpyBean
    private MenuButtonFactory menuFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(testingCommand, "shelterService", shelterService);
    }

    @Nested
    class Execute {

        @Test
        void SheltersIsPresent() {
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
                    .userInfo(UserInfo.builder().phoneNumber("").build())
                    .build();
            when(commandContext.getUser()).thenReturn(user);
            when(commandContext.getUpdate()).thenReturn(update);
            String nameShelter = "Название приюта";
            when(shelterService.findByName(any(String.class))).thenReturn(
                    Shelter.builder()
                            .id(UUID.randomUUID())
                            .name(nameShelter)
                            .build()
            );

            // Act
            MessageToTelegram result = testingCommand.execute(commandContext);

            // Assert
            assertThat(result.getSendMessages()).hasSize(1);
            SendMessage first = result.getSendMessages().getFirst();
            assertThat(first.getChatId()).isEqualTo(String.valueOf(chatId));
            assertThat(first.getText()).contains("Вы выбрали приют");
            ReplyKeyboardMarkup replyMarkup = (ReplyKeyboardMarkup) first.getReplyMarkup();
            assertThat(replyMarkup.getKeyboard().size()).isEqualTo(3);
            List<String> nameButtons = replyMarkup.getKeyboard()
                    .stream()
                    .flatMap(Collection::stream)
                    .map(KeyboardButton::getText)
                    .toList();
            assertThat(nameButtons).hasSize(5);
            assertThat(nameButtons).contains(checkButton);
            verify(userService).save(user);
        }

    }


    @Nested
    class isCommand {
        @Nested
        class Correct {
            @Test
            void isCommand() {
                // Arrange
                String nameShelter = "Название приюта";
                when(shelterService.findAll()).thenReturn(new ArrayList<>() {{
                    this.add(
                            Shelter.builder()
                                    .id(UUID.randomUUID())
                                    .name(nameShelter)
                                    .build()
                    );
                }});

                // Act
                boolean result = testingCommand.isCommand(nameShelter);

                // Assert
                assertTrue(result);
            }

        }

        @Nested
        class Incorrect {
            @Test
            void incorrectCommand() {
                // Arrange
                String nameShelter = "Название приюта";
                when(shelterService.findAll()).thenReturn(new ArrayList<>() {{
                    this.add(
                            Shelter.builder()
                                    .id(UUID.randomUUID())
                                    .name(nameShelter)
                                    .build()
                    );
                }});

                // Act
                boolean result = testingCommand.isCommand("Нет такого приюта");

                // Assert
                assertFalse(result);
            }
        }
    }

}