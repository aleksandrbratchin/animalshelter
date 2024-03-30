package ru.teamfour.service.impl.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.dao.entity.user.UserInfo;
import ru.teamfour.repositories.UserRepository;
import ru.teamfour.service.api.user.UserServiceApi;
import ru.teamfour.textcommand.command.api.State;

@Service
@Transactional
public class UserService implements UserServiceApi {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User client) {
        return repository.save(client);
    }

    @Override
    public User save(Update update, State state) {
        var message = update.getMessage();
        var chatId = message.getChat().getId();
        return repository.save(
                User.builder()
                        .chatId(chatId)
                        .state(State.MAIN_MENU)
                        .role(RoleUser.CLIENT)
                        .userInfo(getUserInfo(update))
                        .build()
        );
    }

    @Override
    public User updateInfoAndState(User user, Update update, State state) {
        user.setState(state);
        user.setUserInfo(getUserInfo(update));
        return repository.save(user);
    }

    @Override
    public User updateState(User user, State state) {
        user.setState(state);
        return repository.save(user);
    }

    @Override
    public User findByUserByChatIdOrCreateUser(Update update) {
        var message = update.getMessage();
        var chatId = message.getChat().getId();
        return repository.findByChatId(chatId)
                .orElseGet(
                        () -> this.save(update, State.MAIN_MENU)
                );
    }

    /**
     * Маппер из {@link Update} в {@link UserInfo}
     *
     * @param update из телеграм
     * @return Информация о пользователе {@link UserInfo}
     */
    private UserInfo getUserInfo(Update update) {
        var message = update.getMessage();
        var telegramUser = message.getFrom();
        var contact = message.getContact();
        return UserInfo.builder()
                .firstName(telegramUser.getFirstName())
                .lastName(telegramUser.getLastName())
                .phoneNumber(contact == null ? null : contact.getPhoneNumber())
                .nickName(telegramUser.getUserName())
                .build();
    }
}
