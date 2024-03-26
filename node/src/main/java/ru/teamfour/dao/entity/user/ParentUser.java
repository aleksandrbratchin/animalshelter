package ru.teamfour.dao.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.teamfour.dao.entity.AuditEntity;
import ru.teamfour.textcommand.command.api.State;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role",
        discriminatorType = DiscriminatorType.STRING)
public abstract class ParentUser extends AuditEntity {


    @Column(name = "chat_id")
    Long chatId;

    @Column(insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private RoleUser role;

    @Enumerated(EnumType.STRING)
    private State state;

    public ParentUser(UUID id, Long chatId, State state) {
        super(id);
        this.chatId = chatId;
        this.state = state;
    }
}
