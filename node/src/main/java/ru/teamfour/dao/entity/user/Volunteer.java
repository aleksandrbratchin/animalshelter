package ru.teamfour.dao.entity.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
@DiscriminatorValue("VOLUNTEER")
public class Volunteer extends ParentUser {

    @Builder
    public Volunteer(UUID id, Long chatId, State state) {
        super(id, chatId, state);
    }
}
