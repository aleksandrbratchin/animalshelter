package ru.teamfour.dto.adoptionanimal;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.teamfour.dto.shelter.ShelterInfoDto;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdoptionProcessAnimalActiveDto {

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
    private UUID idUser;
    /**
     * id животного которое усыновляют
     */
    @NotNull
    private UUID idAnimal;
    /**
     * Кол-во дней усыновления
     */
    @NotNull
    private int days;
    /**
     * Кол-во отчетов сдано
     */
    @NotNull
    private int reportsHaveBeenCreated;
    /**
     * Кол-во успешных отчетов
     */
    @NotNull
    private int successfulReport;
    /**
     * Кол-во проваленных отчетов
     */
    @NotNull
    private int failedReport;
    /**
     * Кол-во пропущенно дней
     */
    @NotNull
    private int missedDay;

}
