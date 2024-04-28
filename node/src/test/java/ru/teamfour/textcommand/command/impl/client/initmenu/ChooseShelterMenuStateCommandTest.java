package ru.teamfour.textcommand.command.impl.client.initmenu;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.dao.entity.user.UserInfo;
import ru.teamfour.mappers.shelter.ShelterAddDtoMapper;
import ru.teamfour.mappers.shelter.ShelterDtoMapper;
import ru.teamfour.myutils.MessageUtils;
import ru.teamfour.repositories.ShelterRepository;
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
class ChooseShelterMenuStateCommandTest {

    @Value("${buttonName.dogShelter}")
    private String dogShelter;

    @Value("${buttonName.catShelter}")
    private String catShelter;

    @Value("${buttonName.backButton}")
    private String backButton;

    @Autowired
    private ChooseShelterMenuCommand testingCommand;

    @SpyBean
    private MessageUtils messageUtils;

    @SpyBean
    private MenuButtonFactory menuFactory;

    @MockBean
    private UserService userService;

    @InjectMocks
    private ShelterServiceImpl shelterService;

    @MockBean
    private ShelterRepository shelterRepository;

    @SpyBean
    private ShelterAddDtoMapper shelterAddDtoMapper;

    @SpyBean
    private ShelterDtoMapper shelterDtoMapper;

    @Nested
    class Execute {

        @Test
        void SheltersIsNotPresent() {
            // Arrange
            CommandContext commandContext = mock(CommandContext.class);
            long chatId = 1_000_000L;
            Chat chat = new Chat();
            chat.setId(chatId);
            Message message = new Message();
            message.setChat(chat);
            message.setText(dogShelter);
            Update update = new Update();
            update.setMessage(message);
            User user = User.builder()
                    .chatId(chatId)
                    .build();
            when(commandContext.getUser()).thenReturn(user);
            when(commandContext.getUpdate()).thenReturn(update);
            when(shelterRepository.findByTypeOfAnimal(any())).thenReturn(new ArrayList<>());

            // Act
            MessageToTelegram result = testingCommand.execute(commandContext);

            // Assert
            assertThat(result.getSendMessages()).hasSize(1);
            SendMessage first = result.getSendMessages().getFirst();
            assertThat(first.getChatId()).isEqualTo(String.valueOf(chatId));
            assertThat(first.getText()).contains("Нет приютов");
            ReplyKeyboardMarkup replyMarkup = (ReplyKeyboardMarkup) first.getReplyMarkup();
            assertThat(replyMarkup.getKeyboard().size()).isEqualTo(1);
            List<String> nameButtons = replyMarkup.getKeyboard()
                    .stream()
                    .flatMap(Collection::stream)
                    .map(KeyboardButton::getText)
                    .toList();
            assertThat(nameButtons).hasSize(1);
            assertThat(nameButtons).contains(backButton);
            verify(userService).save(user);
        }

        @Test
        void SheltersIsPresent() {
            // Arrange
            CommandContext commandContext = mock(CommandContext.class);
            long chatId = 1_000_000L;
            Chat chat = new Chat();
            chat.setId(chatId);
            Message message = new Message();
            message.setChat(chat);
            message.setText(dogShelter);
            Update update = new Update();
            update.setMessage(message);
            User user = User.builder()
                    .chatId(chatId)
                    .userInfo(UserInfo.builder().phoneNumber("").build())
                    .build();
            when(commandContext.getUser()).thenReturn(user);
            when(commandContext.getUpdate()).thenReturn(update);
            String nameShelter = "Название приюта";
            when(shelterRepository.findByTypeOfAnimal(any())).thenReturn(new ArrayList<>() {{
                this.add(
                        Shelter.builder()
                                .id(UUID.randomUUID())
                                .name(nameShelter)
                                .typeOfAnimal(TypeAnimal.DOG)
                                .build()
                );
            }});

            // Act
            MessageToTelegram result = testingCommand.execute(commandContext);

            // Assert
            assertThat(result.getSendMessages()).hasSize(1);
            SendMessage first = result.getSendMessages().getFirst();
            assertThat(first.getChatId()).isEqualTo(String.valueOf(chatId));
            assertThat(first.getText()).contains("Выберите приют");
            ReplyKeyboardMarkup replyMarkup = (ReplyKeyboardMarkup) first.getReplyMarkup();
            assertThat(replyMarkup.getKeyboard().size()).isEqualTo(2);
            List<String> nameButtons = replyMarkup.getKeyboard()
                    .stream()
                    .flatMap(Collection::stream)
                    .map(KeyboardButton::getText)
                    .toList();
            assertThat(nameButtons).hasSize(2);
            assertThat(nameButtons).contains(nameShelter);
            assertThat(nameButtons).contains(backButton);
            verify(userService).save(user);
        }
    }

    @Nested
    class isCommand {
        @Nested
        class Correct {
            @Test
            void cat() {

                // Act
                boolean result = testingCommand.isCommand(catShelter);

                // Assert
                assertTrue(result);
            }

            @Test
            void dog() {

                // Act
                boolean result = testingCommand.isCommand(dogShelter);

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