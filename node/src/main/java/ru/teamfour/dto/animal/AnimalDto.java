package ru.teamfour.dto.animal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import ru.teamfour.dao.entity.animal.AdoptionAnimalState;
import ru.teamfour.dao.entity.animal.TypeAnimal;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalDto {

    @NotNull(message = "UUID cannot be null")
    private UUID id;

    @NotBlank
    private String name;

    @NotNull
    private TypeAnimal typeAnimal;

    @Positive
    private Double age;

    @NotBlank
    private String breed;

    @NotBlank
    private String habits;

    @NotBlank
    private AdoptionAnimalState adopted;

}
