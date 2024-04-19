package ru.teamfour.dto.adoptionanimal;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessStatus;
import ru.teamfour.dao.entity.user.User;

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
