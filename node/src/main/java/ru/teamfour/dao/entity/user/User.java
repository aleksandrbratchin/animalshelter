package ru.teamfour.dao.entity.user;

import jakarta.persistence.*;
import lombok.*;
import ru.teamfour.dao.entity.AuditEntity;
import ru.teamfour.textcommand.command.api.State;

import java.util.UUID;

/**
 * Таблица клиентов и волонтеров
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends AuditEntity {

    @Column(name = "chat_id", unique = true, nullable = false)
    Long chatId;
    /**
     * role - роль пользователя {@link RoleUser}
     */
    @Column(name = "role_user", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleUser role;

    /**
     * state - название меню в котором пользователь находится в данный момент {@link State}
     */
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    /**
     * userInfo - Общая информация о пользователе и контактные данные {@link UserInfo}
     */
    @Embedded
    private UserInfo userInfo;

    @Builder
    public User(UUID id, Long chatId, RoleUser role, State state, UserInfo userInfo) {
        super(id);
        this.chatId = chatId;
        this.role = role;
        this.state = state;
        this.userInfo = userInfo;
    }

}
