package ru.teamfour.dao.entity.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.teamfour.dao.entity.AuditEntity;
import ru.teamfour.dao.entity.shelter.Shelter;
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

    @Embedded
    private VolunteerParam volunteerParam;

    @Embedded
    private Chat chat;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shelter_id", referencedColumnName = "id")
    private Shelter shelter;

    @Builder
    public User(UUID id, Long chatId, RoleUser role, State state, UserInfo userInfo, Shelter shelter) {
        super(id);
        this.chatId = chatId;
        this.role = role;
        this.state = state;
        this.userInfo = userInfo;
        this.volunteerParam = new VolunteerParam();
        this.chat = new Chat();
        this.shelter = shelter;

    }

}
