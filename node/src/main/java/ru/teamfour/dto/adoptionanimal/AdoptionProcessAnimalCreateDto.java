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
    @NotNull //todo можно проверять что у пользователя нет усыновления в этом приюте
    private UUID idUser;

    /**
     * id животного которое усыновляют
     */
    @NotNull //todo можно проверять что животное в доступном статусе
    private UUID idAnimal;

}
