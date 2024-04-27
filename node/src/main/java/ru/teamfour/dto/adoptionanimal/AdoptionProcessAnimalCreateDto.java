package ru.teamfour.dto.adoptionanimal;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdoptionProcessAnimalCreateDto {

    /**
     * id приюта
     */
    @NotNull
    private UUID idShelter;

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

}
