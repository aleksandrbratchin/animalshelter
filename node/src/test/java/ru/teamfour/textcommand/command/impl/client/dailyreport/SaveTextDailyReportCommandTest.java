package ru.teamfour.textcommand.command.impl.client.dailyreport;

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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessStatus;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.photocommand.CommandPhotoContext;
import ru.teamfour.repositories.DailyReportRepository;
import ru.teamfour.service.impl.dailyreport.DailyReportService;
import ru.teamfour.service.impl.user.UserService;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import transfer.TransferByteObject;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
class SaveTextDailyReportCommandTest {

    @Value("${buttonName.sendText}")
    private String checkButton;

    @Autowired
    private SaveTextDailyReportCommand testingCommand;

    @MockBean
    private UserService userService;
    @InjectMocks
    private DailyReportService dailyReportServiceApi;

    @MockBean
    private DailyReportRepository dailyReportRepository;

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
        UUID shelterId = UUID.randomUUID();
        User user = User.builder()
                .chatId(chatId)
                .adoptions(List.of(
                        AdoptionProcessAnimal.builder()
                                .id(UUID.randomUUID())
                                .shelter(Shelter.builder()
                                        .id(shelterId)
                                        .build())
                                .adoptionProcessStatus(AdoptionProcessStatus.PROCESS_ADOPTION)
                                .build()
                ))
                .shelter(
                        Shelter.builder()
                                .id(shelterId)
                                .build()
                )
                .build();
        when(commandContext.getUser()).thenReturn(user);
        when(commandContext.getUpdate()).thenReturn(update);

        // Act
        MessageToTelegram result = testingCommand.execute(commandContext);

        // Assert
        assertThat(result.getSendMessages()).hasSize(1);
        SendMessage first = result.getSendMessages().getFirst();
        assertThat(first.getChatId()).isEqualTo(String.valueOf(chatId));
        assertThat(first.getText()).contains("Отчет отправлен");
        ReplyKeyboardMarkup replyMarkup = (ReplyKeyboardMarkup) first.getReplyMarkup();
        assertThat(replyMarkup.getKeyboard().size()).isEqualTo(2);
        List<String> nameButtons = replyMarkup.getKeyboard()
                .stream()
                .flatMap(Collection::stream)
                .map(KeyboardButton::getText)
                .toList();
        assertThat(nameButtons).hasSize(4);
        assertThat(nameButtons).contains(checkButton);
        verify(userService).save(user);
    }

    @Test
    void isCommand() {

        // Act
        boolean result = testingCommand.isCommand("");

        // Assert
        assertTrue(result);
    }

}