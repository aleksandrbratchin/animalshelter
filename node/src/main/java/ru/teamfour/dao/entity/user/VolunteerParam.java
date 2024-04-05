package ru.teamfour.dao.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * Параменты для пользователей с ролью волонтер ({@code RoleUser.VOLUNTEER})
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class VolunteerParam {

    /**
     * Загруженность волонтера - сколько запросовв он обработал
     * в зависимости от этого параметра удем выдавать случайного волонтера
     */
    @Column(name = "workload")
    private int workload;

}
