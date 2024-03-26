package ru.teamfour.dao.entity.user;

import jakarta.persistence.*;
import lombok.*;
import ru.teamfour.dao.entity.AuditEntity;
import ru.teamfour.textcommand.command.api.State;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users extends AuditEntity {

    @Column(name = "chat_id")
    Long chatId;

    @Enumerated(EnumType.STRING)
    private RoleUser role;

    @Enumerated(EnumType.STRING)
    private State state;

    @Builder

    public Users(UUID id, Long chatId, RoleUser role, State state) {
        super(id);
        this.chatId = chatId;
        this.role = role;
        this.state = state;
    }

}
