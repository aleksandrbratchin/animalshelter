package ru.teamfour.dto.adoptionanimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.teamfour.dto.animal.AnimalDto;
import ru.teamfour.dto.shelter.ShelterInfoDto;
import ru.teamfour.dto.user.UserInfoDto;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdoptionProcessAnimalInfoDto {

    @NotNull
    private UUID id;
    /**
     * приют
     */
    @NotNull
    private ShelterInfoDto shelter;

    /**
     * id пользователя
     */
    @NotNull
    private UserInfoDto user;

    /**
     * id животного которое усыновляют
     */
    @NotNull
    private AnimalDto animal;

    @NotBlank
    private String date;

    /**
     * Всего дней проверки
     */
    private int daysOfVerification;

    /**
     * дней провалено
     */
    private int daysRejected;

    /**
     * дней одобрено
     */
    private int daysApproved;

    /**
     * дней пропущено
     */
    private int daysMissed;

}
