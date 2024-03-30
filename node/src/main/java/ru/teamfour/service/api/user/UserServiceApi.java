package ru.teamfour.service.api.user;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.textcommand.command.api.State;

@Service
public interface UserServiceApi {

    User save(User client);

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
}
