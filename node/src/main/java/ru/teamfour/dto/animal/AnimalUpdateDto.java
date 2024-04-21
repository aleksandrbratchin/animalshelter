package ru.teamfour.dto.animal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import ru.teamfour.dao.entity.animal.AdoptionAnimalState;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.validators.adoptionanimalstate.AdoptionAnimalStateValid;
import ru.teamfour.validators.typeanimal.TypeAnimalValid;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalUpdateDto {

    @NotBlank
    private String name;

    @TypeAnimalValid
    private TypeAnimal typeAnimal;

    @Positive
    private Double age;

    @NotBlank
    private String breed;

    @NotBlank
    private String habits;

    @AdoptionAnimalStateValid
    private AdoptionAnimalState adopted;

    @NotNull
    private UUID idShelter;

}
