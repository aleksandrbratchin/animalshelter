package ru.teamfour.textcommand.command.impl.client.adoption;

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
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.infoforadoption.InfoForAdoption;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.myutils.MessageUtils;
import ru.teamfour.repositories.InfoForAdoptionRepository;
import ru.teamfour.service.impl.infoforadoption.InfoForAdoptionServiceImpl;
import ru.teamfour.service.impl.user.UserService;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.menu.MenuButtonFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.teamfour.dao.entity.animal.TypeAnimal.DOG;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
public class ListDocumentsCommandTest {
    @Value("${buttonName.listDocuments}")
    private String buttonName;

    @Value("${buttonName.recommendations}")
    private String checkButton;

    @InjectMocks
    private ListDocumentsCommand testingCommand;

    @MockBean
    private UserService userService;
    @MockBean
    private InfoForAdoptionRepository repository;

    @SpyBean
    private MessageUtils messageUtils;
    @SpyBean
    private InfoForAdoptionServiceImpl serviceInfo;

    @SpyBean
    private MenuButtonFactory menuFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(testingCommand, "buttonName", buttonName);
        ReflectionTestUtils.setField(testingCommand, "service", serviceInfo);
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
                .shelter(
                        Shelter.builder()
                                .name(shelterName)
                                .typeOfAnimal(DOG)
                                .build())
                .build();
        UUID id = UUID.randomUUID();
        InfoForAdoption info1 = new InfoForAdoption(id, DOG,
                "причины отказа - тест1",
                "транспортировка-тест",
                "обустройство для взрослого - тест",
                "обустройство для молодого -тест",
                "советы специалиста -тест",
                "oбустройство для больного - тест",
                "список специалистов -тест1",
                "правило знакомства - тест",
                "список документов - тест");
        when(commandContext.getUser()).thenReturn(user);
        when(commandContext.getUpdate()).thenReturn(update);
        when(repository
                .findInfoForAdoptionByTypeOfAnimal(any(TypeAnimal.class)))
                .thenReturn(Optional.of(info1));
        // when(serviceInfo.findInfoForAdoptionByTypeAnimal(DOG)).thenReturn(info);

        // Act
        MessageToTelegram result = testingCommand.execute(commandContext);

        // Assert
        assertThat(result.getSendMessages()).hasSize(1);
        SendMessage first = result.getSendMessages().getFirst();
        assertThat(first.getChatId()).isEqualTo(String.valueOf(chatId));
        assertThat(first.getText()).contains("список документов - тест");
        ReplyKeyboardMarkup replyMarkup = (ReplyKeyboardMarkup) first.getReplyMarkup();
        assertThat(replyMarkup.getKeyboard().size()).isEqualTo(4);
        List<String> nameButtons = replyMarkup.getKeyboard()
                .stream()
                .flatMap(Collection::stream)
                .map(KeyboardButton::getText)
                .toList();
        assertThat(nameButtons).hasSize(8);
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
