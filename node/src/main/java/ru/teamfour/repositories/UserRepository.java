package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.teamfour.dao.entity.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByChatId(Long chatId);

    @Override
    Optional<User> findById(UUID uuid);

    /**
     * Получает наименее загруженного волонтера
     * @return наименее загруженный волонтер
     */
    @Query("SELECT u FROM User u where u.role='VOLUNTEER' AND u.chat.activeChat IS NULL ORDER BY u.volunteerParam.workload ASC LIMIT 1")
    Optional<User> getAvailableVolunteer();

    @Query("SELECT u FROM User u where u.role='VOLUNTEER' AND u.userInfo.nickName IS NOT NULL ORDER BY u.volunteerParam.workload ASC")
    List<User> getVolunteersByNickNameIsNotNull();

    @Query("SELECT u FROM User u where u.role='VOLUNTEER' AND u.userInfo.phoneNumber IS NOT NULL ORDER BY u.volunteerParam.workload ASC")
    List<User> getVolunteersByPhoneNumberIsNotNull();
}