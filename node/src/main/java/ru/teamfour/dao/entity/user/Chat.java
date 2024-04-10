package ru.teamfour.dao.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * Активный чат. С волонтером или клиентом
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Chat {

    /**
     * Активный чат. С волонтером или клиентом
     */
    @Column(name = "active_chat")
    Long activeChat;

}
