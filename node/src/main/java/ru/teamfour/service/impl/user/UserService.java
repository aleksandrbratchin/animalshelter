package ru.teamfour.service.impl.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.repositories.UserRepository;

@Service
@Transactional
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User save(User client) {
        return repository.save(client);
    }

    public User findByChatId(Long chatId) {
        return repository.findByChatId(chatId)
                .orElse( //todo почему orElse некорректно отрабатывает?
                        null
                );
    }
}
