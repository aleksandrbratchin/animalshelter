package ru.teamfour.service.api.user;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.textcommand.command.api.State;

import java.util.List;
import java.util.UUID;

@Service
public interface UserServiceApi {

    User findByChatId(@NotNull Long chatId);

    User save(User client);

    void saveAll(List<User> client);

    /**
     * Создает пользователя и задает ему значения по умолчанию
     * @param update из телеграм {@link Update}
     * @param state в какое меню перешел пользователь в {@link State}
     * @return Пользователь с обновленной инфомацией {@link User}
     */
    User save(Update update, State state);

    /**
     * Обновляет информацию о пользователе и {@link State}
     * @param user пользователь которому нужно обновить информацию {@link User}
     * @param update из телеграм {@link Update}
     * @param state в какое меню перешел пользователь в {@link State}
     * @return Пользователь с обновленной инфомацией {@link User}
     */
    User updateInfoAndState(User user, Update update, State state);

    /**
     * Обновляет {@link State} у {@link User}
     * @param user пользователь которому нужно обновить информацию {@link User}
     * @param state в какое меню перешел пользователь в {@link State}
     * @return Пользователь с обновленной инфомацией {@link User}
     */
    User updateState(User user, State state);

    /**
     * Ищет пользователя по {@code chatId} если не находит создает нового
     * @param update из телеграм {@link Update}
     * @return Пользователя {@link User}
     */
    User findByUserByChatIdOrCreateUser(Update update);

    /**
     * Получает наименее загруженного волонтера
     * @return наименее загруженный волонтер
     */
    User getAvailableVolunteer();

    /**
     * Получает список волонтеров с именем пользователя
     * @return список волонтеров
     */
    List<User> getVolunteersByNickNameIsNotNull();

    /**
     * Получает список волонтеров с номерами телефонов
     * @return список волонтеров
     */
    List<User> getVolunteersByPhoneNumberIsNotNull();
    /**
     * Получает список пользователей по уникальному идентификатору
     * @return список пользователей
     */
    User getUser (@NotNull UUID id);

    /**
     * Получение списка пользователей по их роли
     *
     * @param role
     * @return лист пользователей
     */
    List<User> getUsersByRole(RoleUser role);
}
