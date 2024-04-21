package ru.teamfour.service.impl.user;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.dao.entity.user.UserInfo;
import ru.teamfour.repositories.UserRepository;
import ru.teamfour.service.api.user.UserServiceApi;
import ru.teamfour.textcommand.command.api.State;

import java.util.List;
import java.util.UUID;

@Service
@Validated
@Transactional
public class UserService implements UserServiceApi {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findByChatId(@NotNull Long chatId) {
        return repository.findByChatId(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Отсутствует пользователь с chatId = " + chatId.toString()));
    }

    @Override
    public User save(User client) {
        return repository.save(client);
    }

    @Override
    public void saveAll(List<User> client) {
        repository.saveAll(client);
    }

    @Override
    public User save(Update update, State state) {
        var message = update.getMessage();
        var chatId = message.getChat().getId();
        return repository.save(
                User.builder()
                        .chatId(chatId)
                        .state(state)
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

    /**
     * Находит пользователя в БД по {@code chatId}. Если пользователя нет, создает его.
     *
     * @param update из телеграм {@link Update}
     * @return {@link User}
     */
    @Override
    public User findByUserByChatIdOrCreateUser(Update update) {
        var message = update.getMessage();
        var chatId = message.getChat().getId();
        return repository.findByChatId(chatId)
                .orElseGet(
                        () -> this.save(update, State.INIT_MENU)
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

    @Override
    public User getAvailableVolunteer() {
        return repository.getAvailableVolunteer().orElse(null);
    }

    @Override
    public List<User> getVolunteersByNickNameIsNotNull() {
        return repository.getVolunteersByNickNameIsNotNull();
    }

    @Override
    public List<User> getVolunteersByPhoneNumberIsNotNull() {
        return repository.getVolunteersByPhoneNumberIsNotNull();
    }

    @Override
    public User getUser(@NotNull UUID id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Отсутствует пользователь с id = " + id));
    }

    @Override
    public List<User> getUsersByRole(RoleUser role) {
        return repository.findUsersByRole(role);
    }

}
