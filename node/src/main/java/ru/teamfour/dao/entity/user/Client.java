package ru.teamfour.dao.entity.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.teamfour.textcommand.command.api.State;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("CLIENT")
public class Client extends ParentUser {

    @Builder
    public Client(UUID id, Long chatId, State state) {
        super(id, chatId, state);
    }
}
