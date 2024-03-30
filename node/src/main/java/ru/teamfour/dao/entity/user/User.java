package ru.teamfour.dao.entity.user;

import jakarta.persistence.*;
import lombok.*;
import ru.teamfour.dao.entity.AuditEntity;
import ru.teamfour.textcommand.command.api.State;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends AuditEntity {

    @Column(name = "chat_id", unique = true, nullable = false)
    Long chatId;

    @Column(name = "role_user", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleUser role;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

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
