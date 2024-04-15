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
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.infoforadoption.InfoForAdoption;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.myutils.MessageUtils;
import ru.teamfour.repositories.InfoForAdoptionRepository;
import ru.teamfour.repositories.ShelterRepository;
import ru.teamfour.service.impl.infoforadoption.InfoForAdoptionServiceImpl;
import ru.teamfour.service.impl.shelter.ShelterServiceImpl;
import ru.teamfour.service.impl.user.UserService;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.menu.MenuButtonFactory;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.teamfour.dao.entity.animal.TypeAnimal.DOG;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
public class ListAnimalsCommandTest {
    @Value("${buttonName.listAnimals}")
    private String buttonName;

    @Value("${buttonName.recommendations}")
    private String checkButton;

    @InjectMocks
    private ListAnimalsCommand testingCommand;

    @MockBean
    private UserService userService;
    @MockBean
    private ShelterRepository repository;

    @SpyBean
    private MessageUtils messageUtils;
    @SpyBean
    private ShelterServiceImpl service;

    @SpyBean
    private MenuButtonFactory menuFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(testingCommand, "buttonName", buttonName);
        ReflectionTestUtils.setField(testingCommand, "service", service);
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
        UUID id = UUID.randomUUID();
        Animal animal1 = Animal.builder()
                .id(id)
                .name("Жучка")
                .build();
        Animal animal2 = Animal.builder()
                .id(id)
                .name("Барбос")
                .build();
        List<Animal> animals = new ArrayList<>(List.of(animal1, animal2));
        Shelter shelter1 = Shelter.builder()
                .id(id)
                .name(shelterName)
                .typeOfAnimal(DOG)
                .animals(animals)
                .build();
        User user = User.builder()
                .chatId(chatId)
                .shelter(shelter1)
                .build();

        when(commandContext.getUser()).thenReturn(user);
        when(commandContext.getUpdate()).thenReturn(update);
        when(repository
                .getReferenceById(any(UUID.class)))
                .thenReturn(shelter1);

        // Act
        MessageToTelegram result = testingCommand.execute(commandContext);

        // Assert
        assertThat(result.getSendMessages()).hasSize(1);
        SendMessage first = result.getSendMessages().getFirst();
        assertThat(first.getChatId()).isEqualTo(String.valueOf(chatId));
        assertThat(first.getText()).contains("Жучка");
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
