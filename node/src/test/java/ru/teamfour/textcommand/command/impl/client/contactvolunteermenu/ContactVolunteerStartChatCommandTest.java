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
public class ContactVolunteerStartChatCommandTest {
    @Value("${buttonName.contactVolunteerStartChat}")
    private String buttonName;
    @Value("${buttonName.contactVolunteersByPhoneNumber}")
    private String checkButton;
    @InjectMocks
    private ContactVolunteersByNicknameCommand testingCommand;

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


}
